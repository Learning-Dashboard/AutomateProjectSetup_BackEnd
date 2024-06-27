package com.upc.gessi.automation.rest.DTO;

public class IterationDTO {

    private String name;

    private String subject;

    private String fromData;

    private String toData;

    private String projects;

    public IterationDTO(){

    }

    public IterationDTO(String name, String subject, String fromData, String toData, String projects){
        this.name = name;
        this.subject = subject;
        this.fromData = fromData;
        this.toData = toData;
        this.projects = projects;
    }

    public String getName(){ return name;}

    public String getSubject(){ return subject;}

    public String getFromData(){ return fromData;}

    public String getToData(){ return toData;}

    public String getProjects(){ return projects;}
}
