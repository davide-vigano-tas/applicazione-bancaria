package eu.tasgroup.applicativo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Conto;

@Repository
public interface ContiRepository extends JpaRepository<Conto, Long>{

	@Query("Select avg(c.saldo) from Conto c")
	Double saldoMedio();
	
	@Query("Select c from Conto c where c.saldo = 0")
	List<Conto> getContiSaldoZero();
}
