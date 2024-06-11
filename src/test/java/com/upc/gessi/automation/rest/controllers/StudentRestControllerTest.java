package com.upc.gessi.automation.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upc.gessi.automation.domain.controllers.ProjectController;
import com.upc.gessi.automation.domain.controllers.StudentController;
import com.upc.gessi.automation.domain.models.Student;
import com.upc.gessi.automation.rest.DTO.StudentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class StudentRestControllerTest {

    @Mock
    StudentController studentController;

    @Mock
    ProjectController projectController;

    @InjectMocks
    StudentRestController studentRestController;

    @Test
    void getStudentsTest(){
        List<String> students = new LinkedList<>();
        students.add("a");
        students.add("b");
        when(studentController.getAll()).thenReturn(students);
        List<String> expected = studentRestController.getAll();
        assertEquals(students,expected);
    }

    @Test
    void getStudentsProjectTest(){
        List<StudentDTO> students = new LinkedList<>();
        StudentDTO s = new StudentDTO("test", 1,"test_github","test_taiga","test_sheets");
        students.add(s);
        when(projectController.getId("test","asw")).thenReturn(1);
        when(studentController.getStudentsProject(1)).thenReturn(students);
        List<StudentDTO> expected = studentRestController.getStudentsProject("test","asw");
        assertEquals(students,expected);
    }
}
