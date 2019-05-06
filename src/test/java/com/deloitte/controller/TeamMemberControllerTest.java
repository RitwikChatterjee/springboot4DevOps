package com.deloitte.controller;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamMemberControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testGetAllTeamMembers() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/teammembers").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testGetTeamMemberById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/teammembers/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Chintan")));
	}

	@Test
	public void testDeleteTeamMemberById() {
//		TODO: Write implementation
//		fail("Not yet implemented");
	}

	@Test
	public void testUpdateTeamMemberById() {
//		TODO: Write implementation
//		fail("Not yet implemented");
	}

	@Test
	public void testInsertTeamMember() {
//		TODO: Write implementation
//		fail("Not yet implemented");
	}

}
