package com.upc.gessi.automation.rest.controllers;

import com.upc.gessi.automation.domain.controllers.ProjectController;
import com.upc.gessi.automation.domain.controllers.SubjectController;
import com.upc.gessi.automation.domain.models.Subject;
import com.upc.gessi.automation.rest.DTO.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("/projects")
public class ProjectRestController {

    @Autowired
    ProjectController projectController;



    @Autowired

    private static final String NAME = "name";
    private static final String SUBJECT = "subject";
    private static final String URL_GITHUB = "URL_github";
    private static final String URL_TAIGA = "URL_taiga";
    private static final String URL_SHEETS = "URL_sheets";

    @GetMapping(value= "/list")
    public Map<String,String> getProjects(){
        return projectController.getProjects();
    }

    @GetMapping
    public Integer getProjectId(@RequestParam(name = "name") String name ,@RequestParam(name = "subject") String subject){
        return projectController.getId(name,subject);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProject(ProjectDTO projectRequest){
        try{
            String name = projectRequest.getName();
            String subject = projectRequest.getSubject();
            String URL_github = projectRequest.getURL_github();
            String URL_taiga = projectRequest.getURL_taiga();
            String URL_sheets = projectRequest.getURL_sheets();


            //System.out.print(" sdgfgfdg     "+name);

            ProjectDTO pDTO = new ProjectDTO(name,subject,URL_github,URL_taiga,URL_sheets);
            projectController.createProject(pDTO);

        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
