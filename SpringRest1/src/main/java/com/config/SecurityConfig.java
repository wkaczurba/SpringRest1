package com.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private Log logger = LogFactory.getLog(WebSecurityConfigurerAdapter.class);
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.warn("Using custom SecurityConfig with csrf() disabled.");
		// TODO Auto-generated method stub
		//super.configure(http);
		
		http
			/*.authorizeRequests()
				.anyRequest()
				.permitAll()
			.and()*/
			.csrf().disable();
	}

}
