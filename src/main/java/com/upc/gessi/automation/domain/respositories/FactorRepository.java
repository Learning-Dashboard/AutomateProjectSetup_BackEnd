package com.upc.gessi.automation.domain.respositories;

import com.upc.gessi.automation.domain.models.Factor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactorRepository extends CrudRepository<Factor,Integer>{

   List<Factor> findAllByProject(String project);
}
