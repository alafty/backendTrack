package com.sumerge.alaftyBackend.Services;

import com.sumerge.alaftyBackend.Interfaces.CourseRepository;
import com.sumerge.alaftyBackend.Interfaces.CourseRecommender;
import com.sumerge.alaftyBackend.Models.Course;
import com.sumerge.alaftyBackend.Models.CourseDto;
import jakarta.persistence.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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

    public List<Course> getRecommendedCourses() throws RuntimeException {
        List<Course> returnList = courseRecommender.recommendedCourses();
        if(returnList == null || returnList.isEmpty()) {
            throw new EntityNotFoundException("No recommended courses found");
        }
        return returnList;
    }

    public CourseDto getCourseByDescription(String description) throws RuntimeException {
        Optional<Course> _course = coursesRepo.findByDescription(description);
        if(_course.isEmpty()) {
            throw new EntityNotFoundException("Course not found");
        }
        CourseDto courseDto = modelMapper.map(_course, CourseDto.class);
        if(courseDto.id == 0 || courseDto.description == null || courseDto.name == null) {
            throw new RuntimeException("Error Mapping Course");
        }
        return courseDto;
    }

//    public List getAllCourses(int pageNumber, int pageSize) {
//        Query query = entityManager.createQuery(findAllQuery);
//        query.setFirstResult((pageNumber-1) * pageSize);
//        query.setMaxResults(pageSize);
//        return query.getResultList();
//    }

    public CourseDto getCourse(int id) throws RuntimeException {
        if(!coursesRepo.existsById(id)) {
            throw new EntityNotFoundException("Course not found");
        }
        Optional<Course> _temp = coursesRepo.findById(id);
        Course _course = _temp.get();
        CourseDto _courseDTO = modelMapper.map(_course, CourseDto.class);
        if(_courseDTO.id == 0 || _courseDTO.description == null || _courseDTO.name == null) {
            throw new RuntimeException("Error Mapping Course");
        }
        return _courseDTO;
    }

    public boolean deleteCourse(int id) throws RuntimeException{
        if(!coursesRepo.existsById(id)) {
            throw new EntityNotFoundException("Course not found");
        }
        coursesRepo.deleteById(id);
        return true;
    }

    public boolean addCourse(Course course) throws RuntimeException {
        if(coursesRepo.existsById(course.id)) {
            throw new EntityExistsException("Course already exists");
        }
        coursesRepo.save(course);
        return true;
    }

    public boolean updateCourseDescription(int id, String description ) throws RuntimeException {
        if(!coursesRepo.existsById(id)) {
            throw new EntityNotFoundException("Course not found");
        }
        Optional<Course> _temp = coursesRepo.findById(id);
        Course _c = _temp.get();
        _c.description = description;
        coursesRepo.save(_c);
        return true;
    }
}
