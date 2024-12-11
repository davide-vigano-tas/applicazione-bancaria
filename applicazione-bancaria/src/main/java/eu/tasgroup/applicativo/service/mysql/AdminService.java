package eu.tasgroup.applicativo.service.mysql;

import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;

public interface AdminService {
	Optional<Amministratore> findByEmailAdmin(String email);
	Optional<Amministratore> findById(long id);
	Amministratore createOrUpdate();
}
