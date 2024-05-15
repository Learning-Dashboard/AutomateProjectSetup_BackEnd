package com.upc.gessi.automation.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name="Factor")
public class Factor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "factor_seq")
    @SequenceGenerator(name="factor_seq", sequenceName="factor_id_seq", allocationSize=1)
    private Integer id;

    @Column(name="externalid")
    private String externalid;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="source")
    private String source;

    @Column(name="category")
    private String category;

    @Column(name="project")
    private String project;

    public Factor(String externalid,String name,String description,String source,String category,String project){
        this.externalid= externalid;
        this.name= name;
        this.description=description;
        this.source= source;
        this.category= category;
        this.project = project;
    }

    public Factor() {

    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public String getSource(){
        return source;
    }

    public String getCategory(){
        return category;
    }

    public String getProject(){
        return project;
    }

    public String getExternalid(){
        return externalid;
    }

}
