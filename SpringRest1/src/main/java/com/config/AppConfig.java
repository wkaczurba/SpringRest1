package com.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DataConfig.class)
@ComponentScan(basePackages="com", 
  excludeFilters= @Filter(type=FilterType.REGEX, pattern="com\\.web\\..*"))
public class AppConfig {

}
 