package com.upc.gessi.automation.domain.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "Subject")
public class Subject implements Serializable {

    @Id
    @Column(name ="name")
    private String name;

    @Column(name="te_github")
    private Boolean github;

    @Column(name="token_github")
    private String token_github;

    @Column(name ="te_taiga")
    private Boolean taiga;

    @Column(name = "te_sheets")
    private Boolean sheets;

    public String getToken(){
        return token_github;
    }

}
