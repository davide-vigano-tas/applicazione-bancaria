package eu.tasgroup.applicativo.service.mongo.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.businesscomponent.model.mongo.OperazioniBancarieMongo;
import eu.tasgroup.applicativo.repository.mongo.OperazioniBancarieMongoRepository;
import eu.tasgroup.applicativo.service.mongo.OperazioniBancarieMongoService;

@Service
public class OperazioniBancarieMongoServiceImpl implements OperazioniBancarieMongoService {
	
	private OperazioniBancarieMongoRepository operazioniBancarieMongoRepository;
	
	public OperazioniBancarieMongoServiceImpl(OperazioniBancarieMongoRepository operazioniBancarieMongoRepository) {
		this.operazioniBancarieMongoRepository = operazioniBancarieMongoRepository;
	}

	@Override
	public Optional<OperazioniBancarieMongo> findById(String id) {
		return operazioniBancarieMongoRepository.findById(id);
	}

	@Override
	public OperazioniBancarieMongo createOrUpdate(OperazioniBancarieMongo operazione) {
		return operazioniBancarieMongoRepository.save(operazione);
	}

	@Override
	public List<OperazioniBancarieMongo> findAll() {
		return operazioniBancarieMongoRepository.findAll();
	}

	@Override
	public double totaleImporto() {
		return operazioniBancarieMongoRepository.importoTotale();
	}

	@Override
	public List<OperazioniBancarieMongo> operazioniPerTipo(String tipo) {
		return operazioniBancarieMongoRepository.operazioniPerTipo(tipo);
	}

	@Override
	public List<OperazioniBancarieMongo> ultimeOperazioni() {
		return operazioniBancarieMongoRepository.ultimeOperazioni();
	}

	@Override
	public double importoMedioOperazione() {
		return operazioniBancarieMongoRepository.importoMedioOperazioni();
	}

}
