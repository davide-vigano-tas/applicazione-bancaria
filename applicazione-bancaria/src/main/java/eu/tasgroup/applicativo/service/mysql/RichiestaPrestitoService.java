package eu.tasgroup.applicativo.service.mysql;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.RichiestaPrestito;

public interface RichiestaPrestitoService {
	List<RichiestaPrestito> getListaPrestitiRichiesti();
	RichiestaPrestito createOrUpdate();
	Optional<RichiestaPrestito> findById(long id);
	List<RichiestaPrestito> findByStatus(String status);
}
