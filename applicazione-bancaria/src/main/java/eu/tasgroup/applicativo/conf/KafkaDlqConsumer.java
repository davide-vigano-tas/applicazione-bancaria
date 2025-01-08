package eu.tasgroup.applicativo.conf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import eu.tasgroup.applicativo.businesscomponent.model.mongo.TransazioniMongo;

@Component
public class KafkaDlqConsumer {


	private Logger logger = Logger.getLogger("DQL");
	private FileHandler fileHandler;
	
	@KafkaListener(topics = "transazioni-dlq-topic", groupId = "transazioni-dlq-group")
	public void receivedFailedTransazione(TransazioniMongo transazione) {
		 try {
				Path path = Paths.get("C:\\logDQL");
			
				if(Files.notExists(path)) {
					Files.createDirectory(path);
				}
		
					fileHandler=new FileHandler("C:\\logDQL\\logfile.log", true);
					logger.addHandler(fileHandler);
					logger.setLevel(Level.ALL);
					SimpleFormatter formato = new SimpleFormatter();
					fileHandler.setFormatter(formato);
				
				logger.log(Level.INFO, "-------------------------------------");
				logger.log(Level.WARNING, transazione.toString());
				logger.log(Level.INFO, "-------------------------------------");
				fileHandler.close();
		 }catch (IOException e) {
			Logger.getLogger("error_logger").log(Level.SEVERE, "Error while logging failed transaction");
		}
		 fileHandler.close();
	}
}
