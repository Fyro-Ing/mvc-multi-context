package com.myapp.api.two.servlet;

import com.myapp.api.app.conf.WebMvcConfig;
import com.myapp.api.app.conf.properties.MvcProperties;
import com.myapp.api.app.service.AppController;
import com.myapp.api.two.TwoConfiguration;
import javax.servlet.MultipartConfigElement;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class TwoServletConfiguration {

  @Bean(name = "twoWebMvcProperties")
  @ConfigurationProperties(prefix = "spring.mvc.two")
  public MvcProperties twoWebMvcProperties() {
    return new MvcProperties();
  }

  public DispatcherServlet twoDispatcherServlet(final MvcProperties twoWebMvcProperties) {
    final DispatcherServlet dispatcherServlet = new DispatcherServlet();
    dispatcherServlet.setDispatchOptionsRequest(twoWebMvcProperties.isDispatchOptionsRequest());
    dispatcherServlet.setDispatchTraceRequest(twoWebMvcProperties.isDispatchTraceRequest());
    dispatcherServlet
      .setThrowExceptionIfNoHandlerFound(twoWebMvcProperties.isThrowExceptionIfNoHandlerFound());
    dispatcherServlet.setPublishEvents(twoWebMvcProperties.isPublishRequestHandledEvents());
    dispatcherServlet.setEnableLoggingRequestDetails(twoWebMvcProperties.isLogRequestDetails());
    return dispatcherServlet;
  }

  @Bean(name = "twoServlet")
  public ServletRegistrationBean<DispatcherServlet> twoServlet(
    @Qualifier("twoWebMvcProperties") final MvcProperties twoWebMvcProperties,
    final ObjectProvider<MultipartConfigElement> multipartConfig) {
    final DispatcherServlet twoDispatcherServlet = twoDispatcherServlet(twoWebMvcProperties);
    final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    context.setBeanNameGenerator(new FullyQualifiedAnnotationBeanNameGenerator());

    context.register(
      WebMvcConfig.class,
      TwoConfiguration.class,
      AppController.class
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
