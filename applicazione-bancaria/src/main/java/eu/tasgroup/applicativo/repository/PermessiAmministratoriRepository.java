package eu.tasgroup.applicativo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import eu.tasgroup.applicativo.businesscomponent.enumerated.Role;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.PermessiAmministratori;

public interface PermessiAmministratoriRepository extends JpaRepository<PermessiAmministratori, Long> {

	@Query("Select r from PermessiAmministratori r where r.amministratore.codAdmin = ?1")
	List<PermessiAmministratori> getByAdminId(long id);
	
	@Query("Select r.amministratore from PermessiAmministratori r where r.ruolo = ?1")
	List<Amministratore> getAdminByRole(Role role);
}
