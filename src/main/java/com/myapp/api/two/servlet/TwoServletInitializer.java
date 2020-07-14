package com.myapp.api.two.servlet;

import com.myapp.api.app.service.AppController;
import com.myapp.api.two.TwoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

/**
 * WebApplicationInitializer : initialisation de la webapp - Definition de l'application context -
 * Creation de la servlet DispatcherServlet - Definition du mapping
 */
@Order(1)
public class TwoServletInitializer extends AbstractDispatcherServletInitializer {

  @Override
  protected WebApplicationContext createServletApplicationContext() {
    // addListeners
    final AnnotationConfigWebApplicationContext context
      = new AnnotationConfigWebApplicationContext();
    // Disallow circular references
    context.setAllowCircularReferences(false);
    context.setBeanNameGenerator(new FullyQualifiedAnnotationBeanNameGenerator());
    context.register(ConfigEnv.class);

    return context;
  }

  @Override
  protected WebApplicationContext createRootApplicationContext() {
    return null;
  }

  @Override
  protected String[] getServletMappings() {
    return new String[]{"/api/two/*"};
  }

  @Override
  protected String getServletName() {
    return "two-dispatcher";
  }

  @ComponentScan({
    "com.myapp.api.two"
  })
  @Import({
    TwoConfiguration.class,
    AppController.class
  })
  private static final class ConfigEnv {

  }

}
