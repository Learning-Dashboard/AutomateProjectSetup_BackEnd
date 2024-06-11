package com.upc.gessi.automation.domain.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "Users")
public class User implements Serializable {

    @Id
    @Column(name="username")
    private String username;

    @Column(name = "externalid")
    private Integer externalId;

    @Column(name = "password")
    private String password;

    public User(String name, String password){
        this.username = name;
        this.password = password;
    }


    public User() {

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword(){ return password;}

    public Integer getExternalId(){ return externalId;}

    public void setExternalId(Integer id){
        this.externalId = id;
    }
}
