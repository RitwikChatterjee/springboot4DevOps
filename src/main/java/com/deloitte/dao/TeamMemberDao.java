package com.deloitte.dao;

import com.deloitte.entity.TeamMember;

import java.util.Collection;

public interface TeamMemberDao {
    Collection<TeamMember> getAllTeamMembers();

    TeamMember getTeamMemberById(String id);

    void removeTeamMemberById(String id);

    void updateTeamMember(TeamMember student);

    void insertTeamMemberToDb(TeamMember student);
}
