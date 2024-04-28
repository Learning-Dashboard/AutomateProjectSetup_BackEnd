package com.upc.gessi.automation.rest.DTO;

public class ProjectDTO {

    private Integer Id;
    private String name;
    private String subject;
    private Integer num_students;
    private String URL_github;
    private String URL_taiga;
    private String URL_sheets;

    private String config_id;


    public ProjectDTO(String name, String subject, String URL_github, String URL_taiga, String URL_sheets){
        this.name=name;
        this.subject=subject;
        this.URL_github=URL_github;
        this.URL_taiga=URL_taiga;
        this.URL_sheets=URL_sheets;
    }

    public String getName(){
        return name;
    }

    public String getSubject(){
        return subject;
    }

    public String getURL_github(){
        return URL_github;
    }

    public String getURL_taiga(){
        return URL_taiga;
    }

    public String getURL_sheets(){
        return URL_sheets;
    }


}
