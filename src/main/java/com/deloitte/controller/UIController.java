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

import java.util.ArrayList;

@Controller
@RequestMapping("/ui")
public class UIController {
    @Autowired
    TeamMemberService teamMemberService;

    @Value("${uiVersion}")
    String uiVersion;

    @Value("${sampleData}")
    String sampleData;

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

    @RequestMapping(value = "/demoadmin", method = RequestMethod.GET)
    public String demoAdmin(Model model) {
        model.addAttribute("uiVersion", uiVersion);
        model.addAttribute("sampleData", sampleData);

        //TODO: Populate sample data on page

        return "demoAdmin";
    }

    @RequestMapping(value = "/demoadmin-reset", method = RequestMethod.GET)
    public String demoAdminReset(Model model) {
        model.addAttribute("uiVersion", uiVersion);
        model.addAttribute("sampleData", sampleData);

        System.out.println(sampleData);

        // Set Sample team members TODO: Read from property file
        TeamMember teamMember1 = new TeamMember("Chintan Dalwadi", "Lead Architect");
        TeamMember teamMember2 = new TeamMember("Sultan Mohammed", "Lead Developer");

        ArrayList<TeamMember> teamMembers = new ArrayList<TeamMember>();
        teamMembers.add(teamMember1);
        teamMembers.add(teamMember2);

        teamMemberService.resetTeamMember(teamMembers);

        model.addAttribute("successMessage", "DB Successfully reset!");

        return "demoAdmin";
    }

    @RequestMapping(value = "/demoadmin-deleteall", method = RequestMethod.GET)
    public String demoAdminDeleteAll(Model model) {
        model.addAttribute("uiVersion", uiVersion);
        model.addAttribute("sampleData", sampleData);

        teamMemberService.deleteAllTeamMembers();

        model.addAttribute("successMessage", "DB Successfully cleaned!");

        return "demoAdmin";
    }

    @RequestMapping(value = "/demoadmin-sampledata", method = RequestMethod.GET)
    public String demoAdminSampleData(Model model) {
        model.addAttribute("uiVersion", uiVersion);
        model.addAttribute("sampleData", sampleData);

        System.out.println(sampleData);

        // Set Sample team members TODO: Read from property file
        TeamMember teamMember1 = new TeamMember("Chintan Dalwadi", "Lead Architect");
        TeamMember teamMember2 = new TeamMember("Sultan Mohammed", "Lead Developer");

        ArrayList<TeamMember> teamMembers = new ArrayList<TeamMember>();
        teamMembers.add(teamMember1);
        teamMembers.add(teamMember2);

        teamMemberService.insertMultiTeamMembers(teamMembers);

        model.addAttribute("successMessage", "DB Successfully updated with sample data!");

        return "demoAdmin";
    }

}
