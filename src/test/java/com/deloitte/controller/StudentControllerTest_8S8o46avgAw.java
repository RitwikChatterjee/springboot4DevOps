package com.deloitte.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
public class StudentControllerTest_8S8o46avgAw {
	
	private MockMvc mockMvc;
	
	@InjectMocks
	private StudentController studentCtlr;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(studentCtlr).build();
	}

	@Test
	public void testStudentController() throws Exception {

		mockMvc.perform(
					MockMvcRequestBuilders.get("/students")
				)
					.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
