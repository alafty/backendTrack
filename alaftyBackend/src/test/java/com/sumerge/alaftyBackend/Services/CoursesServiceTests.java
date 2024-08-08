package com.sumerge.alaftyBackend.Services;
import com.sumerge.alaftyBackend.Interfaces.CourseRecommender;
import com.sumerge.alaftyBackend.Interfaces.CourseRepository;
import com.sumerge.alaftyBackend.Models.Course;
import com.sumerge.alaftyBackend.Models.CourseDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CoursesServiceTests {

    @Mock
    EntityManager entityManager;
    @Mock
    Query query;
    @Mock
    CourseRepository repo;
    @Mock
    CourseRecommender recommender;
    @Mock
    ModelMapper mapper = new ModelMapper();
    @InjectMocks
    CourseService courseService;

    @Test
    void getRecommendedCourses_success() throws Exception {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course());
        when(recommender.recommendedCourses()).thenReturn(courses);

        List<Course> returnedCourses = courseService.getRecommendedCourses();

        assertEquals(courses, returnedCourses);
    }

    @Test
    void getRecommendedCourses_emptyArray_failure() throws Exception {
        List<Course> courses = new ArrayList<>();

        when(recommender.recommendedCourses()).thenReturn(courses);
        assertThrows(RuntimeException.class, () -> courseService.getRecommendedCourses());
    }

    @Test
    void getRecommendedCourses_unmatchingArray_failure() throws Exception {
        List<Course> courses = new ArrayList<>();

        when(recommender.recommendedCourses()).thenReturn(courses);

        assertThrows(EntityNotFoundException.class, () -> courseService.getRecommendedCourses());
    }

    @Test
    void getCourseByDescription_success() throws Exception {
        int id = 1;
        String name = "nameTest";
        String desc = "test";

        Course course = new Course();
        course.id = id;
        course.description = desc;
        course.name = name;

        CourseDto courseDto = new CourseDto();
        courseDto.id = id;
        courseDto.description = desc;
        courseDto.name = name;

        when(repo.findByDescription(desc)).thenReturn(Optional.of(course));
        CourseDto returnedCourse = courseService.getCourseByDescription(desc);

        assertEquals(courseDto.description, returnedCourse.description);
    }

    @Test
    void getCourseByDescription_descriptionNotFound_fail() throws Exception {
        String desc = "test";

        when(repo.findByDescription(desc)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> courseService.getCourseByDescription(desc));
    }
    @Test
    void getCourseById_success() throws Exception {
        Course course = new Course();
        CourseDto courseDto = new CourseDto();
        int id = 1;
        String name = "nameTest";
        String desc = "test";
        course.id = id;
        course.description = desc;
        course.name = name;
        courseDto.id = id;
        courseDto.description = desc;
        courseDto.name = name;

        when(repo.existsById(id)).thenReturn(true);
        when(repo.findById(id)).thenReturn(Optional.of(course));

        CourseDto returnedCourse = courseService.getCourse(id);

        assertEquals(courseDto.id, returnedCourse.id);
    }

    @Test
    void getCourseById_iDNotFound_fail() throws Exception {
        int id = 1;

        when(repo.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> courseService.getCourse(id));
    }

    @Test
    void deleteCourse_success() throws Exception {
        int courseId = 1;
        when(repo.existsById(courseId)).thenReturn(true);

        assertTrue(courseService.deleteCourse(courseId));
    }

    @Test
    void deleteCourse_idNotFound_failure() throws Exception {
        int courseId = 1;
        when(repo.existsById(courseId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> courseService.deleteCourse(courseId));
        verify(repo, never()).deleteById(courseId);
    }

    @Test
    void addCourse_success() throws Exception {
        Course course = new Course();
        course.id= 1;
        course.name = "New Test Course";
        course.description = "New Description";

        when(repo.save(any(Course.class))).thenReturn(course);

        // Act
        boolean result = courseService.addCourse(course);

        // Assert
        assertTrue(result);
        verify(repo, times(1)).save(any(Course.class));
    }

    @Test
    void addCourse_dbProblem_failure() {
        Course course = new Course();
        course.id= 1;
        course.name = "New Test Course";
        course.description = "New Description";

        doThrow(new RuntimeException("Database error")).when(repo).save(any(Course.class));

        assertThrows(RuntimeException.class, () -> courseService.addCourse(course));
    }

    @Test
    void updateCourseDescription_success() throws Exception {
        int id = 1;
        String desc = "New Test Course Description";
        Course course = new Course();
        course.id= 1;
        course.name = "Test Course";
        course.description = "Old Description";

        when(repo.existsById(id)).thenReturn(true);
        when(repo.findById(id)).thenReturn(Optional.of(course));
        assertTrue(courseService.updateCourseDescription(id, desc));
        assertEquals(course.getDescription(), desc);
    }

    @Test
    void updateCourseDescription_idNotFound_failure() throws Exception {
        int courseId = 1;
        String newDescription = "Updated Description";

        when(repo.existsById(courseId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> courseService.updateCourseDescription(courseId, newDescription));
        //verify(repo, never()).save(any(Course.class));
    }

//    @Test
//    void getAllcourses_pageExists_success() throws Exception {
//        int pageNumber = 1;
//        int pageSize = 3;
//        List<Course> courses = Arrays.asList(
//                new Course(1, "Course 1", "Description 1", 3, 90, 10, 5),
//                new Course(2, "Course 2", "Description 2", 3, 85, 15, 4),
//                new Course(3, "Course 3", "Description 3", 3, 88, 12, 4)
//        );
//
//
//        when(entityManager.createQuery(anyString())).thenReturn(query);
//        when(query.setFirstResult((pageNumber - 1) * pageSize)).thenReturn(query);
//        when(query.setMaxResults(pageSize)).thenReturn(query);
//        when(query.getResultList()).thenReturn(courses);
//
//        List<Course> returnedCourses = courseService.getAllCourses(pageNumber, pageSize);
//
//        assertEquals(courses.size(), returnedCourses.size());
//        assertEquals(courses.get(0).getName(), returnedCourses.get(0).getName());
//        verify(entityManager, times(1)).createQuery(anyString());
//        verify(query, times(1)).setFirstResult((pageNumber - 1) * pageSize);
//        verify(query, times(1)).setMaxResults(pageSize);
//        verify(query, times(1)).getResultList();
//
//    }

//    @Test
//    void getAllcourses_pageDoesNotExist_failure() throws Exception {
//        int pageNumber = 100;
//        int pageSize = 10;
//        List<Course> courses = new ArrayList<>();
//
//        when(entityManager.createQuery(anyString())).thenReturn(query);
//        when(query.setFirstResult((pageNumber - 1) * pageSize)).thenReturn(query);
//        when(query.setMaxResults(pageSize)).thenReturn(query);
//        when(query.getResultList()).thenReturn(courses);
//
//        List<Course> returnedCourses = courseService.getAllCourses(pageNumber, pageSize);
//
//        assertEquals(0, returnedCourses.size());
//        verify(entityManager, times(1)).createQuery(anyString());
//        verify(query, times(1)).setFirstResult((pageNumber - 1) * pageSize);
//        verify(query, times(1)).setMaxResults(pageSize);
//        verify(query, times(1)).getResultList();
//
//    }
}
