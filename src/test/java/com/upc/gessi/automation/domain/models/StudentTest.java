package com.upc.gessi.automation.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentTest {

    private Student s;

    @BeforeEach
    public void setStudent(){
        s = new Student("test",0,"test_github","test_taiga","test_sheets");
    }

    @AfterEach
    public void delete(){
        s = null;
    }

    @Test
    void getName(){
        assertEquals(s.getName(),"test");
    }

    @Test
    void getUsername_github(){
        assertEquals(s.getUsername_github(),"test_github");
    }

    @Test
    void getUsername_taiga(){
        assertEquals(s.getUsername_taiga(), "test_taiga");
    }

    @Test
    void getUsername_sheets(){
        assertEquals(s.getUsername_sheets(),"test_sheets");
    }

    @Test
    void getId_project(){
        assertEquals(s.getProject(),0);
    }
}
