package com.myapp.api.one.servlet;

import com.myapp.api.app.conf.WebMvcConfig;
import com.myapp.api.app.conf.properties.MvcProperties;
import com.myapp.api.app.service.AppController;
import com.myapp.api.one.OneConfiguration;
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
public class OneServletConfiguration {

  @Bean(name = "oneWebMvcProperties")
  @ConfigurationProperties(prefix = "spring.mvc.one")
  public MvcProperties oneWebMvcProperties() {
    return new MvcProperties();
  }

  public DispatcherServlet oneDispatcherServlet(final MvcProperties oneWebMvcProperties) {
    final DispatcherServlet dispatcherServlet = new DispatcherServlet();
    dispatcherServlet.setDispatchOptionsRequest(oneWebMvcProperties.isDispatchOptionsRequest());
    dispatcherServlet.setDispatchTraceRequest(oneWebMvcProperties.isDispatchTraceRequest());
    dispatcherServlet
      .setThrowExceptionIfNoHandlerFound(oneWebMvcProperties.isThrowExceptionIfNoHandlerFound());
    dispatcherServlet.setPublishEvents(oneWebMvcProperties.isPublishRequestHandledEvents());
    dispatcherServlet.setEnableLoggingRequestDetails(oneWebMvcProperties.isLogRequestDetails());
    return dispatcherServlet;
  }

  @Bean(name = "oneServlet")
  public ServletRegistrationBean<DispatcherServlet> oneServlet(
    @Qualifier("oneWebMvcProperties") final MvcProperties oneWebMvcProperties,
    final ObjectProvider<MultipartConfigElement> multipartConfig) {
    final DispatcherServlet oneDispatcherServlet = oneDispatcherServlet(oneWebMvcProperties);
    final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    context.setBeanNameGenerator(new FullyQualifiedAnnotationBeanNameGenerator());

    context.register(
      WebMvcConfig.class,
      OneConfiguration.class,
      AppController.class
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
