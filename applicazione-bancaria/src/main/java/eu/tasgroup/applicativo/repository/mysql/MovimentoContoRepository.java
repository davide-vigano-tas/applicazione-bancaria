package eu.tasgroup.applicativo.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.MovimentoConto;

public interface MovimentoContoRepository extends JpaRepository<MovimentoConto, Long> {

}
