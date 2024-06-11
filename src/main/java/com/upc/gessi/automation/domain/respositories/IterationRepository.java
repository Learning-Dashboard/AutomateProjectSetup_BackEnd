package com.upc.gessi.automation.domain.respositories;

import com.upc.gessi.automation.domain.models.Iteration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IterationRepository extends CrudRepository<Iteration, Integer> {

    Iteration findByName(String name);
}
