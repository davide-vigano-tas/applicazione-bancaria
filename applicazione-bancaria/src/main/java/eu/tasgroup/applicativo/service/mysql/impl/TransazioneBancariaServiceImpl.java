package eu.tasgroup.applicativo.service.mysql.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.TransazioneBancaria;
import eu.tasgroup.applicativo.repository.mysql.TransazioneBancariaRepository;
import eu.tasgroup.applicativo.service.mysql.TransazioneBancariaService;

public class TransazioneBancariaServiceImpl implements TransazioneBancariaService {

	@Autowired
	TransazioneBancariaRepository tbr;

	@Override
	public TransazioneBancaria createOrUpdate(TransazioneBancaria transazioneBancaria) {
		return tbr.saveAndFlush(transazioneBancaria);
	}

	@Override
	public void deleteTransazioneBancariaById(long id) {
		tbr.deleteById(id);	
	}

	@Override
	public Optional<TransazioneBancaria> getTransazioneBancariaById(long id) {
		return tbr.findById(id);
	}

	@Override
	public List<TransazioneBancaria> getAll() {
		return tbr.findAll();
	} 
}
