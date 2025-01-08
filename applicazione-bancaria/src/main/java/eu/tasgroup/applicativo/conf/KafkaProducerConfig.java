package eu.tasgroup.applicativo.conf;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import eu.tasgroup.applicativo.businesscomponent.model.mongo.TransazioniMongo;



@Configuration
public class KafkaProducerConfig {
	@Bean
	ProducerFactory<String, TransazioniMongo> producerFactory(){
		Map<String , Object> configProps = new HashMap<String , Object>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<String, TransazioniMongo>(configProps);
	}
	
	@Bean
	KafkaTemplate<String, TransazioniMongo> kafkaTemplate(){
		return new KafkaTemplate<String, TransazioniMongo>(producerFactory());
	}
}
