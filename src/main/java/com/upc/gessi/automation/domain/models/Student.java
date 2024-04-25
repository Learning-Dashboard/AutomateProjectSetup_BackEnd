package com.upc.gessi.automation.domain.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table (name = "Student")
public class Student implements Serializable {

    @Id
    @Column (name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
    @SequenceGenerator(name="student_seq", sequenceName="student_id_seq", allocationSize=1)
    private Integer id;

    @Column (name = "name")
    private String name;

    @Column (name = "project")
    private Integer project;

    @Column (name = "username_github")
    private String username_github;

    @Column (name = "username_taiga")
    private String username_taiga;

    @Column (name = "username_sheets")
    private String username_sheets;

    public Student(String name, Integer id_project, String username_github,String username_taiga,String username_sheets){
        this.name= name;
        this.project= id_project;
        this.username_github= username_github;
        this.username_taiga= username_taiga;
        this.username_sheets= username_sheets;
    }

    public Student() {

    }

    public String getName() {
        return name;
    }

    public String getUsername_github(){
        return username_github;
    }

    public String getUsername_taiga(){
        return username_taiga;
    }

    public Integer getProject() {
        return project;
    }
}
