package eu.tasgroup.applicativo.service.mongo;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mongo.ClienteMongo;

public interface ClientiMongoService {
	Optional<ClienteMongo> findById(String id);
	List<ClienteMongo> getClientiList();
	ClienteMongo createOrUpdate(ClienteMongo cliente);
	int countById();
	List<ClienteMongo> clientiSaldoMax();
	int numeroSaldiNegativi();
	int numeroSaldiPositivi();
	double sommaSaldiClienti();
	int numeroClientiPerIntervalloSaldo(double start, double end);

}
