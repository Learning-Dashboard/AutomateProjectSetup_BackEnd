package com.upc.gessi.automation.domain.models;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubjectTest {

    private Subject s;

    @BeforeEach
    public void init(){
        s= new Subject("test",true,"token",true,true);
    }

    @AfterEach
    public void clean(){
        s = null;
    }

    @Test
    void getnameTest(){
        assertEquals("test", s.getName());
    }

    @Test
    void getGithubTest(){
        assertEquals(true,s.getGithub());
    }

    @Test
    void getTokenTest(){
        assertEquals("token",s.getTokenGithub());
    }

    @Test
    void getTaigaTest(){
        assertEquals(true,s.getTaiga());
    }

    @Test
    void getSheetsTest(){
        assertEquals(true,s.getTaiga());
    }
}
