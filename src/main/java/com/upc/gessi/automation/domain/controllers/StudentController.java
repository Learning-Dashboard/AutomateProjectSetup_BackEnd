package com.upc.gessi.automation.domain.controllers;

import com.upc.gessi.automation.domain.respositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.upc.gessi.automation.domain.models.Student;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository StudentRep;

    public List<String> getAll(){
        Iterable<Student> studentsIt = StudentRep.findAll();
        studentsIt.forEach(student -> System.out.println(student));
        List<String> studentsList = new ArrayList<>();
        studentsIt.forEach(student -> studentsList.add(student.getName()));
        return studentsList;
    }


}
