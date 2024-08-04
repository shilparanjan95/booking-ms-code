package com.wipro.flight.booking.repo;

import com.wipro.flight.booking.dto.BookingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingInfoRepo extends JpaRepository<BookingInfo, UUID> {

     BookingInfo findByFlightId(int flightId);

}
