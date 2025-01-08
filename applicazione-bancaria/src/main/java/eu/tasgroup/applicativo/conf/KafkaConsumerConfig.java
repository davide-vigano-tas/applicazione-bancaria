package eu.tasgroup.applicativo.conf;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import eu.tasgroup.applicativo.businesscomponent.model.mongo.TransazioniMongo;

@Configuration
public class KafkaConsumerConfig {

	@Bean
	ConsumerFactory<String, TransazioniMongo> consumerFactory() {
		Map<String, Object> props = new HashMap<String, Object>();

		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "transazioni-group");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

		// definisce pacchetti consentiti per la deserializzazione dell'oggetto,
		// posso limitare a quali sono gli oggetti consentiti
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

		// Oggetto predefinito
		props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, TransazioniMongo.class.getName());

		return new DefaultKafkaConsumerFactory<String, TransazioniMongo>(props);
	}

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, TransazioniMongo> kafkaListenerContainerFactory() {
		// è un metodo che serve per gestire consumer kafka per poter leggere topic
		// è un listener che sta in ascolto dei messaggi che arrivano
		ConcurrentKafkaListenerContainerFactory<String, TransazioniMongo> factory = new ConcurrentKafkaListenerContainerFactory<String, TransazioniMongo>();

		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
}
