package com.upc.gessi.automation.domain.repositories;

import com.upc.gessi.automation.domain.models.Project;
import com.upc.gessi.automation.domain.respositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class ProjectRepositoryTest {

    @Autowired
    private TestEntityManager  emanager;

    @Autowired
    private ProjectRepository projectRep;

    @Test
    void find(){
        Project p = new Project("test","subject"," "," "," ");
        emanager.persistAndFlush(p);
        assertEquals(projectRep.findByNameAndSubject("test","subject"),p);
    }

    @Test
    void exists(){
        Project p = new Project("test","subject"," "," "," ");
        emanager.persistAndFlush(p);
        assertTrue(projectRep.existsByNameAndSubject("test","subject"));
    }


}
