package com.sumerge.alaftyBackend.Controllers;

import com.sumerge.alaftyBackend.Models.Course;
import com.sumerge.alaftyBackend.Models.CourseDto;
import com.sumerge.alaftyBackend.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String error() {
        return "error";
    }

    @GetMapping("view/{id}/")
    public CourseDto getCourse(@PathVariable String id) {
        System.out.println(id);
        return courseService.getCourse(Integer.parseInt(id));
    }

    @GetMapping("view/{description}")
    public CourseDto getCourseByDescription(@PathVariable String description) {
        return courseService.getCourseByDescription(description);
    }

    @GetMapping("view/all/{pageNumber}/")
    public List getAllCourses(@PathVariable int pageNumber, @RequestParam int pageSize) {
        return courseService.getAllCourses(pageNumber, pageSize);
    }


    @GetMapping("discover/")
    public List<Course> getRecommendedCourses() {
        return courseService.getRecommendedCourses();
    }

    @PutMapping("update/{id}")
    public boolean updateCourseDescription(@PathVariable int id, @RequestBody String  newDescription) {
        try {
            courseService.updateCourseDescription(id, newDescription);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @PostMapping("add/")
    public boolean addCourse(@RequestBody CourseDto course) {
        try {
            courseService.addCourse(course);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @DeleteMapping("delete/{id}")
    public boolean deleteCourse(@PathVariable String id) {
        try {
            courseService.deleteCourse(Integer.parseInt(id));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
