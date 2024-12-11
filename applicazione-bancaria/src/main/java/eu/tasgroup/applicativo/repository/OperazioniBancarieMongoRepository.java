package eu.tasgroup.applicativo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import eu.tasgroup.applicativo.businesscomponent.model.mongo.OperazioniBancarieMongo;

public interface OperazioniBancarieMongoRepository extends MongoRepository<OperazioniBancarieMongo, String> {

	@Aggregation(pipeline ="{ '$group': { '_id': null, 'totalAmount': { '$sum': '$importo' } } }")
	Double importoTotale();
	
	@Query("{'tipoOperazione': ?0}")
	List<OperazioniBancarieMongo> operazioniPerTipo(String tipo);
	
    @Aggregation(pipeline = {
            "{ '$group': { '_id': null, 'maxDate': { '$max': '$dataOperazione' } } }",
            "{ '$lookup': { 'from': 'OperazioniBancarie', 'pipeline': [ { '$match': { 'dataOperazione': { '$eq': '$maxDate' } } } ], 'as': 'OperazioniBancarie' } }"
        })
	List<OperazioniBancarieMongo> ultimeOperazioni();
    
	@Aggregation(pipeline ="{ '$group': { '_id': null, 'avgAmount': { '$avg': '$importo' } } }")
	Double importoMedioOperazioni();
}
