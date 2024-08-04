package com.sumerge.alaftyBackend.Services;

import com.sumerge.alaftyBackend.Interfaces.BookingRecommender;
import com.sumerge.alaftyBackend.Interfaces.CourseRecommender;
import com.sumerge.alaftyBackend.Models.Booking;
import com.sumerge.alaftyBackend.Models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    BookingRecommender bookingsRecommender;

    @Autowired
    @Qualifier("weekdays")
    public void setBookingsRecommender( BookingRecommender bookingsRecommender) {
        this.bookingsRecommender = bookingsRecommender;
    }

    public List<Booking> getRecommendedBookings() {

        return bookingsRecommender.recommendedBookings();
    }
}
