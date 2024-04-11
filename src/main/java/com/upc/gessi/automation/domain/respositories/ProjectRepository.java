package com.upc.gessi.automation.domain.respositories;

import com.upc.gessi.automation.domain.models.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project,Integer> {

    List<Project> findByNameAndSubject(String name,String subject);

}
