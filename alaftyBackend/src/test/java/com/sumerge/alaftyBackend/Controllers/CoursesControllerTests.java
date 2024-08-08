package com.sumerge.alaftyBackend.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.alaftyBackend.Models.Course;
import com.sumerge.alaftyBackend.Models.CourseDto;
import com.sumerge.alaftyBackend.Services.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(CoursesController.class)
public class CoursesControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CourseService courseService;

    ObjectMapper objectMapper = new ObjectMapper();

    static String base_URI  = "http://localhost:8080/courses/";

    @Test
    void getError_badURI_success() throws Exception {
        mockMvc.perform(get(base_URI+"error"))
                .andExpect(status().isOk());
    }

    @Test
    void getCourse_id_success() throws Exception {
        int course_id = 13;
        when(courseService.getCourse(course_id)).thenReturn(new CourseDto());
        mockMvc.perform(get(base_URI+"view/byID/{id}", course_id))
                .andExpect(status().isOk());

    }

    @Test
    void getCourse_id_failure() throws Exception {
        int course_id = 13;
        when(courseService.getCourse(course_id)).thenReturn(null);
        mockMvc.perform(get(base_URI+"view/byID/{id}", course_id))
                .andExpect(status().isNotFound());

    }

    @Test
    void getCourseByDescription_success() throws Exception {
        String description = "test";
        when(courseService.getCourseByDescription(description)).thenReturn(new CourseDto());
        mockMvc.perform(get(base_URI+"view/{description}", description))
                .andExpect(status().isOk());
    }

    @Test
    void getCourseByDescription_fail() throws Exception {
        String description = "test";
        when(courseService.getCourseByDescription(description)).thenReturn(null);
        mockMvc.perform(get(base_URI+"view/{description}", description))
                .andExpect(status().isNotFound());
    }

//    @Test
//    void getAllCourses_pageExists_success() throws Exception {
//        int pageNumber = 4;
//        int pageSize = 5;
//        List<CourseDto> courseList = new ArrayList<>();
//
//        CourseDto course1 = new CourseDto();
//        course1.setId(1);
//        course1.setName("Course 1");
//        course1.setDescription("Description 1");
//
//        CourseDto course2 = new CourseDto();
//        course2.setId(2);
//        course2.setName("Course 2");
//        course2.setDescription("Description 2");
//
//        courseList.add(course1);
//        courseList.add(course2);
//
//        when(courseService.getAllCourses(pageNumber, pageSize)).thenReturn(courseList);
//        mockMvc.perform(get(base_URI+"view/all/{pageNumber}", pageNumber)
//                        .param("pageSize", String.valueOf(pageSize)))
//                .andExpect(status().isOk());
//    }

//    @Test
//    void getAllCourses_pageExists_failure() throws Exception {
//        int pageNumber = 4;
//        int pageSize = 5;
//        List<CourseDto> courseList = new ArrayList<>();
//
//        when(courseService.getAllCourses(pageNumber, pageSize)).thenReturn(courseList);
//        mockMvc.perform(get(base_URI+"view/all/{pageNumber}", pageNumber)
//                        .param("pageSize", String.valueOf(pageSize)))
//                .andExpect(status().isNotFound());
//    }

    @Test
    void getRecommendedCourses_success() throws Exception {
        List<Course> recommendedCourses = new ArrayList<>();
        Course _c1 = new Course();
        recommendedCourses.add(_c1);
        when(courseService.getRecommendedCourses()).thenReturn(recommendedCourses);
        mockMvc.perform(get(base_URI+"discover/"))
                .andExpect(status().isOk());
    }

    @Test
    void getRecommendedCourses_failure() throws Exception {
        when(courseService.getRecommendedCourses()).thenReturn(null);
        mockMvc.perform(get(base_URI+"discover/"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateCourseDescription_withBody_success() throws Exception {
        int course_id = 13;
        String description = "test";
        when(courseService.updateCourseDescription(course_id, description)).thenReturn(true);
        mockMvc.perform(put(base_URI+"update/{id}",course_id)
                        .content(description))
                .andExpect(status().isOk());
    }

    @Test
    void updateCourseDescription_withBody_failure() throws Exception {
        int course_id = 13;
        String description = "test";
        when(courseService.updateCourseDescription(course_id, description)).thenReturn(false);
        mockMvc.perform(put(base_URI+"update/{id}",course_id)
                        .content(description))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateCourseDescription_withoutBody_failure() throws Exception {
        int course_id = 13;
        String description = "test";
        when(courseService.updateCourseDescription(course_id, description)).thenReturn(false);
        mockMvc.perform(put(base_URI+"update/{id}",course_id))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteCourse_success() throws Exception {
        int course_id = 13;
        when(courseService.deleteCourse(course_id)).thenReturn(true);
        mockMvc.perform(delete(base_URI+"delete/{id}",course_id))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCourse_fail() throws Exception {
        int course_id = 13;
        when(courseService.deleteCourse(course_id)).thenReturn(false);
        mockMvc.perform(delete(base_URI+"delete/{id}",course_id))
                .andExpect(status().isNotFound());
    }

    @Test
    void addCourse_withBody_success() throws Exception {
        int course_id = 13;
        Course course = new Course();
        course.id = course_id;

        String body = objectMapper.writeValueAsString(course);
        when(courseService.addCourse(course)).thenReturn(true);
        mockMvc.perform(post(base_URI+"add/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    void addCourse_withBody_fail() throws Exception {
        int course_id = 13;
        Course course = new Course();
        course.id = (course_id);

        String body = objectMapper.writeValueAsString(course);
        when(courseService.addCourse(course)).thenReturn(false);
        mockMvc.perform(post(base_URI+"add/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addCourse_withoutBody_fail() throws Exception {
        int course_id = 13;
        Course course = new Course();
        course.id= (course_id);

        when(courseService.addCourse(course)).thenReturn(false);
        mockMvc.perform(post(base_URI+"add/"))
                .andExpect(status().isBadRequest());
    }

}
