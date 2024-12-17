package eu.tasgroup.applicativo.service;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Pagamento;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Prestito;

public interface ClientiService {
	Cliente createOrUpdate(Cliente cliente);
	
	void deleteCliente(Cliente cliente);
	void changeStatusCliente(long id);
	
	Optional<Cliente> findById(long id);
	Optional<Cliente> findByEmailCliente(String email);
	
	
	int totaleClienti();
	int numeroConti(long id);
	int numeroCarte(long id);
	
	List<Cliente> clientiSaldoMax();
	List<Prestito> listaPrestitiClienti(long id);
	List<Pagamento> listaPagamentiClienti(long id);
	List<Cliente> getClientiList();
	
}
