package eu.tasgroup.applicativo.service;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Conto;

public interface ContiService {
	Conto createOrUpdate(Conto conto);
	
	double saldoMedio();
	
	void deleteContoById(long id);
	
	Optional<Conto> findById(long id);
	
	List<Conto> getAll();
	List<Conto> getContiSaldoZero();
}
