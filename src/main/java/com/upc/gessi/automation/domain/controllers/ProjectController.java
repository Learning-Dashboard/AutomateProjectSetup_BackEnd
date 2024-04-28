package com.upc.gessi.automation.domain.controllers;

import com.upc.gessi.automation.domain.models.Project;
import com.upc.gessi.automation.domain.respositories.ProjectRepository;
import com.upc.gessi.automation.rest.DTO.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;


import java.util.HashMap;
import java.util.Map;


@Controller
public class ProjectController {

    @Autowired
    ProjectRepository projectRep;

    public ProjectController(ProjectRepository projectRep) {
        this.projectRep = projectRep;
    }

    public Map<String,String> getProjects() {
        Map<String, String> list = new HashMap<>();
        Iterable<Project> projects = projectRep.findAll();
        for (Project project : projects) {
            String name = project.getName();
            String subj = project.getSubject();
            list.put(name, subj);
        }
        return list;
    }

    public void createProject(ProjectDTO pDTO){
        Project project = new Project(pDTO.getName(),pDTO.getSubject(),pDTO.getURL_github(),pDTO.getURL_taiga(),pDTO.getURL_sheets());
        projectRep.save(project);

    }

    public Integer getId(String name, String subject) {
        Project project = projectRep.findByNameAndSubject(name, subject);
        return project.getId();
    }
    public String getIdGithub(String name, String subject){
        Project project = projectRep.findByNameAndSubject(name, subject);
        System.out.print(project.getID_github());
        return project.getID_github();
    }

    public String getIdTaiga(String name, String subject){
        Project project = projectRep.findByNameAndSubject(name, subject);
        return project.getID_taiga();
    }


}
