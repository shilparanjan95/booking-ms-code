package com.wipro.flight.booking.service;

import com.wipro.flight.booking.dto.BookingInfo;
import entity.FlightDetails;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookingService {

     List<FlightDetails> getAllFlights(String source, String destination, String date) ;
     BookingInfo initiatePayment(FlightDetails flightDetails);

     void saveBooking(BookingInfo bookingInfo) ;
     BookingInfo getPaymentData(int id);
     void saveBooking(String status, int flightId);

     ResponseEntity<FlightDetails> getAllFlightData(int flightId);





    }
