package eu.tasgroup.applicativo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Conto;
import eu.tasgroup.applicativo.repository.ContiRepository;
import eu.tasgroup.applicativo.service.ContiService;

@Service
public class ContiServiceImpl implements ContiService {
	@Autowired
	ContiRepository contiRepository;

	@Override
	public Double saldoMedio() {
		return contiRepository.saldoMedio() == null ? 0.0 : 
			contiRepository.saldoMedio() ;
	}

	@Override
	public void deleteContoById(long id) {
		contiRepository.deleteById(id);
	}

	@Override
	public List<Conto> getContiSaldoZero() {
		return contiRepository.getContiSaldoZero();
	}

	@Override
	public Conto createOrUpdate(Conto conto) {
		return contiRepository.saveAndFlush(conto);
	}

	@Override
	public Optional<Conto> findById(long id) {
		return contiRepository.findById(id);
	}

	@Override
	public List<Conto> getAll() {
		return contiRepository.findAll();
	}
	
	
}
