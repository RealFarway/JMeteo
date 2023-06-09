package com.springframework.jmeteowebapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Role")
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Role(){

    }

    public Role(String name) {
        super();
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
