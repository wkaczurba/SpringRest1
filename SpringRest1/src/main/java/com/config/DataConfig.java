package com.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.data.CarRepository;
import com.data.JdbcCarRepository;

@Configuration
@ComponentScan("com.data")
public class DataConfig {
	
  // jdbcTemplate bean
  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
	return new JdbcTemplate(dataSource);
  }
  
  @Bean 
  public DataSource dataSource() {
	return new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.H2)
			.addScript("schema.sql")
			.build();
  }
  
  //@Bean
  //public CarRepository carRepository(JdbcTemplate jdbc) {
//	  return new JdbcCarRepository(jdbc);
  //}
   
}
