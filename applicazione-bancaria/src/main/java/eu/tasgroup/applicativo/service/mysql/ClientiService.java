package eu.tasgroup.applicativo.service.mysql;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Pagamento;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Prestito;

public interface ClientiService {
	Optional<Cliente> findById(long id);
	List<Cliente> getClientiList();
	Cliente createOrUpdate();
	List<Cliente> totaleClienti();
	List<Cliente> clientiSaldoMax();
	int numeroConti();
	int numeroCarte();
	List<Prestito> listaPrestitiClienti();
	List<Pagamento> listaPagamentiClienti();
	
}
