package com.deloitte.controller;

import com.deloitte.entity.TeamMember;
import com.deloitte.service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/teammembers")
public class TeamMemberController {

    @Autowired
    private TeamMemberService teamMemberService;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<TeamMember> getAllTeamMembers(){
        return teamMemberService.getAllTeamMembers();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TeamMember getTeamMemberById(@PathVariable("id") String id){
        return teamMemberService.getTeamMemberById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteTeamMemberById(@PathVariable("id") String id){
        teamMemberService.removeTeamMemberById(id);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateTeamMemberById(@RequestBody TeamMember teamMember){
        teamMemberService.updateTeamMember(teamMember);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insertTeamMember(@RequestBody TeamMember teamMember){
        teamMemberService.insertTeamMember(teamMember);
    }
}
