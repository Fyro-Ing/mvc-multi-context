package com.myapp.api.app.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

/**
 * WebApplicationInitializer : initialisation de la webapp - Definition de l'application context -
 * Creation de la servlet DispatcherServlet - Definition du mapping
 */
@Order(0)
@Slf4j
public class ApiInitializer extends AbstractDispatcherServletInitializer {

  @Override
  public void onStartup(final ServletContext servletContext) throws ServletException {
    super.onStartup(servletContext);
    addListeners(servletContext);
  }

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
    final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

    // Disallow circular references
    context.setAllowCircularReferences(false);
    context.setBeanNameGenerator(new FullyQualifiedAnnotationBeanNameGenerator());

    // Register Beans
    context.register(Config.class);

    return context;
  }

  private void addListeners(final ServletContext servletContext) {
    servletContext.addListener(new ServletContextListener() {

      @Override
      public void contextInitialized(final ServletContextEvent sce) {
        ApiInitializer.this.logger.info(" *** Initializing application ***");
      }

      @Override
      public void contextDestroyed(final ServletContextEvent sce) {
        ApiInitializer.this.logger.info(" *** Destroying application ***");
      }
    });
  }

  @Override
  protected String[] getServletMappings() {
    return new String[]{"/api/*"};
  }

  @Override
  protected String getServletName() {
    return "app-dispatcher";
  }

  @ComponentScan
  private static final class Config {

  }

  @ComponentScan({
    "com.myapp.api.app.service"
  })
  @EnableWebMvc
  private static final class ConfigEnv {

  }

}
