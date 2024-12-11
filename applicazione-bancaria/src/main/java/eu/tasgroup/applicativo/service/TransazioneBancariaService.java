package eu.tasgroup.applicativo.service;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.TransazioneBancaria;

public interface TransazioneBancariaService {
	
	TransazioneBancaria createOrUpdate(TransazioneBancaria transazioneBancaria);

	void deleteTransazioneBancariaById(long id);

	Optional<TransazioneBancaria> getTransazioneBancariaById(long id);

	List<TransazioneBancaria> getAll();
}
