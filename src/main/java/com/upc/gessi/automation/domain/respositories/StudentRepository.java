package com.upc.gessi.automation.domain.respositories;

import com.upc.gessi.automation.domain.models.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student,Integer> {

    List<Student> findAllByProject(Integer project);
}

