package com.wipro.flight.booking.producer;

import com.wipro.flight.booking.dto.BookingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookingProducer {


    @Autowired
    private KafkaTemplate<String, BookingInfo> kafkaTemplate;

    public void sendMessage(String topic, BookingInfo bookingInfo) {
        kafkaTemplate.send(topic, bookingInfo);
    }

}
