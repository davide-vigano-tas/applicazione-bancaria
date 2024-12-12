package eu.tasgroup.applicativo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.businesscomponent.model.mongo.ClienteMongo;
import eu.tasgroup.applicativo.repository.ClientiMongoRepository;
import eu.tasgroup.applicativo.service.ClientiMongoService;

@Service
public class ClientiMongoServiceImpl implements ClientiMongoService {

	private ClientiMongoRepository clientiMongoRepository;
	
	public ClientiMongoServiceImpl(ClientiMongoRepository clientiMongoRepository) {
		this.clientiMongoRepository = clientiMongoRepository;
	}

	@Override
	public Optional<ClienteMongo> findById(String id) {
		return clientiMongoRepository.findById(id);
	}
	
	@Override
	public Optional<ClienteMongo> findByCodCliente(int codCliente) {
		return clientiMongoRepository.findByCodCliente(codCliente);
	}

	@Override
	public List<ClienteMongo> getClientiList() {
		return clientiMongoRepository.findAll();
	}

	@Override
	public ClienteMongo createOrUpdate(ClienteMongo cliente) {
		return clientiMongoRepository.save(cliente);
	}

	@Override
	public int countById() {
		return (int) clientiMongoRepository.count();
	}

	@Override
	public int numeroSaldiNegativi() {
		List<ClienteMongo> lista = clientiMongoRepository.saldoMinoreDi(0);
		return lista.size();
	}

	@Override
	public int numeroSaldiPositivi() {
		List<ClienteMongo> lista = clientiMongoRepository.saldoMaggioreDi(0);
		return lista.size();
	}

	@Override
	public double sommaSaldiClienti() {
		double sommaSaldo = 0;
		for(ClienteMongo c : clientiMongoRepository.findAll()) {
			sommaSaldo+=c.getSaldoConto();
		}
		return sommaSaldo;
	}

	@Override
	public int numeroClientiPerIntervalloSaldo(double start, double end) {
		return clientiMongoRepository.countSaldoClienteByIntervallo(start,end);
	}

	@Override
	public void deleteClienteMongo(ClienteMongo cliente) {
		clientiMongoRepository.delete(cliente);
	}
}
