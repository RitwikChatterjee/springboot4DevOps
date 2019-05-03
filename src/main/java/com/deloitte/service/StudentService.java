package com.deloitte.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.deloitte.dao.StudentDao;
import com.deloitte.entity.Student;

import java.util.Collection;

@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;

    public Collection<Student> getAllStudents(){
        return this.studentDao.getAllStudents();
    }

    public Student getStudentById(String id){
        return this.studentDao.getStudentById(id);
    }


    public void removeStudentById(String id) {
        this.studentDao.removeStudentById(id);
    }

    public void updateStudent(Student student){
        this.studentDao.updateStudent(student);
    }

    public void insertStudent(Student student) {
        this.studentDao.insertStudentToDb(student);
    }
}
