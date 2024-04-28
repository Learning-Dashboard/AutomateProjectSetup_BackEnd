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

    @Column(name= "config_id")
    private Integer Config_id;



    public Project(String name, String subject, String url_github, String url_taiga, String url_sheets) {

    }
    public Project(String name, String subject, String URL_g, String URL_t, String URL_s,String ID_g, String ID_t){
        this.name= name;
        this.subject= subject;
        this.URL_github=URL_g;
        this.URL_taiga=URL_t;
        this.URL_sheets=URL_s;

    }

    public Project() {

    }

    public Integer getId(){
        return id;
    }

    public String getID_github(){
        String[] parts = URL_github.split("/");

        int index=-1;
        for(int i = 0; i< parts.length; i++){
            if(parts[i].equals("github.com")){
                index=i;
                break;
            }
        }
        if(index != -1){
            System.out.print(parts[index+1]);
            return parts[index+1];
        }
        return null;
    }

    public String getID_taiga(){
        String[] parts = URL_taiga.split("/");

        int index=-1;
        for(int i = 0; i< parts.length; i++){
            if(parts[i].equals("project")){
                index=i;
                break;
            }
        }
        if(index != -1){
            return parts[index+1];
        }
        return null;}

    public String getName(){
        return name;
    }

    public String getSubject(){
        return subject;
    }


}
