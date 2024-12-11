package eu.tasgroup.applicativo.service.mysql;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Pagamento;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Prestito;

public interface ClientiService {
	Optional<Cliente> findById(long id);
	List<Cliente> getClientiList();
	Cliente createOrUpdate(Cliente cliente);
	void deleteCliente(Cliente cliente);
	int totaleClienti();
	List<Cliente> clientiSaldoMax();
	int numeroConti(long id);
	int numeroCarte(long id);
	List<Prestito> listaPrestitiClienti(long id);
	List<Pagamento> listaPagamentiClienti(long id);
	
}
