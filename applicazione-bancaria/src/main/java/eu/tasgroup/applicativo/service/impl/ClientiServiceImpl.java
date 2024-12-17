package eu.tasgroup.applicativo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Pagamento;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Prestito;
import eu.tasgroup.applicativo.repository.ClientiRepository;
import eu.tasgroup.applicativo.security.AdminOnly;
import eu.tasgroup.applicativo.service.ClientiService;
import jakarta.transaction.Transactional;

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
		return clientiRepository.saveAndFlush(cliente);
	}

	@Override
	public List<Cliente> clientiSaldoMax() {
		return clientiRepository.clientiSaldoMax();
	}

	@Override
	@Transactional
	public int numeroConti(long id) {
		Cliente c = clientiRepository.findById(id).get();
		Hibernate.initialize(c.getConti());
		return c.getConti().size();
	}

	@Override
	@Transactional
	public int numeroCarte(long id) {
		Cliente c = clientiRepository.findById(id).get();
		Hibernate.initialize(c.getCarte());
		return c.getCarte().size();
	}

	@Override
	@Transactional
	public List<Prestito> listaPrestitiClienti(long id) {
		Cliente c = clientiRepository.findById(id).get();
		List<Prestito> lista = new ArrayList<Prestito>();
		Hibernate.initialize(c.getPrestiti());
		lista.addAll(c.getPrestiti());
		return lista;
		
	}

	@Override
	@Transactional
	public List<Pagamento> listaPagamentiClienti(long id) {
		Cliente c = clientiRepository.findById(id).get();
		List<Pagamento> lista = new ArrayList<Pagamento>();
		Hibernate.initialize(c.getPagamenti());
		lista.addAll(c.getPagamenti());
		return lista;
	}

	@Override
	public void deleteCliente(Cliente cliente) {
		clientiRepository.delete(cliente);
	}

	@Override
	public Optional<Cliente> findByEmailCliente(String email) {
		return clientiRepository.findByEmailCliente(email);
	}

	@AdminOnly
	@Override
	public void changeStatusCliente(long id) {
		Optional<Cliente> clienteOpt = clientiRepository.findById(id);
		
		if(clienteOpt.isEmpty())
			return;
		
		Cliente c = clienteOpt.get();
		
		if(c.isAccountBloccato()) {
			c.setAccountBloccato(false);
			c.setTentativiErrati(0);
		}else {
			c.setAccountBloccato(true);
		}
		
		clientiRepository.save(c);
	}
}
