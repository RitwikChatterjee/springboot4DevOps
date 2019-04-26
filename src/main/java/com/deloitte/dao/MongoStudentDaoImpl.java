package com.deloitte.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.deloitte.entity.Student;

import java.util.Collection;

@Repository
@Qualifier("mongoData")
public class MongoStudentDaoImpl implements StudentDao {

    @Autowired
    private MongoStudentRepo repo;

    @Override
    public Collection<Student> getAllStudents() {
        return repo.findAll();
    }

    @Override
    public Student getStudentById(String id) {
        return  repo.findById(id).get();
    }

    @Override
    public void removeStudentById(String id) {
        repo.deleteById(id);
    }

    @Override
    public void updateStudent(Student student) {
        Student student2Updt = repo.findById(student.getId()).get();
        student2Updt.setName(student.getName());
        student2Updt.setCourse(student.getCourse());
        repo.save(student2Updt);
    }

    @Override
    public void insertStudentToDb(Student student) {
        repo.insert(student);
    }
}
