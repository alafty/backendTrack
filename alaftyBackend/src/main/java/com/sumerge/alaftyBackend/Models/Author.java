package com.sumerge.alaftyBackend.Models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    public String name;
    public String email;
    public Date birthDate;

    @ManyToMany
    public List<Course> courses;
}
