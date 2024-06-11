package com.upc.gessi.automation.domain.repositories;

import com.upc.gessi.automation.domain.models.Metric;
import com.upc.gessi.automation.domain.respositories.MetricRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public class MetricRespositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MetricRepository metricRep;

    @Test
    void findByNameTest(){
        Metric m = new Metric(1,"test","project");
        entityManager.persistAndFlush(m);
        assertEquals(metricRep.findByName("test"),m);
    }

    @Test
    void findAllByProjectTest(){
        Metric m1 = new Metric(1,"test1","project");
        Metric m2 = new Metric(2,"test2","project");
        entityManager.persistAndFlush(m1);
        entityManager.persistAndFlush(m2);
        List<Metric> metrics = new ArrayList<>();
        metrics.add(m1);
        metrics.add(m2);
        assertEquals(metricRep.findAllByProject("project"),metrics);
    }
}
