package eu.tasgroup.applicativo.service.mongo.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoTransazione;
import eu.tasgroup.applicativo.businesscomponent.model.mongo.TransazioniMongo;
import eu.tasgroup.applicativo.repository.mongo.TransazioneMongoRepository;
import eu.tasgroup.applicativo.service.mongo.TransazioniMongoService;

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
	public int numeroMedioTransazioniPerCliente() {
		return transazioneMongoRepository.numeroMedioTransazioniPerCliente().intValue();
	}

	@Override
	public double totaleImportoPerMese(int mese) {
		return transazioneMongoRepository.totaleImportoPerMese(mese);
	}

	@Override
	public void deleteTransazioneMongo(TransazioniMongo transazione) {
		transazioneMongoRepository.delete(transazione);
	}

}
