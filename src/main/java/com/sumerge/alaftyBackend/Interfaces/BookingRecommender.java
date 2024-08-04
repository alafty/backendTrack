package com.sumerge.alaftyBackend.Interfaces;


import com.sumerge.alaftyBackend.Models.Booking;
import com.sumerge.alaftyBackend.Models.Course;

import java.util.List;

public interface BookingRecommender {

    List<Booking> recommendedBookings();

}
