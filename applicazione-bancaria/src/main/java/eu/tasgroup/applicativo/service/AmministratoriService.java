package eu.tasgroup.applicativo.service;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;

public interface AmministratoriService {
	Optional<Amministratore> findByEmailAdmin(String email);
	Optional<Amministratore> findById(long id);
	List<Amministratore> findAll();
	Amministratore createOrUpdate(Amministratore amministratore);
	void deleteAdmin(Amministratore amministratore);
}
