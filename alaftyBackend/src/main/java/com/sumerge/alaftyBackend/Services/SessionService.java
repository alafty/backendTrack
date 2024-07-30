package com.sumerge.alaftyBackend.Services;

import com.sumerge.alaftyBackend.Interfaces.SessionRecommender;
import com.sumerge.alaftyBackend.Models.Course;
import com.sumerge.alaftyBackend.Models.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {

    @Autowired
    private SessionRecommender dayRecommender;

    public List<Session> getRecommendedSessions() {
        return dayRecommender.recommendSessions();
    }

}
