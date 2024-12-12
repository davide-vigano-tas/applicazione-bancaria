package eu.tasgroup.applicativo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Conto;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.MovimentoConto;

@Repository
public interface MovimentoContoRepository extends JpaRepository<MovimentoConto, Long> {
	
	List<MovimentoConto> findByConto(Conto conto);
}
