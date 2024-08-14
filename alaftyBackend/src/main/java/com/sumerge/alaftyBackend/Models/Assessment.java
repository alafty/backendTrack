package com.sumerge.alaftyBackend.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    public String content;
}
