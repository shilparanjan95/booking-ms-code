package com.wipro.flight.booking.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import java.util.HashMap;
import java.util.Map;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class BookingKafkaProducerConfig {
	 @Bean
	    public <K, V> ProducerFactory<K, V> createBookingProducerFactory(){
		 
	        Map<String,Object> config = new HashMap<>();
	        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		    config.put(ConsumerConfig.GROUP_ID_CONFIG, "booking");
			config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
	        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
	        return new DefaultKafkaProducerFactory(config);
	    }

	    @Bean
	    public <K, V> KafkaTemplate<K, V> createNewsKafkaTemplate(){
	        return new KafkaTemplate<>(createBookingProducerFactory());
	    }
}