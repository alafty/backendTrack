package com.sumerge.alaftyBackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.alaftyBackend.Models.Assessment;
import com.sumerge.alaftyBackend.Models.Author;
import com.sumerge.alaftyBackend.Models.Course;
import com.sumerge.alaftyBackend.Models.Rating;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AlaftyBackendTasksApplicationTests {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private ApplicationContext applicationContext;
	private ObjectMapper objectMapper = new ObjectMapper();
	static String base_URI  = "http://localhost:8080/courses/";


	@Test
	void contextLoads() {
		assertNotNull(applicationContext.getApplicationName());
	}

	@Test
	@Order(1)
	void addCourse_detailsProvided_success() throws Exception {
		Course course = new Course();
		course.id = 3;
		course.name = "New Course";
		course.description = "The Newly Added Course";
		course.credit = 10;
		course.authors = new HashSet<>(List.of(new Author()));
		course.ratings = new HashSet<>(List.of(new Rating()));
		course.assessment = new Assessment();

		String json = objectMapper.writeValueAsString(course);
		mvc.perform(post(base_URI + "add/")
						.with(httpBasic("Alafty", "al13"))
						.header("x-validation-report", "true")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isOk());

		mvc.perform(get(base_URI + "view/byID/1")
						.with(httpBasic("Alafty", "al13"))
						.header("x-validation-report", "true"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("New Course"))
				.andExpect(jsonPath("$.description").value("The Newly Added Course"));
	}

	@Test
	void addCourse_detailsMissing_fail() throws Exception {
		Course course = new Course();
		course.id = 3;
		course.name = "New Course";
		course.description = "The Newly Added Course";
		course.credit = 10;

		String json = objectMapper.writeValueAsString(course);
		mvc.perform(post(base_URI + "add/")
						.with(httpBasic("Alafty", "al13"))
						.header("x-validation-report", "true")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isBadRequest());
	}

	@Test
	@Order(2)
	void viewCourseByPage_pageExists_success() throws Exception {
		mvc.perform(get(base_URI + "view/all/1/")
						.with(httpBasic("Alafty", "al13"))
						.header("x-validation-report", "true")
						.param("pageSize","1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("New Course"))
				.andExpect(jsonPath("$[0].description").value("The Newly Added Course"));
	}

	@Test
	void viewCourseByPage_pageDoesNotExist_fail() throws Exception {
		mvc.perform(get(base_URI + "view/all/3/")
						.with(httpBasic("Alafty", "al13"))
						.header("x-validation-report", "true")
						.param("pageSize","1"))
				.andExpect(status().isOk())
				.andExpect(content().json("[]"));
	}


	@Test
	void discoverCourses_authorized_success() throws Exception {
		mvc.perform(get(base_URI + "discover/")
				.with(httpBasic("Alafty", "al13"))
				.header("x-validation-report", "true"))
				.andExpect(status().isOk());
	}

	@Test
	void discoverCourses_authorized_fail() throws Exception {
		mvc.perform(get(base_URI + "discover/")
						.with(httpBasic("thief", "secret"))
						.header("x-validation-report", "true"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void discoverCourses_authenticated_fail() throws Exception {
		mvc.perform(get(base_URI + "discover/")
						.with(httpBasic("Alafty", "al13"))
						.header("x-validation-report", "false"))
				.andExpect(status().isForbidden());
	}

	@Test
	@Order(3)
	void viewCourseById_idExists_success() throws Exception {
		mvc.perform(get(base_URI + "view/byID/1")
						.with(httpBasic("Alafty", "al13"))
						.header("x-validation-report", "true"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("New Course"))
				.andExpect(jsonPath("$.description").value("The Newly Added Course"));
	}

	@Test
	void viewCourseById_idDoesNotExist_fail() throws Exception {
		mvc.perform(get(base_URI + "view/byID/12")
						.with(httpBasic("Alafty", "al13"))
						.header("x-validation-report", "true"))
				.andExpect(status().isNotFound());
	}

	@Test
	@Order(4)
	void viewCourseByDescription_descriptionExists_success() throws Exception {
		mvc.perform(get(base_URI + "view/The Newly Added Course")
						.with(httpBasic("Alafty", "al13"))
						.header("x-validation-report", "true"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("New Course"))
				.andExpect(jsonPath("$.description").value("The Newly Added Course"));
	}

	@Test
	void viewCourseByDescription_descriptionDoesNotExist_fail() throws Exception {
		mvc.perform(get(base_URI + "view/Conclusion on Spring")
						.with(httpBasic("Alafty", "al13"))
						.header("x-validation-report", "true"))
				.andExpect(status().isNotFound());
	}



	@Test
	@Order(13)
	void updateCourse_descriptionProvided_success() throws Exception {

		String body = "The Even Newer New Description";
		mvc.perform(put(base_URI + "update/1")
					.with(httpBasic("Alafty", "al13"))
					.header("x-validation-report", "true")
					.contentType(MediaType.APPLICATION_JSON)
					.content(body))
				.andExpect(status().isOk());

		mvc.perform(get(base_URI + "view/" + body)
						.with(httpBasic("Alafty", "al13"))
						.header("x-validation-report", "true"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("New Course"))
				.andExpect(jsonPath("$.description").value("The Even Newer New Description"));
	}




}
