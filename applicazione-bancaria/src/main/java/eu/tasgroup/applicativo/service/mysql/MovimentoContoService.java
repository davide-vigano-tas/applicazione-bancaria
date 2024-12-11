package eu.tasgroup.applicativo.service.mysql;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.MovimentoConto;

public interface MovimentoContoService {
	MovimentoConto createOrUpdate(MovimentoConto movimentoConto);
	
	void deleteMovimentoContoById(long id);
	
	Optional<MovimentoConto> getMovimentoContoById(long id);
	
	List<MovimentoConto> getAll();
}
