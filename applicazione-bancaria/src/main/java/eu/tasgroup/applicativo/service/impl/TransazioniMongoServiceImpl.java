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
	public int numeroTransazioniPerTipo(TipoTransazione tipo) {
		return transazioneMongoRepository.countByTipoTransazione(tipo);
	}

	@Override
	public double calcoloMediaTransazioniPerCliente(int codCliente) {
		return transazioneMongoRepository.calcolaMediaTransazioniPerCliente(codCliente);
	}

	@Override
	public double numeroMedioTransazioniPerCliente() {
		return transazioneMongoRepository.numeroMedioTransazioniPerCliente();
	}

	@Override
	public double totaleImportoPerMese(int mese) {
		return transazioneMongoRepository.totaleImportoPerMese(mese);
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
	public List<TransazioniMongo> findByCliente(int cliente) {
		return transazioneMongoRepository.findByCliente(cliente);
	}

}
