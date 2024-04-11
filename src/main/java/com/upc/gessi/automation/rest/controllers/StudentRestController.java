package com.upc.gessi.automation.rest.controllers;

import com.upc.gessi.automation.domain.controllers.StudentController;
import com.upc.gessi.automation.domain.models.Student;
import com.upc.gessi.automation.rest.DTO.ProjectDTO;
import com.upc.gessi.automation.rest.DTO.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/")
public class StudentRestController {

    @Autowired
    StudentController studentcontroller;

    @GetMapping(value = "/students")
    public List<String>getStudents(){
        System.out.println("AAAAAAAAAA");
        return studentcontroller.getAll();
    }

    @PostMapping(value = "/student")
    @ResponseStatus(HttpStatus.CREATED)
    public void createStudent(StudentDTO studentRequest){
        try{
            String name = studentRequest.getName();
            Integer idProject = studentRequest.getIdProject();
            String username_github = studentRequest.getUsername_github();
            String username_taiga = studentRequest.getUsername_taiga();
            String username_sheets = studentRequest.getUsername_sheets();

            System.out.print(" sdgfgfdg     "+name+ "   "+idProject);

            StudentDTO sDTO = new StudentDTO(name,idProject,username_github,username_taiga,username_sheets);
            studentcontroller.createStudent(sDTO);

        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }






}
