package com.wipro.flight.booking.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BookingInfo {

    @Id
    private UUID bookingId;
    private String status;
    private Double amount;
    private String paymentMode;
    private int flightId;
}
