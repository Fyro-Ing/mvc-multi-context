package com.myapp.api.app.servlet;

import static org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppServletConfiguration {

  @Bean
  @Primary
  public DispatcherServletPath dispatcherServletPath(
    @Qualifier(DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME) final DispatcherServletRegistrationBean dispatcherServletRegistration
  ) {
    // need that with multiple servlet
    return dispatcherServletRegistration;
  }

}
