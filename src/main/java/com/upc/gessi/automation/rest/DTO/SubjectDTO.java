package com.upc.gessi.automation.rest.DTO;

public class SubjectDTO {

    private String name;

    private Boolean github;

    private String token_github;

    private Boolean taiga;

    private Boolean sheets;

    public SubjectDTO(String name, Boolean github, String token, Boolean taiga, Boolean sheets){
        this.name = name;
        this.github = github;
        this.token_github = token;
        this.taiga = taiga;
        this.sheets = sheets;
    }

    public String getName(){
        return name;
    }

    public Boolean getGithub(){ return github;}

    public String getToken_github(){ return token_github;}

    public Boolean getTaiga(){ return taiga;}

    public Boolean getSheets(){ return sheets;}

}
