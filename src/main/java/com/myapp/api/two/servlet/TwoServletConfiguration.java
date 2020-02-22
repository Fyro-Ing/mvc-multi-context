package com.myapp.api.two.servlet;

import com.myapp.api.app.conf.WebMvcConfig;
import com.myapp.api.app.servlet.AppServletConfiguration;
import javax.servlet.MultipartConfigElement;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.http.HttpProperties;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@AutoConfigureOrder(1)
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass(DispatcherServlet.class)
@AutoConfigureAfter({
  AppServletConfiguration.class
})
public class TwoServletConfiguration {

  @Bean(name = "twoWebMvcProperties")
  @ConfigurationProperties(prefix = "spring.mvc.two")
  public WebMvcProperties twoWebMvcProperties() {
    return new WebMvcProperties();
  }

  public DispatcherServlet twoDispatcherServlet(final HttpProperties httpProperties,
    final WebMvcProperties twoWebMvcProperties) {
    final DispatcherServlet dispatcherServlet = new DispatcherServlet();
    dispatcherServlet.setDispatchOptionsRequest(twoWebMvcProperties.isDispatchOptionsRequest());
    dispatcherServlet.setDispatchTraceRequest(twoWebMvcProperties.isDispatchTraceRequest());
    dispatcherServlet
      .setThrowExceptionIfNoHandlerFound(twoWebMvcProperties.isThrowExceptionIfNoHandlerFound());
    dispatcherServlet.setPublishEvents(twoWebMvcProperties.isPublishRequestHandledEvents());
    dispatcherServlet.setEnableLoggingRequestDetails(httpProperties.isLogRequestDetails());
    return dispatcherServlet;
  }

  @Bean(name = "twoServlet")
  public ServletRegistrationBean<DispatcherServlet> twoServlet(
    final ApplicationContext applicationContext, final HttpProperties httpProperties,
    @Qualifier("twoWebMvcProperties") final WebMvcProperties twoWebMvcProperties,
    final ObjectProvider<MultipartConfigElement> multipartConfig) {
    final DispatcherServlet twoDispatcherServlet = twoDispatcherServlet(httpProperties,
      twoWebMvcProperties);
    final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    context.setParent(applicationContext);
    context.setBeanNameGenerator(new FullyQualifiedAnnotationBeanNameGenerator());

    context.register(
      WebMvcConfig.class
    );
    context.scan(
      "com.myapp.api.two"
    );

    twoDispatcherServlet.setApplicationContext(context);

    final DispatcherServletRegistrationBean registration = new DispatcherServletRegistrationBean(
      twoDispatcherServlet, twoWebMvcProperties.getServlet().getPath());
    registration.setName("two-dispatcher");
    registration.setLoadOnStartup(1);
    multipartConfig.ifAvailable(registration::setMultipartConfig);

    return registration;
  }
}
