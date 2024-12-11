package eu.tasgroup.applicativo.repository.mysql;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.RichiestaPrestito;
import eu.tasgroup.applicativo.businesscomponent.enumerated.StatoRichiestaPrestito;

@Repository
public interface RichiestePrestitoRepository extends JpaRepository<RichiestaPrestito, Long> {
	
	List<RichiestaPrestito> findByStato(StatoRichiestaPrestito stato);

}
