package eu.tasgroup.applicativo.service;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.enumerated.Role;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.PermessiAmministratori;

public interface PermessiAmministratoriService {
	PermessiAmministratori createOrUpdate(PermessiAmministratori p);
	Optional<PermessiAmministratori> findById(long id);
	List<PermessiAmministratori> getByAdminId(long id);
	List<Amministratore> getAdminByRole(Role role);

}
