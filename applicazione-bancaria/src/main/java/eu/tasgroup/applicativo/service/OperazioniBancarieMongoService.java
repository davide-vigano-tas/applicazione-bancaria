package eu.tasgroup.applicativo.service;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mongo.OperazioniBancarieMongo;

public interface OperazioniBancarieMongoService {
	Optional<OperazioniBancarieMongo> findById(String id);
	OperazioniBancarieMongo createOrUpdate(OperazioniBancarieMongo operazione);
	List<OperazioniBancarieMongo> findAll();
	double totaleImporto();
	List<OperazioniBancarieMongo> operazioniPerTipo(String tipo);
	List<OperazioniBancarieMongo> ultimeOperazioni();
	double importoMedioOperazione();

}
