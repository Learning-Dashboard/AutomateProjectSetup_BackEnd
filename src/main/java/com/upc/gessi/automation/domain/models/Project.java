package com.upc.gessi.automation.domain.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Project")
public class Project implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
    @SequenceGenerator(name="project_seq", sequenceName="project_id_seq", allocationSize=1)
    private Integer id;

    @Column(name= "name")
    private String name;

    @Column(name= "subject")
    private String subject;

    @Column(name= "num_students")
    private Integer num_students;

    @Column(name= "URL_github")
    private String URL_github;

    @Column(name= "URL_taiga")
    private String URL_taiga;

    @Column(name= "URL_sheets", nullable = true)
    private String URL_sheets;

    public Project() {

    }
    public Project(String name, String subject, String URL_g, String URL_t, String URL_s){
        this.name= name;
        this.subject= subject;
        this.URL_github=URL_g;
        this.URL_taiga=URL_t;
        this.URL_sheets=URL_s;
    }


}
