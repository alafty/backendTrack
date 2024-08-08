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
		CourseService _cS = app.getBean("courseService", CourseService.class);
		BookingService _bS = app.getBean("bookingService", BookingService.class);
		SessionService _lPS = app.getBean("sessionService", SessionService.class);

		_cS.getRecommendedCourses();
		_bS.getRecommendedBookings();
		_lPS.getRecommendedSessions();
	}

	@Component
	class CLRunner implements CommandLineRunner {

		private CourseService _courseService;

		public CLRunner(CourseService courseService) {
			_courseService = courseService;
		}
		@Override
		public void run(String... args) throws Exception {
			Course dummy_1 = new Course(1, "c1", "firstCourse", 4, 2, 1, 1);
			Course dummy_2 = new Course(2, "c2", "secondCourse", 3, 1, 3, 2);
			Course dummy_3 = new Course(3, "c3", "thirdCourse", 2, 3, 4, 3);


//			_courseService.addCourse(dummy_1);
//			_courseService.addCourse(dummy_2);
//			_courseService.addCourse(dummy_3);

//			_courseService.deleteCourse(dummy_1.id);

//			System.out.println(_courseService.getCourse(2).name);

			_courseService.updateCourseDescription(dummy_3.id, "FifthCourse");

		}
	}

}
