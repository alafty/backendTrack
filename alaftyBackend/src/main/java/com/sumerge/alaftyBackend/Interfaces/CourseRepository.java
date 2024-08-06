package com.sumerge.alaftyBackend.Interfaces;

import com.sumerge.alaftyBackend.Models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {


    public Course findByDescription(String description);

}
