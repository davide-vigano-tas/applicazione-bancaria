package eu.tasgroup.applicativo.service.mysql;

import java.util.List;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Conto;

public interface ContoService {
	double saldoMedio(Conto conto);
	void deleteContoById(long id);
	List<Conto> getContiSaldoZero();
}
