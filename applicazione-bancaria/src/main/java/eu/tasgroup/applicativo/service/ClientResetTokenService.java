package eu.tasgroup.applicativo.service;

import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.ClientResetToken;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;

public interface ClientResetTokenService {
	ClientResetToken create(ClientResetToken token);
	Optional<ClientResetToken> findByClientId(long id);
	Optional<ClientResetToken> findByClientEmail(String email);
	void delete(ClientResetToken token);
	Optional<ClientResetToken> findByToken(String token);
	Optional<Cliente> getClientByToken(String token);

}
