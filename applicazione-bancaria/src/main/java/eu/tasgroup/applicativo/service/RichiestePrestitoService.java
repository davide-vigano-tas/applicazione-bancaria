package eu.tasgroup.applicativo.service;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.enumerated.StatoRichiestaPrestito;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.RichiestaPrestito;

public interface RichiestePrestitoService {
	
	RichiestaPrestito createOrUpdate(RichiestaPrestito richiestaPrestito);
	
	Optional<RichiestaPrestito> findById(long id);
	
	void deletePrestitoById(long id);
	
	List<RichiestaPrestito> findByStatus(StatoRichiestaPrestito status);
	List<RichiestaPrestito> getListaPrestitiRichiesti();
}
