package eu.tasgroup.applicativo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;

@Repository
public interface ClientiRepository extends JpaRepository<Cliente, Long> {

	@Query("Select c from Cliente c where c.saldoConto >= (Select max(c1.saldoConto) from Cliente c1)")
	List<Cliente> clientiSaldoMax();
	
	Optional<Cliente> findByEmailCliente(String email);
}
