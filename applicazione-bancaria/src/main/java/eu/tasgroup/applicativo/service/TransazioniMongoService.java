package eu.tasgroup.applicativo.service;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoTransazione;
import eu.tasgroup.applicativo.businesscomponent.model.mongo.TransazioniMongo;

public interface TransazioniMongoService {
	Optional<TransazioniMongo> findById(String id);
	TransazioniMongo createOrUpdate(TransazioniMongo transazione);
	List<TransazioniMongo> findAll();
	Integer numeroTransazioniPerTipo(TipoTransazione tipo);
	Double calcoloMediaTransazioniPerCliente(long codCliente);
	Double numeroMedioTransazioniPerCliente();
	Double totaleImportoPerMese(int mese);  
	Optional<TransazioniMongo> ultimaTransazione();
	List<TransazioniMongo> findByCliente(long cliente);
	void deleteTransazioneMongo(TransazioniMongo transazione);
	Optional<TransazioniMongo> findByCodTransazione(long id);

}
