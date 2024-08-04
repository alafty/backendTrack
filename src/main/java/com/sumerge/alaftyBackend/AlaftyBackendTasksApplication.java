package com.sumerge.alaftyBackend;

import Recommenders.DayRecommender;
import com.sumerge.alaftyBackend.Config.AppConfig;
import com.sumerge.alaftyBackend.Services.CourseService;
import com.sumerge.alaftyBackend.Services.SessionService;
import com.sumerge.alaftyBackend.SessionRecommenders.EnhancedDayRecommender;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AlaftyBackendTasksApplication {

	public static void main(String[] args) {
		ApplicationContext app = new AnnotationConfigApplicationContext(AppConfig.class);
		CourseService _cS = app.getBean("courseService", CourseService.class);
		SessionService _sS = app.getBean("sessionService", SessionService.class);
		EnhancedDayRecommender _eDR = app.getBean("enhancedDayRecommender", EnhancedDayRecommender.class);
		DayRecommender _dR = app.getBean("dayRecommender", DayRecommender.class);

		_cS.getRecommendedCourses();
		_sS.getRecommendedSessions();

		_eDR.recommendSessions();
		_dR.recommendSessions();
	}

}
