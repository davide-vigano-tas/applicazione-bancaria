package eu.tasgroup.applicativo.conf;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoTransazione;
import eu.tasgroup.applicativo.businesscomponent.model.mongo.TransazioniMongo;
import eu.tasgroup.applicativo.repository.TransazioneMongoRepository;

@Component
public class KafkaConsumer {
	private final KafkaTemplate<String, TransazioniMongo> kafkaTemplate;
	private final TransazioneMongoRepository transazioneMongoRepository;

	public KafkaConsumer(KafkaTemplate<String, TransazioniMongo> kafkaTemplate,
			TransazioneMongoRepository transazioneMongoRepository) {
		super();
		this.kafkaTemplate = kafkaTemplate;
		this.transazioneMongoRepository = transazioneMongoRepository;
	}
	
	@KafkaListener(topics = "transazioni-topic", groupId = "transazioni-group")
	public void reciveTransazione(TransazioniMongo transazione) {
		System.out.println("Ho ricevuto la transazione");
		
		// Salvo Transazione in base a una condizione 
		
		if(transazione.getImporto() > 1000.0 && transazione.getTipoTransazione().equals(TipoTransazione.ADDEBITO)) {
			System.out.println("Transazione Fallita, importo massimo superato: " + transazione.getImporto());
			kafkaTemplate.send("transazioni-dlq-topic", transazione);
		}else {
			transazioneMongoRepository.save(transazione);
			System.out.println("Transazione elaborata con successo !");
		}
	}
}
