package com.myapp.api.app;


import java.util.Locale;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;


@SpringBootApplication(
  scanBasePackages = {
    "com.myapp.api.app.servlet",
    "com.myapp.api.one.servlet",
    "com.myapp.api.two.servlet"
  }
)
public class Application {

  public static void main(final String... args) {
    Locale.setDefault(Locale.ENGLISH);
    // - Démarrage de SpringBoot.
    new SpringApplicationBuilder(Application.class)
      .beanNameGenerator(new FullyQualifiedAnnotationBeanNameGenerator())
      .run(args);
  }

}
