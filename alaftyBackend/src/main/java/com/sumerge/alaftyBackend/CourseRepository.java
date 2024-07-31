package com.sumerge.alaftyBackend;

import com.sumerge.alaftyBackend.Models.Course;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class CourseRepository {
    JdbcTemplate template;
    @Value("${course.query.find.by.id}")
    String viewCourseQuery;
    @Value("${course.query.delete.by.id}")
    String deleteCourseQuery;
    @Value("${course.query.update.description}")
    String updateCourseQuery;
    @Value("${course.query.insert}")
    String insertCourseQuery;

    public CourseRepository(JdbcTemplate template) {
        this.template = template;
    }

    public Course find(int id) {
        return template.queryForObject(viewCourseQuery,
                (rs, rowNum) -> new Course(rs.getInt("id"),
                                            rs.getString("name"),
                                            rs.getString("description"),
                                            rs.getInt("credit"),
                                            rs.getInt("assessment"),
                                            rs.getInt("authors"),
                                            rs.getInt("ratings")), id);
    }

    public void delete(int id) {
        template.update(deleteCourseQuery, id);
        System.out.println("Deleted Course: " + id);
    }

    public void add(Course course) {
        template.update(insertCourseQuery, course.id, course.name, course.description, course.credit, course.assessment, course.authors, course.ratings);
    }

    public void update(int id, String description) {
        template.update(updateCourseQuery, description, id);
    }
}
