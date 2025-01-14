package eu.tasgroup.applicativo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.AuditLog;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long>{

	@Query("Select l from AuditLog l where l.admin.id = ?1")
	List<AuditLog> findByAdminId(long id);
	
	@Query("Select l from AuditLog l where l.admin.emailAdmin = ?1")
	List<AuditLog> findByAdminEmail(String email);
}
