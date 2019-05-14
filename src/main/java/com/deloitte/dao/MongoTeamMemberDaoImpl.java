package com.deloitte.dao;

import com.deloitte.entity.TeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
//@Profile("test")
public class MongoTeamMemberDaoImpl implements TeamMemberDao {

    @Autowired
    private MongoTeamMemberRepo repo;

    @Override
    public Collection<TeamMember> getAllTeamMembers() {
        return repo.findAll();
    }

    @Override
    public TeamMember getTeamMemberById(String id) {
        return repo.findById(id).get();
    }

    @Override
    public void removeTeamMemberById(String id) {
        repo.deleteById(id);
    }

    @Override
    public void updateTeamMember(TeamMember teamMember) {
        repo.save(teamMember);
    }

    @Override
    public void insertTeamMemberToDb(TeamMember teamMember) {
        repo.insert(teamMember);
    }
}
