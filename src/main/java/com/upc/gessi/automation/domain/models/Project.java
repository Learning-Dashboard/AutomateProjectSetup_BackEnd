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

    @Column(name= "status")
    private String Status;

    @Column(name= "ID_github")
    private String ID_github;

    @Column(name= "ID_taiga")
    private String ID_taiga;

    public Project() {

    }
    public Project(String name, String subject, String URL_g, String URL_t, String URL_s,String ID_g, String ID_t){
        this.name= name;
        this.subject= subject;
        this.URL_github=URL_g;
        this.URL_taiga=URL_t;
        this.URL_sheets=URL_s;
        this.ID_github=ID_g;
        this.ID_taiga=ID_t;
    }

    public Integer getId(){
        return id;
    }

    public String getID_github(){ return ID_github;}

    public String getID_taiga(){return ID_taiga;}


}
