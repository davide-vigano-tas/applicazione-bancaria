package eu.tasgroup.applicativo.service.mongo;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mongo.TransazioniMongo;

public interface TransazioniMongoService {
	Optional<TransazioniMongo> findById(String id);
	TransazioniMongo createOrUpdate(TransazioniMongo transazione);
	List<TransazioniMongo> findAll();
	int numeroTransazioniPerTipo(String tipo);
	double calcoloMediaTransazioniPerCliente(int codCliente);
	int numeroMedioTransazioniPerCliente();
	double totaleImportoPerMese(int mese);                

}
