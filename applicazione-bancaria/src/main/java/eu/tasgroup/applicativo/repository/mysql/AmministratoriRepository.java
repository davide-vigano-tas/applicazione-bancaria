package eu.tasgroup.applicativo.repository.mysql;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;

public interface AmministratoriRepository extends JpaRepository<Amministratore, Long>{
	
	@Query("Select a from Amministratore a where a.emailAdmin = ?1")
	Optional<Amministratore> findByEmailAdmin(String emailAdmin);

}