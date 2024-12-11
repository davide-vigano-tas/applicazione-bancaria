package eu.tasgroup.applicativo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Transazione;
import eu.tasgroup.applicativo.repository.TransazioneRepository;
import eu.tasgroup.applicativo.service.TransazioneService;

@Service
public class TransazioneServiceImpl implements TransazioneService {

	@Autowired
	TransazioneRepository transazioneRepository;

	@Override
	public Transazione createOrUpdate(Transazione transazione) {
		return transazioneRepository.saveAndFlush(transazione);
	}

	@Override
	public void deleteTransazioneById(long id) {
		transazioneRepository.deleteById(id);
	}

	@Override
	public Optional<Transazione> getTransazioneById(long id) {
		return transazioneRepository.findById(id);
	}

	@Override
	public List<Transazione> getAll() {
		return transazioneRepository.findAll();
	} 
}
