package com.sumerge.alaftyBackend.Models;


import jakarta.persistence.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    public String name;
    public String description;
    public int credit;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    public Assessment assessment;
    @ManyToMany(cascade = CascadeType.ALL)
    public Set<Author> authors;
    @OneToMany(cascade = CascadeType.ALL)
    public Set<Rating> ratings;

    public Course (int id, String name, String description, int credit, Assessment assessment, Set<Rating> ratings, Set<Author> authors) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.credit = credit;
        this.assessment = assessment;
        this.ratings = ratings;
        this.authors = authors;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Course() {

    }
}
