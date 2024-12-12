package eu.tasgroup.applicativo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Prestito;
import eu.tasgroup.applicativo.repository.PrestitoRepository;
import eu.tasgroup.applicativo.service.PrestitoService;

@Service
public class PrestitoServiceImpl implements PrestitoService {

	@Autowired
	PrestitoRepository prestitoRepository;

	@Override
	public Prestito createOrUpdate(Prestito prestito) {
		return prestitoRepository.saveAndFlush(prestito);
	}

	@Override
	public void deletePrestitoById(long id) {
		prestitoRepository.deleteById(id);
	}

	@Override
	public Optional<Prestito> getPrestitoById(long id) {
		return prestitoRepository.findById(id);
	}

	@Override
	public List<Prestito> getAll() {
		return prestitoRepository.findAll();
	}

	@Override
	public double sumPrestitiByCliente(long id) {
		return prestitoRepository.sumPrestitiByCliente(id);
	}
}
