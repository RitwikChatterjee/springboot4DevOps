package com.deloitte.controller;

import com.deloitte.entity.Student;
import com.deloitte.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/uistdt")
public class UIControllerStdt {
    @Autowired
    StudentService studentService;

    @RequestMapping("/students")
    public String studentsList(Model model) {
        model.addAttribute("studentsList", studentService.getAllStudents());
        return "studentsList";
    }

    @RequestMapping("/students-add")
    public String studentsAdd(Model model) {
        return "studentsAdd";
    }

    @RequestMapping("/students-save")
    public String save(@RequestParam String name, @RequestParam String course) {
        Student student = new Student();
        student.setName(name);
        student.setCourse(course);
        studentService.insertStudent(student);

        return "redirect:/ui/students" ;
    }

}
