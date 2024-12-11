package eu.tasgroup.applicativo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.MovimentoConto;
import eu.tasgroup.applicativo.repository.MovimentoContoRepository;
import eu.tasgroup.applicativo.service.MovimentoContoService;

@Service
public class MovimentoContoServiceImpl implements MovimentoContoService{
	
	@Autowired
	MovimentoContoRepository mcr;

	@Override
	public MovimentoConto createOrUpdate(MovimentoConto movimentoConto) {
		return mcr.saveAndFlush(movimentoConto);
	}

	@Override
	public void deleteMovimentoContoById(long id) {
		mcr.deleteById(id);
	}

	@Override
	public Optional<MovimentoConto> getMovimentoContoById(long id) {
		return mcr.findById(id);
	}

	@Override
	public List<MovimentoConto> getAll() {
		return mcr.findAll();
	}
	
}
