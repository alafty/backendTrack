package com.sumerge.alaftyBackend.Services;

import com.sumerge.alaftyBackend.Interfaces.CourseRecommender;
import com.sumerge.alaftyBackend.Models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    CourseRecommender courseRecommender;

    @Autowired
    public CourseService(CourseRecommender courseRecommender) {
        this.courseRecommender = courseRecommender;
    }

    public List<Course> getRecommendedCourses() {
        return courseRecommender.recommendedCourses();
    }
}
