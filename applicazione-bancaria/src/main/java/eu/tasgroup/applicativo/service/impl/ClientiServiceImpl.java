package eu.tasgroup.applicativo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Pagamento;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Prestito;
import eu.tasgroup.applicativo.repository.ClientiRepository;
import eu.tasgroup.applicativo.service.ClientiService;

@Service
public class ClientiServiceImpl implements ClientiService{

	private ClientiRepository clientiRepository;
	
	public ClientiServiceImpl(ClientiRepository clientiRepository) {
		this.clientiRepository = clientiRepository;
	}

	@Override
	public Optional<Cliente> findById(long id) {
		return clientiRepository.findById(id);
	}

	@Override
	public List<Cliente> getClientiList() {
		return clientiRepository.findAll();
	}

	@Override
	public int totaleClienti() {
		return (int) clientiRepository.count();
	}
	
	@Override
	public Cliente createOrUpdate(Cliente cliente) {
		return clientiRepository.save(cliente);
	}

	@Override
	public List<Cliente> clientiSaldoMax() {
		return clientiRepository.clientiSaldoMax();
	}

	@Override
	public int numeroConti(long id) {
		Cliente c = clientiRepository.findById(id).get();
		return c.getConti().size();
	}

	@Override
	public int numeroCarte(long id) {
		Cliente c = clientiRepository.findById(id).get();
		return c.getCarte().size();
	}

	@Override
	public List<Prestito> listaPrestitiClienti(long id) {
		Cliente c = clientiRepository.findById(id).get();
		List<Prestito> lista = new ArrayList<Prestito>();
		lista.addAll(c.getPrestiti());
		return lista;
		
	}

	@Override
	public List<Pagamento> listaPagamentiClienti(long id) {
		Cliente c = clientiRepository.findById(id).get();
		List<Pagamento> lista = new ArrayList<Pagamento>();
		lista.addAll(c.getPagamenti());
		return lista;
	}

	@Override
	public void deleteCliente(Cliente cliente) {
		clientiRepository.delete(cliente);
	}


}
