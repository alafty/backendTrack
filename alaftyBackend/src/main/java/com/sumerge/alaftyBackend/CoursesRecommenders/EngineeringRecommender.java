package com.sumerge.alaftyBackend.CoursesRecommenders;

import com.sumerge.alaftyBackend.Interfaces.CourseRecommender;
import com.sumerge.alaftyBackend.Models.Course;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Primary
public class EngineeringRecommender implements CourseRecommender {


    public List<Course> recommendedCourses() {
        System.out.println("Engineering courses");
        return List.of(new Course(), new Course());
    }

}
