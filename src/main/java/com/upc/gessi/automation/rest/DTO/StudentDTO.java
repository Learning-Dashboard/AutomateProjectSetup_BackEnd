package com.upc.gessi.automation.rest.DTO;

import com.upc.gessi.automation.domain.models.Student;
import com.upc.gessi.automation.domain.respositories.StudentRepository;
import com.upc.gessi.automation.rest.IStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class StudentDTO implements IStudent {

    @Autowired
    private StudentRepository StudentRep;

    @Transactional
    @Override
    public Student save(Student student) {
        return StudentRep.save(student);
    }

    @Transactional(readOnly = true)
    @Override
    public Student findById(Integer id) {
        return StudentRep.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Student student) {
        StudentRep.delete(student);
    }
}
