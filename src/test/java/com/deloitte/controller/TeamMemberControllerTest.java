package com.deloitte.controller;

import com.deloitte.entity.TeamMember;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamMemberControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	ObjectMapper om = new ObjectMapper();
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testGetAllTeamMembers() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/teammembers").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(3)));
	}

	@Test
	public void testGetTeamMemberById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/teammembers/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
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
	public void testInsertTeamMember() throws Exception {
		TeamMember teamMember = new TeamMember("Ritwik", "App lead");
		String jsonRequest = om.writeValueAsString(teamMember);

/*
		MvcResult getAllMvcResultBefore = mockMvc.perform(MockMvcRequestBuilders.get("/teammembers").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String getAllContent = getAllMvcResultBefore.getResponse().getContentAsString();
		TeamMember teamMembersBefore = om.readValue(getAllContent, TeamMember.class );
*/


		MvcResult mvcResult = mockMvc.perform(post("/teammembers").content(jsonRequest).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

/*
		MvcResult getAllMvcResultAfter = mockMvc.perform(MockMvcRequestBuilders.get("/teammembers").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		int getAllSizeAfter = getAllMvcResultAfter.getResponse().getContentLength();
		Assert.assertEquals(getAllSizeBefore+1, getAllSizeAfter);
*/
	}

}
