package com.upc.gessi.automation.rest.DTOs;

import com.upc.gessi.automation.domain.models.Student;
import com.upc.gessi.automation.rest.DTO.StudentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentDTOTest {

    private Student s;
    private StudentDTO sDTO;

    @BeforeEach
    void init(){
        s = new Student("test", 1,"test_github","test_taiga","test_sheets");
        sDTO = new StudentDTO("test", 1,"test_github","test_taiga","test_sheets");
    }

    @Test
    void getName(){
        assertEquals("test",sDTO.getName());
    }

    @Test
    void getProject(){
        assertEquals(1,sDTO.getProject());
    }

    @Test
    void getGithub(){
        assertEquals("test_github",sDTO.getUsername_github());
    }

    @Test
    void getTaiga(){
        assertEquals("test_taiga",sDTO.getUsername_taiga());
    }

    @Test
    void getSheets(){
        assertEquals("test_sheets",sDTO.getUsername_sheets());
    }
}
