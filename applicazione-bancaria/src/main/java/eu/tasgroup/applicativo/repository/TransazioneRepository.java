package eu.tasgroup.applicativo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Transazione;

@Repository
public interface TransazioneRepository extends JpaRepository<Transazione, Long> {

}
