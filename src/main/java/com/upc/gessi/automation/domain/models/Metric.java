package com.upc.gessi.automation.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Metric")
public class Metric {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "metric_seq")
    @SequenceGenerator(name="metric_seq", sequenceName="metric_id_seq", allocationSize=1)
    private Integer id;

    @Column(name="externalid")
    private Integer externalid;

    @Column(name="name")
    private String name;

    @Column(name="factor")
    private String factor;

    @Column(name="project")
    private String project;


    public Metric(Integer externalid, String name, String project) {
        this.externalid = externalid;
        this.name = name;
        this.project = project;
    }

    public void setFactor(String factor){
        System.out.print("WWWWWWWWW");
        this.factor = factor;
    }

    public String getFactor(){
        return factor;
    }

    public Integer getExternalid(){
        return externalid;
    }

    public String getProject(){
        return project;
    }

    public String getName(){
        return name;
    }

    public Metric() {

    }
}
