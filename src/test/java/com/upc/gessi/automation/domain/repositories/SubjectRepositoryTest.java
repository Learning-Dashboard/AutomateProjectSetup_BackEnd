package com.upc.gessi.automation.domain.repositories;


import com.upc.gessi.automation.domain.models.Subject;
import com.upc.gessi.automation.domain.respositories.SubjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public class SubjectRepositoryTest {

    @Autowired
    private TestEntityManager emanager;

    @Autowired
    private SubjectRepository subjectRep;

    @Test
    void findbyNameTest(){
        Subject s = new Subject("test",true,"token",true,true);
        emanager.persistAndFlush(s);
        assertEquals(subjectRep.findByName("test"),s);
    }
}
