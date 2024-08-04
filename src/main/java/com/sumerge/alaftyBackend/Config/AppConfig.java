package com.sumerge.alaftyBackend.Config;

import Recommenders.DayRecommender;
import com.sumerge.alaftyBackend.CoursesRecommenders.EngineeringRecommender;
import com.sumerge.alaftyBackend.Interfaces.CourseRecommender;
import Interfaces.SessionRecommender;
import com.sumerge.alaftyBackend.Models.Course;
import com.sumerge.alaftyBackend.Models.Session;
import com.sumerge.alaftyBackend.Services.CourseService;
import com.sumerge.alaftyBackend.Services.SessionService;
import com.sumerge.alaftyBackend.SessionRecommenders.EnhancedDayRecommender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public CourseRecommender getCourseRecommender() {
        return new EngineeringRecommender();
    }
    @Bean("courseService")
    public CourseService getCourseService() {
        return  new CourseService(getCourseRecommender());
    }

    @Bean("sessionService")
    public SessionService getSessionService() {
        return new SessionService();
    }

    @Bean("dayRecommender")
    public SessionRecommender getDayRecommender() {
        return (SessionRecommender) new DayRecommender();
    }
    @Bean("enhancedDayRecommender")
    public EnhancedDayRecommender getEnhancedDayRecommender() {
        return new EnhancedDayRecommender();
    }
}
