package eu.tasgroup.applicativo.service;

import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.AdminResetToken;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;

public interface AdminResetTokenService {
	AdminResetToken create(AdminResetToken token);
	Optional<AdminResetToken> findByAdminId(long id);
	Optional<AdminResetToken> findByAdminEmail(String email);
	void delete(AdminResetToken token);
	Optional<AdminResetToken> findByToken(String token);
	Optional<Amministratore> getAdminByToken(String token);

}
