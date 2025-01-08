package eu.tasgroup.applicativo.conf;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.businesscomponent.model.mongo.TransazioniMongo;

@Service
public class KafkaProducer {
	private final KafkaTemplate<String, TransazioniMongo> kafkaTemplate;

	public KafkaProducer(KafkaTemplate<String, TransazioniMongo> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendTransazione(TransazioniMongo transazione) {
		System.out.println("invio della transazione: "+ transazione);
		kafkaTemplate.send("transazioni-topic",transazione);
	}
	
}
