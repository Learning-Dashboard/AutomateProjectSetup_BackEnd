package com.upc.gessi.automation.rest.controllers;

import com.upc.gessi.automation.domain.controllers.FactorController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/factor")
public class FactorRestController {

    @Autowired
    FactorController factorController;

    @GetMapping
    public void createFactors(){
        //factorController.createFactorCategory("Reversed Default");
        factorController.createFactors("bravo11",4,false);
    }
}
