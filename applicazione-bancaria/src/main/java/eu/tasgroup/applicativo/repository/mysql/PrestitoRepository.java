package eu.tasgroup.applicativo.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Prestito;

public interface PrestitoRepository extends JpaRepository<Prestito, Long> {

	
	@Query("Select sum(p.importo) from Prestito p where c.cod_cliente = ?1")
	double sumPrestitiByCliente(long cod_cliente);
}
