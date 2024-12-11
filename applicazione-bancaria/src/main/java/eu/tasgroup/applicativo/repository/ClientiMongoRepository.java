package eu.tasgroup.applicativo.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import eu.tasgroup.applicativo.businesscomponent.model.mongo.ClienteMongo;

public interface ClientiMongoRepository extends MongoRepository<ClienteMongo, String> {

	
	@Query("{'saldo' : {$gt : ?0}}")
	List<ClienteMongo> saldoMaggioreDi(double saldo);
	
	@Query("{'saldo' : {$lt : ?0}}")
	List<ClienteMongo> saldoMinoreDi(double saldo);
	
	@Query("{'saldo' : {$gt: ?0, $lt: ?1}}")
	int countSaldoClienteByIntervallo(double start, double end);
}
