package com.zeddmalam.brc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Configuration class for application.
 */
@Configuration
@ImportResource({
  "classpath*:/rest_config.xml"
})
@ComponentScan( basePackages = "com.zeddmalam.brc" )
@PropertySource({
  "classpath:app.properties"
})
public class AppConfig{
 
   @Bean
   public static PropertySourcesPlaceholderConfigurer properties() {
      return new PropertySourcesPlaceholderConfigurer();
   }
}
