package com.upc.gessi.automation.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IterationTest {

    private Iteration iteration;

    @BeforeEach
    public void setIteration(){
        iteration = new Iteration("test","subject","2024-1-1","2024-1-1","1,2");
    }

    @AfterEach
    public void clean(){ iteration = null;}

    @Test
    void getnameTest(){
        assertEquals("test",iteration.getName());
    }

    @Test
    void getSubjectTest(){
        assertEquals("subject",iteration.getSubject());
    }

    @Test
    void getFromDataTest(){
        assertEquals("2024-1-1",iteration.getFromData());
    }

    @Test
    void getToData(){
        assertEquals("2024-1-1",iteration.getToData());
    }
    @Test
    void getProjectsTest(){
        assertEquals("1,2",iteration.getProjects());
    }

    @Test
    void setProjectsTest(){
        iteration.setProjectIds("1,2,3");
        assertEquals("1,2,3",iteration.getProjects());
    }
}

