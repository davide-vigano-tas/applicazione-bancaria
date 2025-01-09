package eu.tasgroup.applicativo.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Conto;
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

	@Override
	public List<Transazione> getByDates(Conto conto, Date dataInizio, Date dataFine) {
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(dataInizio);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		dataInizio= cal.getTime();
		
		cal.setTime(dataFine);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
	
		dataFine= cal.getTime();
		return transazioneRepository.findByContoAndDataTransazioneBetween( conto, dataInizio, dataFine);
	}
}
