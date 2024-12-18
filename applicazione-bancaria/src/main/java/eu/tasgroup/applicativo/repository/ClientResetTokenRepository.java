package eu.tasgroup.applicativo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.ClientResetToken;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;

public interface ClientResetTokenRepository extends JpaRepository<ClientResetToken, Long> {

	@Query("Select r from ClientResetToken r where r.cliente.codCliente = ?1")
	Optional<ClientResetToken> findByClientId(long id);
	
	@Query("Select r from ClientResetToken r where r.cliente.emailCliente = ?1")
	Optional<ClientResetToken> findByClientEmail(String email);

	Optional<ClientResetToken> findByToken(String token);
	
	@Query("Select p.cliente from ClientResetToken p where p.token = ?1")
	Optional<Cliente> getClientByToken(String token);
}
