package com.myapp.api.app.conf;

import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

  @Resource
  private Validator validatorFactory;

  @Bean
  public MethodValidationPostProcessor methodValidationPostProcessor() {
    // Validation des contraintes (voir ValidationJSR303Aspect)
    final MethodValidationPostProcessor methodValidationPostProcessor;
    methodValidationPostProcessor = new MethodValidationPostProcessor();
    methodValidationPostProcessor.setProxyTargetClass(true);
    methodValidationPostProcessor.setValidator((javax.validation.Validator) this.validatorFactory);
    return methodValidationPostProcessor;
  }

}
