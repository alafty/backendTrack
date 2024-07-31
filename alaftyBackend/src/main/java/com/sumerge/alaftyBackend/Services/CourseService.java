package com.sumerge.alaftyBackend.Services;

import com.sumerge.alaftyBackend.CourseRepository;
import com.sumerge.alaftyBackend.Interfaces.CourseRecommender;
import com.sumerge.alaftyBackend.Models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    CourseRecommender courseRecommender;
    CourseRepository coursesRepo;


    @Autowired
    public CourseService(CourseRecommender courseRecommender, CourseRepository coursesRepo) {
        this.courseRecommender = courseRecommender;
        this.coursesRepo = coursesRepo;
    }

    public List<Course> getRecommendedCourses() {
        return courseRecommender.recommendedCourses();
    }

    public Course getCourse(int id) {
        return coursesRepo.find(id);
    }

    public void deleteCourse(int id) {
        coursesRepo.delete(id);
    }

    public void addCourse(Course course) {
        coursesRepo.add(course);
    }

    public void updateCourseDescription(int id, String description ) {
        coursesRepo.update(id, description);
    }
}
