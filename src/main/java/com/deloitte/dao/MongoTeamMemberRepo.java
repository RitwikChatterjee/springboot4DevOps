package com.deloitte.dao;

import com.deloitte.entity.TeamMember;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

@Profile("test")
public interface MongoTeamMemberRepo extends MongoRepository<TeamMember, String> {
}
