package eu.tasgroup.applicativo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Pagamento;
import eu.tasgroup.applicativo.repository.PagamentoRepository;
import eu.tasgroup.applicativo.service.PagamentoService;

@Service
public class PagamentoServiceImpl implements PagamentoService {
	
	@Autowired
	PagamentoRepository pagamentoRepository;

	@Override
	public Pagamento createOrUpdate(Pagamento pagamento) {
		return pagamentoRepository.saveAndFlush(pagamento);
	}

	@Override
	public void deletePagamentoById(long id) {
		pagamentoRepository.deleteById(id);
	}

	@Override
	public Optional<Pagamento> getPagamentoById(long id) {
		return pagamentoRepository.findById(id);
	}

	@Override
	public List<Pagamento> getAll() {
		return pagamentoRepository.findAll();
	}

	@Override
	public Double sumPagamentiByCliente(long id) {
		if(pagamentoRepository.sumPagamentiByCliente(id)!= null) {
			
			return pagamentoRepository.sumPagamentiByCliente(id);
		}
		return 0.00;
	}
	
	
}
