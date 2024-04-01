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
    @Column
    private String name;

    @Column
    private Boolean github;

    @Column
    private String token_github;

    @Column
    private Boolean taiga;

    @Column
    private Boolean sheets;


}
