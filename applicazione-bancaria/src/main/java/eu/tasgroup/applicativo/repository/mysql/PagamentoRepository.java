package eu.tasgroup.applicativo.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

}
