package com.wipro.flight.booking.service.impl;

import com.wipro.flight.booking.config.UrlConfig;
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
import org.springframework.web.client.HttpClientErrorException;
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

    @Autowired
    UrlConfig urlConfig;

    public List<FlightDetails> getAllFlights(String source, String destination, String date) {
        ResponseEntity<List<FlightDetails>> responseEntity;
        try {
            responseEntity = restTemplate.exchange(
                    urlConfig.getFlightSearchUrl(),
                    HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<FlightDetails>>() {
                    }, source, destination, date);
        }
        catch(HttpClientErrorException clientErrorException) {
            return null;
        }
         return responseEntity.getBody();



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
        return restTemplate.getForEntity(urlConfig.getFlightDataUrl()+flightId, FlightDetails.class);
    }
}
