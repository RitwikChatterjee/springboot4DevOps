package com.deloitte.dao;

import java.util.Collection;

import com.deloitte.entity.Student;

public interface StudentDao {
    Collection<Student> getAllStudents();

    Student getStudentById(String id);

    void removeStudentById(String id);

    void updateStudent(Student student);

    void insertStudentToDb(Student student);
}
