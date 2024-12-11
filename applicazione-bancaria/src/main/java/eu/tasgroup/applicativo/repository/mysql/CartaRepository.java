package eu.tasgroup.applicativo.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Carta;

public interface CartaRepository extends JpaRepository<Carta, Long> {

}
