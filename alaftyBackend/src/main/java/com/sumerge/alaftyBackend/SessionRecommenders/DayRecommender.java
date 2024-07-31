package com.sumerge.alaftyBackend.SessionRecommenders;

import com.sumerge.alaftyBackend.Interfaces.BookingRecommender;
import com.sumerge.alaftyBackend.Interfaces.SessionRecommender;
import com.sumerge.alaftyBackend.Models.Booking;
import com.sumerge.alaftyBackend.Models.Session;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DayRecommender implements SessionRecommender {


    public List<Session> recommendSessions() {
        System.out.println("Day");
        return List.of();
    }
}
