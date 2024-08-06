package com.wipro.flight.booking.service.impl;

import com.wipro.flight.booking.config.UrlConfig;
import com.wipro.flight.booking.producer.BookingProducer;
import com.wipro.flight.booking.repo.BookingInfoRepo;
import entity.FlightDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class BookingServiceImplTest {
    @InjectMocks
    BookingServiceImpl bookingService;

    @Autowired
    RestTemplate restTemplate;

    @Mock
    BookingProducer bookingProducer;

    @Mock
    BookingInfoRepo bookingInfoRepo;

    @Autowired
    UrlConfig urlConfig;

    @Test
    public void getAllFlightsTest(){
        Assertions.assertTrue(true);
    }


    }
