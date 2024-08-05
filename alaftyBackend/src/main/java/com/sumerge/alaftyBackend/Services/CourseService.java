package com.sumerge.alaftyBackend.Services;

import com.sumerge.alaftyBackend.Interfaces.CourseRepository;
import com.sumerge.alaftyBackend.Interfaces.CourseRecommender;
import com.sumerge.alaftyBackend.Models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    CourseRecommender courseRecommender;
    private CourseRepository coursesRepo;
    @PersistenceContext
    EntityManager entityManager;
    @Value("${course.query.findAll}")
    String findAllQuery;


    @Autowired
    public CourseService(CourseRecommender courseRecommender, CourseRepository coursesRepo) {
        this.courseRecommender = courseRecommender;
        this.coursesRepo = coursesRepo;
    }

    public List<Course> getRecommendedCourses() {
        return courseRecommender.recommendedCourses();
    }

    public List getAllCourses(int pageNumber, int pageSize) {
        Query query = entityManager.createQuery(findAllQuery);
        query.setFirstResult((pageNumber-1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public Course getCourse(int id) {
        Optional<Course> _temp = coursesRepo.findById(id);
        if(_temp.isPresent()) {
            Course _c = _temp.get();
            return _c;
        }
        return null;
    }

    public void deleteCourse(int id) {
        coursesRepo.deleteById(id);
    }

    public void addCourse(Course course) {
        coursesRepo.save(course);
    }

    public void updateCourseDescription(int id, String description ) {
        Optional<Course> _temp = coursesRepo.findById(id);
        if(_temp.isPresent()) {
            Course _c = _temp.get();
            _c.description = description;
            coursesRepo.save(_c);
        }
    }
}
