package com.upc.gessi.automation.domain.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table (name = "Student")
public class Student implements Serializable {

    @Id
    @Column (name = "id")
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column (name = "name")
    private String name;

    @Column (name = "id_project")
    private String id_project;

    @Column (name = "username_github")
    private String username_github;

    @Column (name = "username_taiga")
    private String username_taiga;

    @Column (name = "username_sheets")
    private String username_sheets;

    public String getName() {
        return name;
    }
}
