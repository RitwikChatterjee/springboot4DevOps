package com.deloitte.controller;

import com.deloitte.entity.TeamMember;
import com.deloitte.service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ui")
public class UIController {
    @Autowired
    TeamMemberService teamMemberService;

    @Value("${uiVersion}")
    String uiVersion;

    @RequestMapping(value="/teammembers", method = RequestMethod.GET)
    public String teamMembersList(Model model) {
        model.addAttribute("uiVersion", uiVersion);
        model.addAttribute("teamMembersList", teamMemberService.getAllTeamMembers());
        return "teamMembersList";
    }

    @RequestMapping(value = "/teammembers-add", method = RequestMethod.GET)
    public String teamMembersAdd(Model model) {
        model.addAttribute("uiVersion", uiVersion);
        return "teamMembersAdd";
    }

    @RequestMapping(value = "/teammembers-save", method = RequestMethod.GET)
    public String save(@RequestParam String name, @RequestParam String role) {
        TeamMember teamMember = new TeamMember();
        teamMember.setName(name);
        teamMember.setRole(role);
        teamMemberService.insertTeamMember(teamMember);

        return "redirect:/ui/teammembers" ;
    }

}
