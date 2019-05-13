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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(3)));
	}

	@Test
	public void testGetTeamMemberById() throws Exception {

		TeamMember teamMember2Retrieve = new TeamMember();
		//TODO: Set the team member to retrieve by a call to getAll
		teamMember2Retrieve.setId("1");

		mockMvc.perform(MockMvcRequestBuilders.get("/teammembers/{id}",teamMember2Retrieve.getId()).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(teamMember2Retrieve.getId()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Chintan")));
	}

	@Test
	public void testInsertTeamMember() throws Exception {
		TeamMember teamMember = new TeamMember("JUnitTest", "Test lead");
		String jsonRequest = om.writeValueAsString(teamMember);

/*
		MvcResult getAllMvcResultBefore = mockMvc.perform(MockMvcRequestBuilders.get("/teammembers").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String getAllContent = getAllMvcResultBefore.getResponse().getContentAsString();
		TeamMember teamMembersBefore = om.readValue(getAllContent, TeamMember.class );
*/

		MvcResult mvcResult = mockMvc.perform(post("/teammembers").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

	}

	@Test
	public void testUpdateTeamMemberById() throws Exception {

		TeamMember teamMember2Updt = new TeamMember();
		//TODO: Set the team member to retrieve by a call to getAll
		teamMember2Updt.setId("2");
		teamMember2Updt.setName("UpdatedName");
		teamMember2Updt.setRole("UpdatedRole");

		String jsonRequest = om.writeValueAsString(teamMember2Updt);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/teammembers/{id}", teamMember2Updt.getId()).content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

	}

	@Test
	public void testDeleteTeamMemberById() throws Exception {

		TeamMember teamMember2Delete = new TeamMember();
		//TODO: Set the team member to retrieve by a call to getAll
		teamMember2Delete.setId("1");

		mockMvc.perform(MockMvcRequestBuilders.delete("/teammembers/{id}",teamMember2Delete.getId()).accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());

			//TODO: Update code to check user does not exists
//				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(teamMember2Delete.getId()))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Chintan")));


	}


}
