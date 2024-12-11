package eu.tasgroup.applicativo.service.mysql.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Carta;
import eu.tasgroup.applicativo.repository.mysql.CartaRepository;
import eu.tasgroup.applicativo.service.mysql.CartaService;

@Service
public class CartaServiceImp implements CartaService {

	@Autowired
	CartaRepository cartaRepository;

	@Override
	public Carta createOrUpdate(Carta carta) {
		return cartaRepository.saveAndFlush(carta);
	}

	@Override
	public void deleteCartaById(long id) {
		cartaRepository.deleteById(id);
		
	}

	@Override
	public Optional<Carta> getCartaById(long id) {
		return cartaRepository.findById(id);
	}

	@Override
	public List<Carta> getAll() {
		return cartaRepository.findAll();
	} 
	
	
}
