package com.upc.gessi.automation.domain.controllers;

import com.upc.gessi.automation.domain.models.Project;
import com.upc.gessi.automation.domain.respositories.ProjectRepository;
import com.upc.gessi.automation.rest.DTO.ProjectDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProjectController {

    @Autowired
    ProjectRepository projectRep;


    public void createProject(ProjectDTO pDTO){
        Project project = new Project(pDTO.getName(),pDTO.getSubject(),pDTO.getURL_github(),pDTO.getURL_taiga(),pDTO.getURL_sheets());
        projectRep.save(project);

    }


}
