package com.upc.gessi.automation.rest.controllers;

import com.upc.gessi.automation.domain.controllers.StudentController;
import com.upc.gessi.automation.domain.models.Student;
import com.upc.gessi.automation.rest.DTO.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class StudentRestController {

    @Autowired
    StudentController studentcontroller;



    @GetMapping(value = "/")
    public String Hola(){
        return "Hola Mundo";
    }

    @GetMapping(value = "/students")
    public List<String>getStudents(){
        System.out.println("AAAAAAAAAA");
        return studentcontroller.getAll();
    }





}
