package eu.tasgroup.applicativo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.AdminResetToken;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;

public interface AdminResetTokenRepository extends JpaRepository<AdminResetToken, Long> {

	@Query("Select r from AdminResetToken r where r.admin.id = ?1")
	Optional<AdminResetToken> findByAdminId(long id);
	
	@Query("Select r from AdminResetToken r where r.admin.emailAdmin = ?1")
	Optional<AdminResetToken> findByAdminEmail(String email);

	Optional<AdminResetToken> findByToken(String token);
	
	@Query("Select p.admin from AdminResetToken p where p.token = ?1")
	Optional<Amministratore> getAdminByToken(String token);
}
