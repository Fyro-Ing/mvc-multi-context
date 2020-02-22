package com.myapp.api.app.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class AppConfig {

  /**
   * instanciation du bean pour la gestion des @Value Ne doit pas être supprimé, les @Value ne
   * seraient alors plus gérés ... que via l'import du plugin springfox !
   *
   * @return @see org.springframework.context.support.PropertySourcesPlaceholderConfigurer
   * @see <a href="http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html">Bean
   * Spring</a> #BeanFactoryPostProcessor-returning @Bean methods
   */
  @Bean
  public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }
}
