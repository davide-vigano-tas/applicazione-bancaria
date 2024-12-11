package eu.tasgroup.applicativo.repository.mysql;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;

public interface ClientiRepository extends JpaRepository<Cliente, Long> {
	 

	
	@Query("Select c from Cliente c where c.saldo >= (Select max(c1.saldo) from Cliente c1)")
	List<Cliente> clientiSaldoMax();
	


}
