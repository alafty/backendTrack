package com.sumerge.alaftyBackend.SessionRecommenders;

import Model.Session;
import Recommenders.DayRecommender;

import java.util.List;

public class EnhancedDayRecommender extends DayRecommender {
    @Override
    public List<Session> recommendSessions() {
        System.out.println("Enhanced Day");
        return List.of();
    }
}
