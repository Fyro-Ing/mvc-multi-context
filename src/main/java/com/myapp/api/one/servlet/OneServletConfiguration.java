package com.myapp.api.one.servlet;

import com.myapp.api.one.OneConfiguration;
import javax.servlet.MultipartConfigElement;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class OneServletConfiguration {

  @Bean(name = "oneWebMvcProperties")
  @ConfigurationProperties(prefix = "spring.mvc.one")
  public WebMvcProperties oneWebMvcProperties() {
    return new WebMvcProperties();
  }

  public DispatcherServlet oneDispatcherServlet(final HttpProperties httpProperties,
    final WebMvcProperties oneWebMvcProperties) {
    final DispatcherServlet dispatcherServlet = new DispatcherServlet();
    dispatcherServlet.setDispatchOptionsRequest(oneWebMvcProperties.isDispatchOptionsRequest());
    dispatcherServlet.setDispatchTraceRequest(oneWebMvcProperties.isDispatchTraceRequest());
    dispatcherServlet
      .setThrowExceptionIfNoHandlerFound(oneWebMvcProperties.isThrowExceptionIfNoHandlerFound());
    dispatcherServlet.setPublishEvents(oneWebMvcProperties.isPublishRequestHandledEvents());
    dispatcherServlet.setEnableLoggingRequestDetails(httpProperties.isLogRequestDetails());
    return dispatcherServlet;
  }

  @Bean(name = "oneServlet")
  public ServletRegistrationBean<DispatcherServlet> oneServlet(
    final ApplicationContext applicationContext, final HttpProperties httpProperties,
    @Qualifier("oneWebMvcProperties") final WebMvcProperties oneWebMvcProperties,
    final ObjectProvider<MultipartConfigElement> multipartConfig) {
    final DispatcherServlet oneDispatcherServlet = oneDispatcherServlet(httpProperties,
      oneWebMvcProperties);
    final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    context.setParent(applicationContext);
    context.setBeanNameGenerator(new FullyQualifiedAnnotationBeanNameGenerator());

    context.register(
      OneConfiguration.class
    );

    oneDispatcherServlet.setApplicationContext(context);

    final DispatcherServletRegistrationBean registration = new DispatcherServletRegistrationBean(
      oneDispatcherServlet, oneWebMvcProperties.getServlet().getPath());
    registration.setName("one-dispatcher");
    registration.setLoadOnStartup(1);
    multipartConfig.ifAvailable(registration::setMultipartConfig);

    return registration;
  }
}
