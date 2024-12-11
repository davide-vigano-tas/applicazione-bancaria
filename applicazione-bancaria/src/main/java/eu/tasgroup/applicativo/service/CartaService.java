package eu.tasgroup.applicativo.service;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Carta;

public interface CartaService  {
	
	Carta createOrUpdate(Carta carta);
	
	void deleteCartaById(long id);
	
	Optional<Carta> getCartaById(long id);
	
	List<Carta> getAll();
}
