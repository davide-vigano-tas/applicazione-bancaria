package eu.tasgroup.applicativo.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Prestito;

public interface PrestitoRepository extends JpaRepository<Prestito, Long> {

}
