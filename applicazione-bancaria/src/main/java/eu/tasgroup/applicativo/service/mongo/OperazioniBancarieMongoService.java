package eu.tasgroup.applicativo.service.mongo;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mongo.OperazioniBancarieMongo;

public interface OperazioniBancarieMongoService {
	Optional<OperazioniBancarieMongo> findById(String id);
	OperazioniBancarieMongo createOrUpdate(OperazioniBancarieMongo operazione);
	List<OperazioniBancarieMongo> findAll();
	double totaleImporto();
	int numeroOperazioniPerTipo(String tipo);
	List<OperazioniBancarieMongo> ultimeOperazioni();
	double importoMedioOperazione();

}
