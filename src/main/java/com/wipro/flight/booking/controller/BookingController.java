package com.wipro.flight.booking.controller;

import com.wipro.flight.booking.dto.BookingInfo;
import com.wipro.flight.booking.service.BookingService;
import com.wipro.flight.booking.service.impl.BookingServiceImpl;
import entity.FlightDetails;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000",methods = {RequestMethod.GET,RequestMethod.POST})
@RequestMapping("/booking")
@Tag(name="booking ms" ,description = "These end point are used to search and book flights ")
public class BookingController {

    @Autowired
    BookingServiceImpl bookingService;

    @ApiResponses(
           value= {@ApiResponse(responseCode = "200", description = "Flight details Found"),
                   @ApiResponse(responseCode = "404", description = "Flight details Not Found")
           }
    )
    @Operation(description = "This end point is used to search flights.",summary = "search flights ")
    @CircuitBreaker(name="booking-flight-data", fallbackMethod="flightDataSearch")
    @GetMapping("/search")
    public ResponseEntity<?> searchAllFlight(@Parameter(name = "source",description ="Departure Airport Name") @RequestParam("source") String source,
                                                               @Parameter(name = "destination",description ="Arrival Airport Name") @RequestParam("destination") String destination,
                                                               @Parameter(name = "date",description ="Travel Date") @RequestParam("date") String date)
    {
        List<FlightDetails> flightDetails =  bookingService.getAllFlights(source,destination,date);
        return ResponseEntity.ok(flightDetails);
    }

    @Operation(description = "This end point is used to book flights.",summary = "book flights ")
    @PostMapping("/pay")
    public ResponseEntity<BookingInfo> bookFlight(@Parameter(name="flight details",description = "pass the flight related info") @RequestBody FlightDetails flightDetails)
    {
        BookingInfo bookingInfo = bookingService.initiatePayment(flightDetails);
       BookingInfo bookingInfoDb =  bookingService.getPaymentData(flightDetails.getId());
       /*if(bookingInfoDb.getStatus().equals("Successful"))
           return ResponseEntity.ok(bookingInfoDb); */
       return ResponseEntity.ok(bookingInfoDb);

    }

    public List<FlightDetails>  flightDataSearch(Throwable throwable)
    {
        FlightDetails flightDetails = FlightDetails.builder()
                .source("Delhi -default")
                .destination("Pune - default")
                .airlineName("AirAsia")
                .id(1)
                .price(5000.0)
                .fromDate("2025-12-12")
                .toDate("2025-12-12")
                .fromTime("4:00")
                .toTime("6:00")
                .build();
        return List.of(flightDetails);
    }


    @GetMapping("/{flightId}")
    public BookingInfo getPaymentInfo(@PathVariable int flightId)
    {
        return bookingService.getPaymentData(flightId);
    }
    @GetMapping("flight/{flightId}")
    public ResponseEntity<FlightDetails> getFlightInfo (@PathVariable int flightId)
    {
     return bookingService.getAllFlightData(flightId);
    }






}
