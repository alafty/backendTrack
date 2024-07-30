package com.sumerge.alaftyBackend.CoursesRecommenders;

import com.sumerge.alaftyBackend.Interfaces.CourseRecommender;
import com.sumerge.alaftyBackend.Models.Course;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("managementRecommender")
public class ManagementRecommender implements CourseRecommender {

    public List<Course> recommendedCourses() {
        System.out.println("Management courses");
        return List.of(new Course("M1"), new Course("M2"));
    }

}
