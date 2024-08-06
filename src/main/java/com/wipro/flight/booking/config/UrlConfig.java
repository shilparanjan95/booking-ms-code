package com.wipro.flight.booking.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class UrlConfig {

    @Value("${flight.search}")
    private String flightSearchUrl;

    @Value("${flight.detail}")
    private String flightDataUrl;


}
