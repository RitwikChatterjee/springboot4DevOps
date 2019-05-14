package com.deloitte.service;

import com.deloitte.dao.TeamMemberDao;
import com.deloitte.entity.TeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TeamMemberService {

    @Autowired
    private TeamMemberDao teamMemberDao;

    public Collection<TeamMember> getAllTeamMembers(){
        return this.teamMemberDao.getAllTeamMembers();
    }

    public TeamMember getTeamMemberById(String id){
        return this.teamMemberDao.getTeamMemberById(id);
    }


    public void removeTeamMemberById(String id) {
        this.teamMemberDao.removeTeamMemberById(id);
    }

    public void updateTeamMember(TeamMember teamMember){
        this.teamMemberDao.updateTeamMember(teamMember);
    }

    public void insertTeamMember(TeamMember teamMember) {
        this.teamMemberDao.insertTeamMemberToDb(teamMember);
    }
}
