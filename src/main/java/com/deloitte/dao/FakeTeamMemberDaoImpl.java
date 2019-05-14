package com.deloitte.dao;

import com.deloitte.entity.TeamMember;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Repository
@Profile("local")
public class FakeTeamMemberDaoImpl implements TeamMemberDao {

    private static Map<String, TeamMember> teamMembers;

    static {

        teamMembers = new HashMap<String, TeamMember>();

        teamMembers.put("1", new TeamMember("1", "Chintan", "Project Lead"));
        teamMembers.put("2", new TeamMember("2","Sultan", "DevOps Lead"));
        teamMembers.put("3", new TeamMember("3","Nayeem", "Sponsor"));

    }

    @Override
    public Collection<TeamMember> getAllTeamMembers(){
        return this.teamMembers.values();
    }

    @Override
    public TeamMember getTeamMemberById(String id){
        return this.teamMembers.get(id);
    }

    @Override
    public void removeTeamMemberById(String id) {
        this.teamMembers.remove(id);
    }

    @Override
    public void updateTeamMember(TeamMember teamMember){
        TeamMember s = teamMembers.get(teamMember.getId());
        s.setRole(teamMember.getRole());
        s.setName(teamMember.getName());
        teamMembers.put(teamMember.getId(), teamMember);
    }

    @Override
    public void insertTeamMemberToDb(TeamMember teamMember) {
        // If id does not exist, generate one
        if (teamMember.getId()== null) teamMember.setId(Integer.toString((new Random()).nextInt()));

        this.teamMembers.put(teamMember.getId(), teamMember);
    }
}
