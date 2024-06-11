package com.upc.gessi.automation.domain.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table
public class Iteration implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "iteration_seq")
    @SequenceGenerator(name="iteration_seq", sequenceName="iteration_id_seq", allocationSize=1)
    private Integer id;

    @Column(name = "externalid")
    private Integer externalId;

    @Column(name = "name")
    private String name;

    @Column(name = "subject")
    private String subject;

    @Column(name = "fromdate")
    private String fromDate;

    @Column(name = "todate")
    private String toDate;

    @Column(name = "projectids")
    private String projectIds;

    public Iteration(){

    }

    public Iteration(String name, String subject, String iniDate, String finalDate, String project){
        this.name = name;
        this.subject = subject;
        this.fromDate = iniDate;
        this.toDate = finalDate;
        this.projectIds = project;
    }
    public String getName(){ return name;}

    public String getSubject(){ return subject;}

    public String getFromData(){ return fromDate;}

    public String getToData(){ return toDate;}

    public String getProjects(){ return projectIds;}

    public void setProjectIds(String ids){
        this.projectIds = ids;
    }


}

