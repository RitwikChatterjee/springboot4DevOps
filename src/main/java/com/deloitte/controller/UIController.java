package com.deloitte.controller;

import com.deloitte.entity.TeamMember;
import com.deloitte.service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value = "/teammembers-details/{id}", method = RequestMethod.GET)
    public String teamMembersAdd(Model model, @PathVariable("id") String id) {
        model.addAttribute("uiVersion", uiVersion);
        model.addAttribute("teamMember", teamMemberService.getTeamMemberById(id));
        return "teamMembersDetails";
    }

    @RequestMapping(value = "/teammembers-add", method = RequestMethod.GET)
    public String teamMembersAdd(Model model) {
        model.addAttribute("uiVersion", uiVersion);
        return "teamMembersAdd";
    }

    @RequestMapping(value = "/teammembers-save", method = RequestMethod.GET)
    public String teamMemberSave(@RequestParam String name, @RequestParam String role) {
        TeamMember teamMember = new TeamMember();
        teamMember.setName(name);
        teamMember.setRole(role);
        teamMemberService.insertTeamMember(teamMember);

        return "redirect:/ui/teammembers" ;
    }

    @RequestMapping(value = "/teammembers-delete", method = RequestMethod.GET)
    public String teamMemberDelete(@RequestParam String Id) {
        teamMemberService.removeTeamMemberById(Id);

        return "redirect:/ui/teammembers" ;
    }

    @RequestMapping(value = "/teammembers-modify", method = RequestMethod.GET)
    public String teamMemberModify(Model model, @RequestParam String teamMemberId) {
        model.addAttribute("uiVersion", uiVersion);
        model.addAttribute("teamMember", teamMemberService.getTeamMemberById(teamMemberId));
        return "teamMembersModify";
    }

    @RequestMapping(value = "/teammembers-update", method = RequestMethod.GET)
    public String teamMemberUpdate(@RequestParam String teamMemberId, @RequestParam String name, @RequestParam String role) {
        TeamMember teamMember2Updt = teamMemberService.getTeamMemberById(teamMemberId);
        teamMember2Updt.setName(name);
        teamMember2Updt.setRole(role);
        teamMemberService.updateTeamMember(teamMember2Updt);

        return "redirect:/ui/teammembers" ;
    }

}
