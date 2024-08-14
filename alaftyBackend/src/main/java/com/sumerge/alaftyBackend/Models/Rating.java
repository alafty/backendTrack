package com.sumerge.alaftyBackend.Models;

import jakarta.persistence.*;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    public int number;
}
