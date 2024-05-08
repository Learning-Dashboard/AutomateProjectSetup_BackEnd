package com.upc.gessi.automation.domain.controllers;

import com.upc.gessi.automation.domain.models.Project;
import com.upc.gessi.automation.domain.respositories.StudentRepository;
import com.upc.gessi.automation.rest.DTO.ProjectDTO;
import com.upc.gessi.automation.rest.DTO.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.upc.gessi.automation.domain.models.Student;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository StudentRep;

    public List<String> getAll(){
        Iterable<Student> studentsIt = StudentRep.findAll();
        studentsIt.forEach(student -> System.out.println(student));
        List<String> studentsList = new ArrayList<>();
        studentsIt.forEach(student -> studentsList.add(student.getName()));
        return studentsList;
    }

    public void createStudent(StudentDTO sDTO){
        Student student = new Student(sDTO.getName(),sDTO.getProject(),sDTO.getUsername_github(),sDTO.getUsername_taiga(),sDTO.getUsername_sheets());
        StudentRep.save(student);
    }

    public ArrayList<String> getStudentsGithubProject(Integer id_proj){
        List<Student> students = StudentRep.findAllByProject(id_proj);
        ArrayList<String> github_usernames = new ArrayList<>();
        for(Student student : students){
            System.out.print(student.getUsername_github());
            github_usernames.add(student.getUsername_github());
        }
        return github_usernames;
    }

    public ArrayList<String> getStudentsTaigaProject(Integer id_proj){
        List<Student> students = StudentRep.findAllByProject(id_proj);
        ArrayList<String> taiga_usernames = new ArrayList<>();
        for(Student student : students){
            System.out.print(student.getUsername_taiga());
            taiga_usernames.add(student.getUsername_taiga());
        }
        return taiga_usernames;
    }

    public List<StudentDTO> getStudentsProject(Integer id_proj){
        List<Student> students = StudentRep.findAllByProject(id_proj);
        List<StudentDTO> stu = new ArrayList<>();
        for(Student student : students){
            if(student.getProject() == id_proj) {
                StudentDTO studen = new StudentDTO(student.getName(), student.getUsername_github(), student.getUsername_taiga(), student.getUsername_sheets());
                stu.add(studen);
            }
        }
        return stu;
    }
}
