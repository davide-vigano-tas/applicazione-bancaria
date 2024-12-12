package eu.tasgroup.applicativo.service;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mongo.ClienteMongo;

public interface ClientiMongoService {
	Optional<ClienteMongo> findById(String id);
	Optional<ClienteMongo> findByCodCliente(int codCliente);
	List<ClienteMongo> getClientiList();
	ClienteMongo createOrUpdate(ClienteMongo cliente);
	int countById();
	int numeroSaldiNegativi();
	int numeroSaldiPositivi();
	double sommaSaldiClienti();
	int numeroClientiPerIntervalloSaldo(double start, double end);
	void deleteClienteMongo(ClienteMongo cliente);

}
