package eu.tasgroup.applicativo.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Transazione;

public interface TransazioneRepository extends JpaRepository<Transazione, Long> {

}
