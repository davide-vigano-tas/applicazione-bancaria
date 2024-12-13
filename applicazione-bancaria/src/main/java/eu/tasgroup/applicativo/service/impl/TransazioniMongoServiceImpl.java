package eu.tasgroup.applicativo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoTransazione;
import eu.tasgroup.applicativo.businesscomponent.model.mongo.TransazioniMongo;
import eu.tasgroup.applicativo.repository.TransazioneMongoRepository;
import eu.tasgroup.applicativo.service.TransazioniMongoService;

@Service
public class TransazioniMongoServiceImpl implements TransazioniMongoService {

	private TransazioneMongoRepository transazioneMongoRepository;
	
	public TransazioniMongoServiceImpl(TransazioneMongoRepository transazioneMongoRepository) {
		this.transazioneMongoRepository = transazioneMongoRepository;
	}

	@Override
	public Optional<TransazioniMongo> findById(String id) {
		return transazioneMongoRepository.findById(id);
	}

	@Override
	public TransazioniMongo createOrUpdate(TransazioniMongo transazione) {
		return transazioneMongoRepository.save(transazione);
	}

	@Override
	public List<TransazioniMongo> findAll() {
		return transazioneMongoRepository.findAll();
	}

	@Override
	public Integer numeroTransazioniPerTipo(TipoTransazione tipo) {
		
		Integer result = transazioneMongoRepository.countByTipoTransazione(tipo);
		return (result != null) ? result : 0;
	}

	@Override
	public Double calcoloMediaTransazioniPerCliente(long codCliente) {
		Double result = transazioneMongoRepository.calcolaMediaTransazioniPerCliente(codCliente);
		return (result != null) ? result : 0.00;
	}

	@Override
	public Double numeroMedioTransazioniPerCliente() {
		Double result = transazioneMongoRepository.numeroMedioTransazioniPerCliente();
		return (result != null) ? result : 0.00;
	}

	@Override
	public Double totaleImportoPerMese(int mese) {
		Double result = transazioneMongoRepository.totaleImportoPerMese(mese);
		return (result != null) ? result : 0.00;
	}

	@Override
	public void deleteTransazioneMongo(TransazioniMongo transazione) {
		transazioneMongoRepository.delete(transazione);
	}

	@Override
	public Optional<TransazioniMongo> ultimaTransazione() {
		return transazioneMongoRepository.findTopByOrderByDataTransazioneDesc();
	}

	@Override
	public List<TransazioniMongo> findByCliente(long cliente) {
		return transazioneMongoRepository.findByCliente(cliente);
	}

}
