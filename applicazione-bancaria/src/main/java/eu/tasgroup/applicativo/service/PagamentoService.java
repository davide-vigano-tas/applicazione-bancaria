package eu.tasgroup.applicativo.service;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Pagamento;

public interface PagamentoService {
	
	Pagamento createOrUpdate(Pagamento pagamento);
	
	void deletePagamentoById(long id);
	
	Optional<Pagamento> getPagamentoById(long id);
	
	List<Pagamento> getAll();
}
