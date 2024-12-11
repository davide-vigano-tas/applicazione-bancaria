package eu.tasgroup.applicativo.service;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Prestito;

public interface PrestitoService {
	
	Prestito createOrUpdate(Prestito prestito);
	
	void deletePrestitoById(long id);
	
	Optional<Prestito> getPrestitoById(long id);
	
	List<Prestito> getAll();
}
