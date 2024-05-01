package com.upc.gessi.automation.domain.controllers;

import com.upc.gessi.automation.domain.models.Project;
import com.upc.gessi.automation.domain.respositories.ProjectRepository;
import com.upc.gessi.automation.rest.DTO.ProjectDTO;
import com.upc.gessi.automation.rest.DTO.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ProjectController {

    @Autowired
    ProjectRepository projectRep;

    public ProjectController(ProjectRepository projectRep) {
        this.projectRep = projectRep;
    }

    /*public Map<String,String> getProjects() {
        Map<String, String> list = new HashMap<>();
        Iterable<Project> projects = projectRep.findAll();
        for (Project project : projects) {
            String name = project.getName();
            String subj = project.getSubject();
            list.put(name, subj);
        }
        return list;
    }*/
    public List<ProjectDTO>getProjects(){
        List<ProjectDTO> projects = new ArrayList<>();
        Iterable<Project> projectsit = projectRep.findAll();
        for(Project project : projectsit){
            System.out.print("IDDDD "+project.getId());
            ProjectDTO pDTO = new ProjectDTO(project.getId(), project.getName(),project.getSubject(),project.getURL_github(), project.getURL_taiga(), project.getURL_sheets());
            projects.add(pDTO);
        }
        return projects;
    }

    public void createProject(ProjectDTO pDTO){

        Project project = new Project(pDTO.getName(),pDTO.getSubject(),pDTO.getUrlGithub(),pDTO.getUrlTaiga(),pDTO.getUrlSheets());
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
