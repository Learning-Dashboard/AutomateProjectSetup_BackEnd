package com.upc.gessi.automation.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MetricTest {

    private Metric m;

    @BeforeEach
    public void init(){
        m = new Metric(1,"test","project");
    }

    @AfterEach
    public void clean(){
        m = null;
    }

    @Test
    void setandgetFactor(){
        m.setFactor("factor");
        assertEquals("factor",m.getFactor());
    }

    @Test
    void getExternalidTest(){
        assertEquals(1,m.getExternalid());
    }

    @Test
    void getProjectTest(){
        assertEquals("project",m.getProject());
    }

    @Test
    void getNameTest(){
        assertEquals("test",m.getName());
    }
}
