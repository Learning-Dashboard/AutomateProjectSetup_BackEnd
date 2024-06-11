package com.upc.gessi.automation.domain.controllers;


import com.upc.gessi.automation.domain.models.Project;
import com.upc.gessi.automation.domain.models.Subject;
import com.upc.gessi.automation.domain.respositories.ProjectRepository;
import com.upc.gessi.automation.rest.DTO.ProjectDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectController projectController;

    @Test
    void getProjectTest(){
        Project p = new Project("test","subject","url_git","url_taiga","url_shee");
        ProjectDTO pdto = new ProjectDTO("test","subject","url_git","url_taiga","url_shee");
        Mockito.when(projectRepository.findByNameAndSubject("test","subject")).thenReturn(p);
        ProjectDTO dto = projectController.getProject("test","subject");
        assertEquals(dto.getName(),pdto.getName());
    }

    @Test
    void getProjectsTest(){
        Project p = new Project("test","subject","url_git","url_taiga","url_shee");
        ProjectDTO pdto = new ProjectDTO("test","subject","url_git","url_taiga","url_shee");
        List<ProjectDTO> projects = new ArrayList<>();
        List<Project> proj = new ArrayList<>();
        proj.add(p);
        projects.add(pdto);
        Mockito.when(projectRepository.findAll()).thenReturn(proj);
        List<ProjectDTO> expected = projectController.getProjects();
        assertEquals(expected.get(0).getName(),projects.get(0).getName());
    }

    @Test
    void createTest_exists(){
        ProjectDTO pdto = new ProjectDTO("test","subject","url_git","url_taiga","url_shee");
        Mockito.when(projectRepository.existsByNameAndSubject(pdto.getName(), pdto.getSubject())).thenReturn(true);

        projectController.createProject(pdto);

        verify(projectRepository,never()).save(any());

    }
    @Test
    void createTest_notexists(){
        ProjectDTO pdto = new ProjectDTO("test","subject","url_git","url_taiga","url_shee");
        Mockito.when(projectRepository.existsByNameAndSubject(pdto.getName(), pdto.getSubject())).thenReturn(false);

        projectController.createProject(pdto);

        verify(projectRepository,times(1)).save(any());

    }

    @Test
    void getIdTest(){
        Project p = new Project("test","subject","url_git","url_taiga","url_shee");
        p.setId(123);
        Mockito.when(projectRepository.findByNameAndSubject(eq("test"),eq("subject"))).thenReturn(p);

        Integer id = projectController.getId("test","subject");
        System.out.print(id);
        System.out.print(p.getId());
        assertEquals(123,id);
    }

    @Test
    void getIdGithubTest(){
       Project p = new Project("test","subject","https://github.com/test_github","https://tree.taiga.io/project/test_taiga/timeline","  ");

        Mockito.when(projectRepository.findByNameAndSubject(eq("test"),eq("subject"))).thenReturn(p);

        String id = projectController.getIdGithub("test","subject");
        assertEquals("test_github",id);
    }

    @Test
    void getIdtaigaTest(){
        Project p = new Project("test","subject","https://github.com/test_github","https://tree.taiga.io/project/test_taiga/timeline","  ");
        Mockito.when(projectRepository.findByNameAndSubject(eq("test"),eq("subject"))).thenReturn(p);

        String id = projectController.getIdTaiga("test","subject");
        assertEquals("test_taiga",id);
    }

    @Test
    void getIdSheetsTest(){
        Project p = new Project("test","subject","https://github.com/test_github","https://tree.taiga.io/project/test_taiga/timeline","https://tree.taiga.io/d/test_sheets/timeline");
        Mockito.when(projectRepository.findByNameAndSubject(eq("test"),eq("subject"))).thenReturn(p);

        String id = projectController.getIdSheets("test","subject");
        assertEquals("test_sheets",id);
    }

    @Test
    void setNumStudentsTest(){
        Project p = new Project("test","subject","https://github.com/test_github","https://tree.taiga.io/project/test_taiga/timeline","  ");
        Mockito.when(projectRepository.save(any(Project.class))).thenReturn(p);
        Mockito.when(projectRepository.findByNameAndSubject(eq("test"),eq("subject"))).thenReturn(p);

        projectController.setNumStudents(10,"test","subject");
        assertEquals(10,p.getNum_students());
    }

}
