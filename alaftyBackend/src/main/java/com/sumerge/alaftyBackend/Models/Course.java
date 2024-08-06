package com.sumerge.alaftyBackend.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;
import java.util.UUID;

@Entity
public class Course {
    @Id
    public int id;
    public String name;
    public String description;
    public int credit;
    public int assessment;
    public int authors;
    public int ratings;

    public Course (int id, String name, String description, int credit, int assessment, int ratings, int authors) {
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
