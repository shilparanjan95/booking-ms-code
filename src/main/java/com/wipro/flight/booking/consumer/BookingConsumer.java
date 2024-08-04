package com.wipro.flight.booking.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.wipro.flight.booking.dto.BookingInfo;
import com.wipro.flight.booking.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookingConsumer {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BookingService bookingService;



    @KafkaListener(topics = "t2", groupId = "booking")
    public void listen(String message) throws JsonProcessingException {
        System.out.println("Received message: " + message);
        //update payment status
        BookingInfo bookingInfo = objectMapper.readValue(message, BookingInfo.class);
        bookingService.saveBooking(bookingInfo.getStatus(),bookingInfo.getFlightId());
        log.info("booking info updated");


    }


}
