package eu.tasgroup.applicativo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoTransazione;
import eu.tasgroup.applicativo.businesscomponent.model.mongo.TransazioniMongo;

public interface TransazioneMongoRepository extends MongoRepository<TransazioniMongo, String> {

	Integer countByTipoTransazione(TipoTransazione tipo);

	// Media transazioni per cliente
	@Aggregation(pipeline = { "{ $match: { cliente: ?0 } }", "{ $group: { _id: null, media: { $avg: '$importo' } } }" })
	Double calcolaMediaTransazioniPerCliente(int codCliente);

	// numero medio transazioni per cliente
	@Aggregation(pipeline = { "{ '$group': { '_id': '$cliente', 'count': { '$sum': 1 } } }",
			"{ '$group': { '_id': null, 'media': { '$avg': '$count' } } }" })
	Double numeroMedioTransazioniPerCliente();

	// Totale importo per mese
	@Aggregation(pipeline = { "{ $match: { $expr: { $eq: [ { $month: '$dataTransazione' }, ?0 ] } } }",
			"{ $group: { _id: null, totale: { $sum: '$importo' } } }" })
	Double totaleImportoPerMese(int mese);

	// Data ultima transazione
	@Aggregation(pipeline = { "{ '$sort': { 'dataTransazione': -1 } }", "{ '$limit': 1 }" })	
	Optional<TransazioniMongo> findTopByOrderByDataTransazioneDesc();

	// Transazioni per cliente
	List<TransazioniMongo> findByCliente(int cliente);
}
