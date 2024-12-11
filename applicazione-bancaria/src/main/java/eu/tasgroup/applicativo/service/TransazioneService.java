package eu.tasgroup.applicativo.service;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Transazione;

public interface TransazioneService {
	
	Transazione createOrUpdate(Transazione transazione);
	
	void deleteTransazioneById(long id);
	
	Optional<Transazione> getTransazioneById(long id);
	
	List<Transazione> getAll();
}
