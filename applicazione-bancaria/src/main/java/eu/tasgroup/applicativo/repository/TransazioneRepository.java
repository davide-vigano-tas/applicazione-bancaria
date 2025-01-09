package eu.tasgroup.applicativo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Conto;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Transazione;

@Repository
public interface TransazioneRepository extends JpaRepository<Transazione, Long> {
	
	List<Transazione> findByContoAndDataTransazioneBetween(Conto conto,Date data1, Date data2);
}
