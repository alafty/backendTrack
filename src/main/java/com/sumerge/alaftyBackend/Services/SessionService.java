package com.sumerge.alaftyBackend.Services;

import Model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import Interfaces.SessionRecommender;
import java.util.List;

public class SessionService {

    @Autowired
    private SessionRecommender dayRecommender;

    public List<Session> getRecommendedSessions() {
        return dayRecommender.recommendSessions();
    }

}
