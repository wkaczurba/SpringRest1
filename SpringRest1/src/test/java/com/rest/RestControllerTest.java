package com.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceView;

import com.config.AppConfig;
import com.data.CarNotFoundException;
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
	public void restExampleTest() throws Exception {
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
			.andExpect(jsonPath("$.engineSize", is(1000.0)))
			.andExpect(jsonPath("$.year", is(1995)));

			// Printing the stuff:
			String str = result.andReturn().getResponse().getContentAsString();
			System.out.println(str); // {"id":2,"make":"Fiat","model":"Punto","engineSize":1000.0,"year":1995}
	}	
	
	@Test
	public void restPostTest() throws Exception {
		MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();
			
		// Add:
		//MvcResult results = mvc.perform(
		MvcResult results = mvc.perform(
				post("/cars/add")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"id\":1,\"make\":\"Mercedes\",\"model\":\"Benz\",\"engineSize\":3398.0,\"year\":2011}"))
			.andExpect(status().isCreated())
			//.andExpect(header().stringValues("Location", "http://localhost/cars/1"))
			.andReturn();
		
		Assert.assertTrue(results.getResponse().getHeaderNames().contains("Location") );
		String location = results.getResponse().getHeader("Location");
		
		Assert.assertEquals("http://localhost/cars/1", location);
		
		// Get:
		mvc.perform(get("/cars/1"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.*", hasSize(5)))
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.make", is("Mercedes")))
			.andExpect(jsonPath("$.model", is("Benz")))
			.andExpect(jsonPath("$.engineSize", is(3398.0)))
			.andExpect(jsonPath("$.year", is(2011)))
			.andExpect(status().isOk());
	}
	
	public void retrievingInvalidCar() throws Exception {
		MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();
		mvc.perform(get("/cars/222"))
			.andExpect(status().isNotFound());
	}
	

}
