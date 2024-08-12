package com.sumerge.alaftyBackend;

import com.sumerge.alaftyBackend.Interfaces.BookingRecommender;
import com.sumerge.alaftyBackend.Interfaces.CourseRecommender;
import com.sumerge.alaftyBackend.Models.Booking;
import com.sumerge.alaftyBackend.Models.Course;
import com.sumerge.alaftyBackend.Services.BookingService;
import com.sumerge.alaftyBackend.Services.CourseService;
import com.sumerge.alaftyBackend.Services.SessionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import java.awt.print.Book;
import java.util.List;

@SpringBootApplication
public class AlaftyBackendTasksApplication {

	public static void main(String[] args) throws Exception{
		ConfigurableApplicationContext app = SpringApplication.run(AlaftyBackendTasksApplication.class, args);

	}

	@Component
	class CLRunner implements CommandLineRunner {

		private CourseService _courseService;

		public CLRunner(CourseService courseService) {
			_courseService = courseService;
		}
		@Override
		public void run(String... args) throws Exception {

		}
	}

}
