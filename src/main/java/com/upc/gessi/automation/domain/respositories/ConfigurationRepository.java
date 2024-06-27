package com.upc.gessi.automation.domain.respositories;

import com.upc.gessi.automation.domain.models.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends CrudRepository<Configuration,Integer> {

}
