package com.upc.gessi.automation.rest.controllers;

import com.upc.gessi.automation.domain.controllers.ProjectController;
import com.upc.gessi.automation.domain.controllers.StudentController;
import com.upc.gessi.automation.domain.models.Student;
import com.upc.gessi.automation.rest.DTO.ProjectDTO;
import com.upc.gessi.automation.rest.DTO.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentRestController {

    @Autowired
    StudentController studentcontroller;

    @Autowired
    ProjectController projectController;

    @GetMapping
    public List<String>getAll(){
        System.out.println("AAAAAAAAAA");
        return studentcontroller.getAll();
    }

    /*@PostMapping(value = "/stu")
    public void putstudents(){
        studentcontroller.putStudents("bravo11","asw");
    }*/

    @GetMapping(value="/project")
    public List<StudentDTO> getStudentsProject(@RequestParam(name = "name") String name ,@RequestParam(name = "subject") String subject){
        var project_id = projectController.getId(name, subject);
        return studentcontroller.getStudentsProject(project_id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createStudents(@RequestBody List<Map<String,Object>> requestDataList){
        for(Map<String, Object> requestData: requestDataList) {
            String name = (String) requestData.get("name");
            String subject = (String) requestData.get("subject");
            System.out.print("AAAAAAAAAAAAAAAAAA\n");
            System.out.print(name);
            System.out.print(subject);

            List<Map<String, String>> members = (List<Map<String, String>>) requestData.get("members");
            System.out.print(members);

            var id_project = projectController.getId(name,subject);
            System.out.print(id_project);
            System.out.print(members.size());
            projectController.setNumStudents(members.size()+1,name,subject);

            for(Map<String,String> memberData: members){
                StudentDTO student = new StudentDTO(memberData.get("name"),id_project,memberData.get("githubUsername"),memberData.get("taigaUsername"),memberData.get("sheetsUsername"));
                studentcontroller.createStudent(student);
                //studentcontroller.putStudents(name,subject);
            }
        }


    }
    @PutMapping
    public void updateStudent(String project, String name, List<String> patata){

    }

    /*@PostMapping(value = "/student")
    @ResponseStatus(HttpStatus.CREATED)
    public void createStudent(StudentDTO studentRequest){
        try{
            String name = studentRequest.getName();
            Integer project = studentRequest.getProject();
            System.out.print(project);
            String username_github = studentRequest.getUsername_github();
            String username_taiga = studentRequest.getUsername_taiga();
            String username_sheets = studentRequest.getUsername_sheets();

            //System.out.print(" sdgfgfdg     "+name+ "   "+);

            StudentDTO sDTO = new StudentDTO(name,project,username_github,username_taiga,username_sheets);
            studentcontroller.createStudent(sDTO);

        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }*/






}
