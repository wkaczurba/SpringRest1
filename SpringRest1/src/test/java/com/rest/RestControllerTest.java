package com.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceView;

import com.config.AppConfig;
import com.data.CarRepository;
import com.web.WebConfig;

@ContextConfiguration(classes={AppConfig.class, WebConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class RestControllerTest {
	
	// Wiring controller bean, so controller also gets Respository from AppConfig.:
	@Autowired
	CarRestController controller;
	
	@Autowired
	WebApplicationContext wContext;
	
	@Test
	public void restTest() throws Exception {
		MockMvc mvc = MockMvcBuilders.standaloneSetup(controller)
				.build();
		
		    // Checking JSON: {"id":2,"make":"Fiat","model":"Punto","engineSize":1000.0,"year":1995}
			ResultActions result = mvc.perform(get("/cars/example"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.*", hasSize(5))) // 5 elements in the array (id, make, model ,... 
			.andExpect(jsonPath("$.id", is(2)))    // requires: com.jayway.jsonpath:json-path, org.hamcrest:hamcrest-all
			.andExpect(jsonPath("$.make", is("Fiat")))
			.andExpect(jsonPath("$.model", is("Punto")))
			.andExpect(jsonPath("engineSize", is(1000.0)))
			.andExpect(jsonPath("$.year", is(1995)));

			// Printing the stuff:
			String str = result.andReturn().getResponse().getContentAsString();
			System.out.println(str); // {"id":2,"make":"Fiat","model":"Punto","engineSize":1000.0,"year":1995}
	}	
	

}
