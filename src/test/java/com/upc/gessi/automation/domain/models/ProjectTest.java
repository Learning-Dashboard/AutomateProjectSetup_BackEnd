package com.upc.gessi.automation.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ProjectTest {

     private Project p;

     @BeforeEach
    public void setP(){
         p = new Project("test","subject_test","https://github.com/test_github","https://tree.taiga.io/project/test_taiga/timeline","https://tree.taiga.io/d/test_sheets/timeline");
     }

     @AfterEach
    public void delete(){
         p = null;
     }

     @Test
    void getName(){
         assertEquals(p.getName(),"test");
     }

     @Test
    void getSubject(){
         assertEquals(p.getSubject(),"subject_test");
     }

     @Test
     void getURL_github(){
         assertEquals(p.getURL_github(), "https://github.com/test_github");
     }

     @Test
     void getURL_taiga(){
         assertEquals(p.getURL_taiga(),"https://tree.taiga.io/project/test_taiga/timeline");
     }

     @Test
     void getURL_Sheets(){
         assertEquals(p.getURL_sheets(), "  ");
     }

     @Test
     void setNum_Students(){
         p.setNum_students(5);
         assertEquals(p.getNum_students(), 5);
     }

    @Test
    void getID_taiga(){
        assertEquals(p.getID_taiga(),"test_taiga");
    }

     @Test
     void getID_github(){
         assertEquals(p.getID_github(),"test_github");
     }

     @Test
     void getID_sheets(){
         assertEquals(p.getID_Sheets(),"test_sheets");
     }

     @Test
    void setConfigId(){
         p.setConfig_id(5);
         assertEquals(p.getConfig_id(),5);
     }



}
