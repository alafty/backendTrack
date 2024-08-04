package com.sumerge.alaftyBackend.SessionRecommenders;

import Model.Session;
import Interfaces.SessionRecommender;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NightRecommender implements SessionRecommender {

    public List<Session> recommendSessions() {
        System.out.println("Night");
        return List.of();
    }
}
