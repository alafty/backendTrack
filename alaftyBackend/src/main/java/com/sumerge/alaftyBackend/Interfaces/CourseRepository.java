package com.sumerge.alaftyBackend.Interfaces;

import com.sumerge.alaftyBackend.Models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {


    public Optional<Course> findByDescription(String description);

}
