package com;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.web.MainController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


// No need to configure get the configuration here.
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes={})
public class MvcTest {


	@Test
	public void helloTest() throws Exception {
		MainController controller = new MainController();
		MockMvc mvc = MockMvcBuilders.standaloneSetup(controller)
				.setSingleView(new InternalResourceView("index")) // Probably no need for the setSingleView("")
				.build();

		mvc.perform(get("/")).andExpect(view().name("index"));
	}
}
