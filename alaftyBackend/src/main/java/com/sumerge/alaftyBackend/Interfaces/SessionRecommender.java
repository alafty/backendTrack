package com.sumerge.alaftyBackend.Interfaces;


import com.sumerge.alaftyBackend.Models.Booking;
import com.sumerge.alaftyBackend.Models.Session;

import java.util.List;

public interface SessionRecommender {

    List<Session> recommendSessions();

}
