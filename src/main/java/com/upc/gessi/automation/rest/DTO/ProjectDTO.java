package com.upc.gessi.automation.rest.DTO;

public class ProjectDTO {

    private Integer Id;
    private String name;
    private String subject;
    private Integer num_students;
    private String URL_github;
    private String URL_taiga;
    private String URL_sheets;

    private String status;
    private String ID_github;
    private String ID_taiga;

    public ProjectDTO(String name, String subject, String URL_github, String URL_taiga, String URL_sheets, String ID_github, String ID_taiga){
        this.name=name;
        this.subject=subject;
        this.URL_github=URL_github;
        this.URL_taiga=URL_taiga;
        this.URL_sheets=URL_sheets;
        this.ID_github=ID_github;
        this.ID_taiga=ID_taiga;
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

    public String getID_github(){return ID_github;}

    public String getID_taiga(){return ID_taiga;}

}
