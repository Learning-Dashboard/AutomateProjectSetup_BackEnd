package com.upc.gessi.automation.rest.controllers;

import com.upc.gessi.automation.domain.controllers.IterationController;
import com.upc.gessi.automation.rest.DTO.IterationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/iteration")
public class IterationRestController {

    @Autowired
    IterationController iterationController;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createIteration(){
        System.out.print("enter");
        IterationDTO i = new IterationDTO("hola","asw","2024-06-02","2024-06-13","7,4");
        iterationController.addIteration(i);
    }
    @PutMapping("/edit")
    public void edit(){
        iterationController.addProject("hola",4);
    }
}
