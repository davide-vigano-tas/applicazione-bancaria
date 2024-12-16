package eu.tasgroup.applicativo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.LogAccessi;

@Repository
public interface LogAccessiRepository extends JpaRepository<LogAccessi, Long>{

	@Query("Select l from LogAccessi l where l.admin.id = ?1")
	List<LogAccessi> findByAdminId(long id);
	
	@Query("Select l from LogAccessi l where l.admin.emailAdmin = ?1")
	List<LogAccessi> findByAdminEmail(String email);
}
