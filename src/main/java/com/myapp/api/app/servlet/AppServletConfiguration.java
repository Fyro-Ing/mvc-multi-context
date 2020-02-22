package com.myapp.api.app.servlet;

import com.myapp.api.app.servlet.AppServletConfiguration.ConfigEnv;
import javax.servlet.MultipartConfigElement;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.http.HttpProperties;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.DispatcherServlet;

@Import({ConfigEnv.class})
@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass(DispatcherServlet.class)
@AutoConfigureBefore(DispatcherServletAutoConfiguration.class)
public class AppServletConfiguration {

  @Bean(name = "appWebMvcProperties")
  @Primary
  @ConfigurationProperties(prefix = "spring.mvc.app")
  public WebMvcProperties appWebMvcProperties() {
    return new WebMvcProperties();
  }

  @Primary
  @Bean(name = "appDispatcherServlet")
  public DispatcherServlet appDispatcherServlet(final HttpProperties httpProperties,
    final WebMvcProperties appWebMvcProperties) {
    final DispatcherServlet dispatcherServlet = new DispatcherServlet();
    dispatcherServlet.setDispatchOptionsRequest(appWebMvcProperties.isDispatchOptionsRequest());
    dispatcherServlet.setDispatchTraceRequest(appWebMvcProperties.isDispatchTraceRequest());
    dispatcherServlet
      .setThrowExceptionIfNoHandlerFound(appWebMvcProperties.isThrowExceptionIfNoHandlerFound());
    dispatcherServlet.setPublishEvents(appWebMvcProperties.isPublishRequestHandledEvents());
    dispatcherServlet.setEnableLoggingRequestDetails(httpProperties.isLogRequestDetails());
    return dispatcherServlet;
  }

  @Primary
  @Bean
  public DispatcherServletRegistrationBean appServlet(
    @Qualifier("appDispatcherServlet") final DispatcherServlet dispatcherServlet,
    @Qualifier("appWebMvcProperties") final WebMvcProperties appWebMvcProperties,
    final ObjectProvider<MultipartConfigElement> multipartConfig) {
    final DispatcherServletRegistrationBean registration = new DispatcherServletRegistrationBean(
      dispatcherServlet, appWebMvcProperties.getServlet().getPath());
    registration.setName("main api");
    registration.setLoadOnStartup(appWebMvcProperties.getServlet().getLoadOnStartup());
    multipartConfig.ifAvailable(registration::setMultipartConfig);
    return registration;
  }

  @ComponentScan({
    "com.api.app.conf"
  })
  protected static final class ConfigEnv {

  }

}
