package com.upc.gessi.automation.rest.controllers;

import com.upc.gessi.automation.domain.controllers.ProjectController;
import com.upc.gessi.automation.rest.DTO.ProjectDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.bridge.Message;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;



@RestController
@RequestMapping("/projects")
public class ProjectRestController {

    private static final String NAME = "name";
    private static final String SUBJECT = "subject";
    private static final String URL_GITHUB = "URL_github";
    private static final String URL_TAIGA = "URL_taiga";
    private static final String URL_SHEETS = "URL_sheets";



    @Autowired
    ProjectController projectController;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProject(ProjectDTO projectRequest){
        try{
            String name = projectRequest.getName();
            String subject = projectRequest.getSubject();
            String URL_github = projectRequest.getURL_github();
            String URL_taiga = projectRequest.getURL_taiga();
            String URL_sheets = projectRequest.getURL_sheets();

            System.out.print(" sdgfgfdg     "+name);

            ProjectDTO pDTO = new ProjectDTO(name,subject,URL_github,URL_taiga,URL_sheets);
            projectController.createProject(pDTO);

        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
