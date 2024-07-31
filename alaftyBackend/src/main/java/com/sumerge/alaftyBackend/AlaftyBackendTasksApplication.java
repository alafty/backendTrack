package com.sumerge.alaftyBackend;

import com.sumerge.alaftyBackend.Interfaces.BookingRecommender;
import com.sumerge.alaftyBackend.Interfaces.CourseRecommender;
import com.sumerge.alaftyBackend.Models.Booking;
import com.sumerge.alaftyBackend.Models.Course;
import com.sumerge.alaftyBackend.Services.BookingService;
import com.sumerge.alaftyBackend.Services.CourseService;
import com.sumerge.alaftyBackend.Services.SessionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.print.Book;
import java.util.List;

@SpringBootApplication
public class AlaftyBackendTasksApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext app = SpringApplication.run(AlaftyBackendTasksApplication.class, args);
		CourseService _cS = app.getBean("courseService", CourseService.class);
		BookingService _bS = app.getBean("bookingService", BookingService.class);
		SessionService _lPS = app.getBean("sessionService", SessionService.class);

		_cS.getRecommendedCourses();
		_bS.getRecommendedBookings();
		_lPS.getRecommendedSessions();
	}

}
