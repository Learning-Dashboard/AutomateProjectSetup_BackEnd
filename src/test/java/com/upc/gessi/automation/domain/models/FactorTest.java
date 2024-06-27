package com.upc.gessi.automation.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FactorTest {

    private Factor f;

    @BeforeEach
    public void setFactor(){
        f = new Factor("test_id","test","description","source","category","1,2");
    }

    @AfterEach
    public void clean(){
        f = null;
    }

    @Test
    void getNameTest(){
        assertEquals("test",f.getName());
    }

    @Test
    void getDesctiptionTest(){
        assertEquals("description",f.getDescription());
    }

    @Test
    void getSourceTest(){
        assertEquals("source",f.getSource());
    }

    @Test
    void getCategoryTest(){
        assertEquals("category",f.getCategory());
    }

    @Test
    void getProjectTest(){
        assertEquals("1,2",f.getProject());
    }

    @Test
    void getExternalIdTest(){
        assertEquals("test_id",f.getExternalid());
    }
}