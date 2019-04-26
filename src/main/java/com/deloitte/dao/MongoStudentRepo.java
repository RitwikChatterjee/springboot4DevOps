package com.deloitte.dao;

import com.deloitte.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoStudentRepo extends MongoRepository<Student, String> {
}
