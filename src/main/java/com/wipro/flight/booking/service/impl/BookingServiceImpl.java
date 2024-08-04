package com.wipro.flight.booking.service.impl;

import com.wipro.flight.booking.dto.BookingInfo;
import com.wipro.flight.booking.producer.BookingProducer;
import com.wipro.flight.booking.repo.BookingInfoRepo;
import com.wipro.flight.booking.service.BookingService;
import entity.FlightDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {


    @Autowired
    RestTemplate restTemplate;

    @Autowired
    BookingProducer bookingProducer;

    @Autowired
    BookingInfoRepo bookingInfoRepo;

    public List<FlightDetails> getAllFlights(String source, String destination, String date) {

        ResponseEntity<List<FlightDetails>> responseEntity = restTemplate.exchange(
                "http://api-gateway/flight/search?source={source}&destination={destination}&date={date}",
                HttpMethod.GET,null,
                new ParameterizedTypeReference<List<FlightDetails>>() {},source,destination,date);
        if(responseEntity.getStatusCode().is2xxSuccessful())
        return responseEntity.getBody();

        return null;

    }

    public BookingInfo initiatePayment(FlightDetails flightDetails)
    {

        log.info("payment initiated for flight id {}",flightDetails);

       /* Once the payment is initiated then a message will be generated with the booking id, amount
            ,mode of payment etc to a Kafka topic (say T1) */

      BookingInfo bookingInfo =   BookingInfo.builder().bookingId(UUID.randomUUID()).paymentMode("Card")
                .amount(flightDetails.getPrice())
                .status("Initiated")
                .flightId(flightDetails.getId())
                .build();
        log.info("message sent to kafka topic t1");
        bookingProducer.sendMessage("t1",bookingInfo);
        saveBooking(bookingInfo);
        return bookingInfo;

    }

    public void saveBooking(BookingInfo bookingInfo) {
        bookingInfoRepo.save(bookingInfo);
    }

    public BookingInfo getPaymentData(int id)
    {
        return bookingInfoRepo.findByFlightId(id);
    }


    public void saveBooking(String status, int flightId) {
     BookingInfo updateBooking =   bookingInfoRepo.findByFlightId(flightId);
        updateBooking.setStatus(status);
        bookingInfoRepo.save(updateBooking);
    }

    @Override
    public ResponseEntity<FlightDetails> getAllFlightData(int flightId) {
        return restTemplate.getForEntity("http://api-gateway/flight/"+flightId, FlightDetails.class);
    }
}
