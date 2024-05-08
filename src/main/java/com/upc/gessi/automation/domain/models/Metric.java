package com.upc.gessi.automation.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Metric")
public class Metric {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "metric_seq")
    @SequenceGenerator(name="metric_seq", sequenceName="metric_id_seq", allocationSize=1)
    private Integer id;

    @Column(name="externalid")
    private Integer externalid;

    @Column(name="name")
    private String name;

}
