package com.upc.gessi.automation.domain.respositories;

import com.upc.gessi.automation.domain.models.Subject;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubjectRepository extends CrudRepository<Subject,Integer> {

    Subject findByName(String name);

}
