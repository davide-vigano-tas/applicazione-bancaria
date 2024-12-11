package eu.tasgroup.applicativo.service.mysql.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.businesscomponent.enumerated.StatoRichiestaPrestito;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.RichiestaPrestito;
import eu.tasgroup.applicativo.repository.mysql.RichiestePrestitoRepository;
import eu.tasgroup.applicativo.service.mysql.RichiestePrestitoService;

@Service
public class RichiestePrestitoServiceImpl implements RichiestePrestitoService {
	
	@Autowired
	RichiestePrestitoRepository rpr;

	@Override
	public RichiestaPrestito createOrUpdate(RichiestaPrestito richiestaPrestito) {
		return rpr.saveAndFlush(richiestaPrestito);
	}

	@Override
	public Optional<RichiestaPrestito> findById(long id) {
		return rpr.findById(id);
	}

	@Override
	public void deletePrestitoById(long id) {
		rpr.deleteById(id);
	}

	@Override
	public List<RichiestaPrestito> findByStatus(StatoRichiestaPrestito status) {
		return rpr.findByStato(status);
	}

	@Override
	public List<RichiestaPrestito> getListaPrestitiRichiesti() {
		return rpr.findAll();
	}
	
	
}
