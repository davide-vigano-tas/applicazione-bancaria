package eu.tasgroup.applicativo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Prestito;

@Repository
public interface PrestitoRepository extends JpaRepository<Prestito, Long> {

	
	@Query("Select sum(p.importo) from Prestito p where p.cliente.codCliente = ?1")
	Double sumPrestitiByCliente(long cod_cliente);
}
