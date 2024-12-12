package eu.tasgroup.applicativo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import eu.tasgroup.applicativo.businesscomponent.model.mongo.OperazioniBancarieMongo;

public interface OperazioniBancarieMongoRepository extends MongoRepository<OperazioniBancarieMongo, String> {

	@Aggregation(pipeline ="{ '$group': { '_id': null, 'totalAmount': { '$sum': '$importo' } } }")
	Double importoTotale();
	
	@Query("{'tipoOperazione': ?0}")
	List<OperazioniBancarieMongo> operazioniPerTipo(String tipo);
	
	@Aggregation(pipeline = { "{ '$sort': { 'dataOperazione': -1 } }", "{ '$limit': 1 }" })
	Optional<OperazioniBancarieMongo> ultimeOperazioni();
    
	@Aggregation(pipeline ="{ '$group': { '_id': null, 'avgAmount': { '$avg': '$importo' } } }")
	Double importoMedioOperazioni();
}
