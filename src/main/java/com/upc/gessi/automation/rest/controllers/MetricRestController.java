package com.upc.gessi.automation.rest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

@RestController
@RequestMapping("/metric")
public class MetricRestController {

    @GetMapping
    public String getMetrics(){

        try{
            String baseUrl = System.getenv("SERVER");
            System.out.print(baseUrl);
            //URL url = new URL("http://localhost:8888/api/metric/current")
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "holaa";
    };
}
