package com.upc.gessi.automation.rest.controllers;

import com.upc.gessi.automation.domain.controllers.SubjectController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectRestController {

    @Autowired
    SubjectController subjectController;
    
    @GetMapping(value="/token")
    public String getToken(@RequestParam(name = "name") String name){
        return subjectController.getTokenGit(name);
    }
    @GetMapping
    public List<String> getSubjects(){
        return subjectController.getSubjects();
    }
}
