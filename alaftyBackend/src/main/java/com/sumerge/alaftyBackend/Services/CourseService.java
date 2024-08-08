package com.sumerge.alaftyBackend.Services;

import com.sumerge.alaftyBackend.Interfaces.CourseRepository;
import com.sumerge.alaftyBackend.Interfaces.CourseRecommender;
import com.sumerge.alaftyBackend.Models.Course;
import com.sumerge.alaftyBackend.Models.CourseDto;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
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

    ModelMapper modelMapper = new ModelMapper();


    @Autowired
    public CourseService(CourseRecommender courseRecommender, CourseRepository coursesRepo) {
        this.courseRecommender = courseRecommender;
        this.coursesRepo = coursesRepo;
        TypeMap<Course, CourseDto> propertyMapper = modelMapper.createTypeMap(Course.class, CourseDto.class);
        propertyMapper.addMapping(Course::getId, CourseDto::setId);
        propertyMapper.addMapping(Course::getName, CourseDto::setName);
        propertyMapper.addMapping(Course::getDescription, CourseDto::setDescription);
    }

    public List<Course> getRecommendedCourses() {
        return courseRecommender.recommendedCourses();
    }

    public CourseDto getCourseByDescription(String description) {
        Course _course = coursesRepo.findByDescription(description);
        return modelMapper.map(_course, CourseDto.class);
    }

//    public List getAllCourses(int pageNumber, int pageSize) {
//        Query query = entityManager.createQuery(findAllQuery);
//        query.setFirstResult((pageNumber-1) * pageSize);
//        query.setMaxResults(pageSize);
//        return query.getResultList();
//    }

    public CourseDto getCourse(int id) {
        Optional<Course> _temp = coursesRepo.findById(id);
        if(_temp.isPresent()) {
            Course _course = _temp.get();
            CourseDto _courseDTO = modelMapper.map(_course, CourseDto.class);
            return _courseDTO;
        }
        return null;
    }

    public boolean deleteCourse(int id) {
        coursesRepo.existsById(id);
        coursesRepo.deleteById(id);
        return true;
    }

    public boolean addCourse(Course course) {
        coursesRepo.save(course);
        return true;
    }

    public boolean updateCourseDescription(int id, String description ) {
        Optional<Course> _temp = coursesRepo.findById(id);
        if(_temp.isPresent()) {
            Course _c = _temp.get();
            _c.description = description;
            coursesRepo.save(_c);
            return true;
        }
        return false;
    }
}
