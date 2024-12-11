package eu.tasgroup.applicativo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;

@Repository
public interface AmministratoriRepository extends JpaRepository<Amministratore, Long>{
	
	@Query("Select a from Amministratore a where a.emailAdmin = ?1")
	Optional<Amministratore> findByEmailAdmin(String emailAdmin);

}
