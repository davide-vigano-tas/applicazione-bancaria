package eu.tasgroup.applicativo.repository.mysql;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Conto;

public interface ContiRepository extends JpaRepository<Conto, Long>{

	@Query("Select avg(c.saldo) from Conto c")
	double saldoMedio();
	
	@Query("Select c from Conto c where c.saldo = 0")
	List<Conto> getContiSaldoZero();
}
