package com.deloitte.controller;

import com.deloitte.entity.TeamMember;
import com.deloitte.service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ui")
public class UIController {
    @Autowired
    TeamMemberService teamMemberService;

    @RequestMapping("/teammembers")
    public String teamMembersList(Model model) {
        model.addAttribute("teamMembersList", teamMemberService.getAllTeamMembers());
        return "teamMembersList";
    }

    @RequestMapping("/teammembers-add")
    public String teamMembersAdd(Model model) {
        return "teamMembersAdd";
    }

    @RequestMapping("/teammembers-save")
    public String save(@RequestParam String name, @RequestParam String role) {
        TeamMember teamMember = new TeamMember();
        teamMember.setName(name);
        teamMember.setRole(role);
        teamMemberService.insertTeamMember(teamMember);

        return "redirect:/ui/teammembers" ;
    }

}
