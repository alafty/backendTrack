package com.sumerge.alaftyBackend.Controllers;

import com.sumerge.alaftyBackend.Models.Course;
import com.sumerge.alaftyBackend.Models.CourseDto;
import com.sumerge.alaftyBackend.Services.CourseService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("courses/")
public class CoursesController {

    private CourseService courseService;

    @Autowired
    public CoursesController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/error")
    public String getError() {
        return "error";
    }

    @GetMapping("view/byID/{id}")
    public CourseDto getCourse(@PathVariable String id) throws RuntimeException {
        if(id == "0" || id == null) {
            throw new IllegalArgumentException("Invalid ID");
        }
        CourseDto _tempCourse = courseService.getCourse(Integer.parseInt(id));
        if(_tempCourse == null || _tempCourse.id == 0 || _tempCourse.name == null || _tempCourse.description == null){
            throw new EntityNotFoundException("Error Mapping Course");
        }
        return _tempCourse;
    }

    @GetMapping("view/{description}")
    public CourseDto getCourseByDescription(@PathVariable String description) throws RuntimeException {
        if(description == null || description.isEmpty()){
            throw new IllegalArgumentException("No Description Provided");
        }
        CourseDto _tempCourse =  courseService.getCourseByDescription(description);
        if(_tempCourse == null || _tempCourse.id == 0 || _tempCourse.name == null || _tempCourse.description == null){
            throw new EntityNotFoundException("Error Mapping Course");
        }
        return _tempCourse;
    }

    @GetMapping("view/all/{pageNumber}/")
    public List getAllCourses(@PathVariable int pageNumber, @RequestParam int pageSize) {
        return courseService.getAllCourses(pageNumber, pageSize);
    }


    @GetMapping("discover/")
    public List<Course> getRecommendedCourses() throws RuntimeException {
        List<Course> _tempCourses = courseService.getRecommendedCourses();
        if(_tempCourses == null || _tempCourses.isEmpty()){
            throw new EntityNotFoundException("No recommended courses found");
        }
        return _tempCourses;
    }

    @PutMapping("update/{id}")
    public void updateCourseDescription(@PathVariable int id, @RequestBody String newDescription) throws RuntimeException {
        if(id == 0 || newDescription == null){
            throw new IllegalArgumentException("No ID or Description provided");
        }
        if(!courseService.updateCourseDescription(id, newDescription)){
            throw new EntityNotFoundException("Course Not Found");
        }
    }

    @PostMapping("add/")
    public void addCourse(@RequestBody Course course) {
        if(course.description == null || course.name == null || course.authors == null || course.assessment == null || course.credit == 0 || course.ratings == null){
            throw new IllegalArgumentException("Course Data Missing");
        }
        boolean result = courseService.addCourse(course);
        System.out.println(result);
        if(!result){
            throw new EntityExistsException("Course Already Exists");
        }
    }

    @DeleteMapping("delete/{id}")
    public void deleteCourse(@PathVariable String id) {
        if(!courseService.deleteCourse(Integer.parseInt(id))){
            throw new EntityNotFoundException("Course Not Found");
        }
    }
}
