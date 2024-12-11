package eu.tasgroup.applicativo.service.mongo.impl;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mongo.ClienteMongo;
import eu.tasgroup.applicativo.repository.mongo.ClientiMongoRepository;
import eu.tasgroup.applicativo.service.mongo.ClientiMongoService;

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

}
