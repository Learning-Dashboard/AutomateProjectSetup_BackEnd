package com.upc.gessi.automation.domain.controllers;

import com.upc.gessi.automation.domain.models.Subject;
import com.upc.gessi.automation.domain.respositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;

    public String getTokenGit(String name){
        Subject sub = subjectRepository.findByName(name);
        return sub.getToken(); // Devuelve el ID del primer proyecto encontrado
    }
}
