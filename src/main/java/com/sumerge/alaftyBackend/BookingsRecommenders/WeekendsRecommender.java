package com.sumerge.alaftyBackend.BookingsRecommenders;

import com.sumerge.alaftyBackend.Interfaces.BookingRecommender;
import com.sumerge.alaftyBackend.Interfaces.CourseRecommender;
import com.sumerge.alaftyBackend.Models.Booking;
import com.sumerge.alaftyBackend.Models.Course;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("weekends")
public class WeekendsRecommender implements BookingRecommender {

    public List<Booking> recommendedBookings() {
        System.out.println("Weekends Bookings");
        return List.of(new Booking("Friday"), new Booking("Saturday"));
    }
}
