package com.upc.gessi.automation.rest.controllers;

import com.upc.gessi.automation.domain.controllers.ProjectController;
import com.upc.gessi.automation.rest.DTO.ProjectDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProjectRestControllerTest {

    @Mock
    ProjectController projectController;

    @InjectMocks
    ProjectRestController projectRestController;

    @Test
    void getProjectsTest(){
        List<ProjectDTO> projects = new ArrayList<>();
        ProjectDTO pDTO1 = new ProjectDTO("test1","subject"," "," "," ");
        projects.add(pDTO1);
        when(projectController.getProjects()).thenReturn(projects);
        List<ProjectDTO> expected = projectRestController.getAll();
        assertEquals(projects,expected);
    }

    @Test
    void getProjectsIdTest(){
        ProjectDTO pDTO1 = new ProjectDTO("test1","subject"," "," "," ");
        when(projectController.getProject("test1","subject")).thenReturn(pDTO1);
        ProjectDTO expected = projectRestController.getProjectId("test1","subject");
        assertEquals(pDTO1.getName(),expected.getName());
    }

    @Test
    void createProjectTest(){
        List<ProjectDTO> projectRequests = Arrays.asList(
                new ProjectDTO("Project1", "Subject1", "http://github.com/project1", "http://taiga.com/project1", "http://sheets.com/project1"),
                new ProjectDTO("Project2", "Subject2", "http://github.com/project2", "http://taiga.com/project2", "http://sheets.com/project2")
        );

        try {
            for (ProjectDTO projectRequest : projectRequests) {
                String name = projectRequest.getName();
                String subject = projectRequest.getSubject();
                String urlGithub = projectRequest.getUrlGithub();
                String urlTaiga = projectRequest.getUrlTaiga();
                String urlSheets = projectRequest.getUrlSheets();

                System.out.print(" sdgfgfdg     " + name);

                ProjectDTO pDTO = new ProjectDTO(name, subject, urlGithub, urlTaiga, urlSheets);
                projectController.createProject(pDTO);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Verifica que el método createProject fue llamado dos veces
        verify(projectController, times(2)).createProject(any(ProjectDTO.class));
    }
}
