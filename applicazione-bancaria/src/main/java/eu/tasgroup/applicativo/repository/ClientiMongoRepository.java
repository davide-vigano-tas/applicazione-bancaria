package eu.tasgroup.applicativo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import eu.tasgroup.applicativo.businesscomponent.model.mongo.ClienteMongo;

public interface ClientiMongoRepository extends MongoRepository<ClienteMongo, String> {

	@Query("{'saldoConto' : {$gt : ?0}}")
	List<ClienteMongo> saldoMaggioreDi(double saldo);

	@Query("{'saldoConto' : {$lt : ?0}}")
	List<ClienteMongo> saldoMinoreDi(double saldo);

	@Aggregation(pipeline = { 
			"{ $match: { 'saldoConto' : { $gt: ?0, $lt: ?1 } } }", 
			"{ $count: 'totalCount' }" })
	Integer countSaldoClienteByIntervallo(double start, double end);
}
