package com.upc.gessi.automation.domain.models;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Entity
@Table(name="Configuration")
public class Configuration {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "config_seq")
    @SequenceGenerator(name="config_seq", sequenceName="config_id_seq", allocationSize=1)
    private Integer id;

    @Column(name = "timestamp")
    private String time;

    @Column(name = "status")
    private Integer status;

    @Column(name = "tasks")
    private Integer tasks;

    public Configuration() {

    }

    public Configuration(String time, Integer status, Integer tasks){
        this.time = time;
        this.status = status;
        this.tasks = tasks;
    }

    public Integer getId() {
        return id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
