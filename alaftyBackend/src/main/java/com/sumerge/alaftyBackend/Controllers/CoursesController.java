package com.sumerge.alaftyBackend.Controllers;

import com.sumerge.alaftyBackend.Models.Course;
import com.sumerge.alaftyBackend.Models.CourseDto;
import com.sumerge.alaftyBackend.Services.CourseService;
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
    public CourseDto getCourse(@PathVariable String id) {
        CourseDto _tempCourse = courseService.getCourse(Integer.parseInt(id));
        if(_tempCourse == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return _tempCourse;
    }

    @GetMapping("view/{description}")
    public CourseDto getCourseByDescription(@PathVariable String description) {
        CourseDto _tempCourse =  courseService.getCourseByDescription(description);
        if(_tempCourse == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return _tempCourse;
    }

//    @GetMapping("view/all/{pageNumber}/")
//    public List getAllCourses(@PathVariable int pageNumber, @RequestParam int pageSize) {
//        return courseService.getAllCourses(pageNumber, pageSize);
//    }


    @GetMapping("discover/")
    public List<Course> getRecommendedCourses() {
        if(courseService.getRecommendedCourses() == null || courseService.getRecommendedCourses().size() == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return courseService.getRecommendedCourses();
    }

    @PutMapping("update/{id}")
    public boolean updateCourseDescription(@PathVariable int id, @RequestBody String newDescription) {
        if(courseService.updateCourseDescription(id, newDescription)){
            return true;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
    }

    @PostMapping("add/")
    public boolean addCourse(@RequestBody Course course) {
        if(!courseService.addCourse(course)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course already exists");
        }
        return true;
    }

    @DeleteMapping("delete/{id}")
    public boolean deleteCourse(@PathVariable String id) {
        if(!courseService.deleteCourse(Integer.parseInt(id))){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return true;
    }
}
