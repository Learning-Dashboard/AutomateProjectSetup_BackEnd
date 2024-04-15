package com.upc.gessi.automation.rest.controllers;

import com.upc.gessi.automation.domain.controllers.ProjectController;
import com.upc.gessi.automation.rest.DTO.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;



@RestController
@RequestMapping("/projects")
public class ProjectRestController {

    @Autowired
    ProjectController projectController;

    private static final String NAME = "name";
    private static final String SUBJECT = "subject";
    private static final String URL_GITHUB = "URL_github";
    private static final String URL_TAIGA = "URL_taiga";
    private static final String URL_SHEETS = "URL_sheets";

    @GetMapping
    public Integer getProjectId(@RequestParam(name = "name") String name ,@RequestParam(name = "subject") String subject){
        return projectController.getId(name,subject);
    }

    @GetMapping(value = "/config")
    public Boolean getconfigProject(@RequestParam(name="name") String name, @RequestParam(name= "subject") String subject){

        return projectController.configQR_connect_scriptT(name,subject);
        //return projectController.configQR_connect_configM(name,subject,"github");
        //projectController.configQR_connect_configGT(name,subject,"github");
        // projectController.configQR_connect_configGT(name,subject,"taiga");

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
            String ID_github = projectRequest.getID_github();
            String ID_taiga = projectRequest.getID_taiga();

            //System.out.print(" sdgfgfdg     "+name);

            ProjectDTO pDTO = new ProjectDTO(name,subject,URL_github,URL_taiga,URL_sheets,ID_github,ID_taiga);
            projectController.createProject(pDTO);

        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
