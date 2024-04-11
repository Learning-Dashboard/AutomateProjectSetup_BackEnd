package com.upc.gessi.automation.domain.controllers;

import com.upc.gessi.automation.domain.models.Project;
import com.upc.gessi.automation.domain.respositories.StudentRepository;
import com.upc.gessi.automation.rest.DTO.ProjectDTO;
import com.upc.gessi.automation.rest.DTO.StudentDTO;
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

    public void createStudent(StudentDTO sDTO){
        Student student = new Student(sDTO.getName(),sDTO.getIdProject(),sDTO.getUsername_github(),sDTO.getUsername_taiga(),sDTO.getUsername_sheets());
        StudentRep.save(student);
    }






}
