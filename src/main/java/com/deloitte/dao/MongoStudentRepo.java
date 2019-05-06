package com.deloitte.dao;

import com.deloitte.entity.Student;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

@Profile("test")
public interface MongoStudentRepo extends MongoRepository<Student, String> {
}
