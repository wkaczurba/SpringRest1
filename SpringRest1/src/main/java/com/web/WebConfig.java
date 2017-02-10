package com.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.config.DataConfig;

@Configuration
@EnableWebMvc
@ComponentScan({"com.web", "com.rest"})
public class WebConfig extends WebMvcConfigurerAdapter {
	// Needs view resolver
	
	@Bean
	public ViewResolver viewResolver() {
	  InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	  viewResolver.setPrefix("/WEB-INF/views/");
	  viewResolver.setSuffix(".jsp");
	
	  return viewResolver;
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
