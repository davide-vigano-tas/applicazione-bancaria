package eu.tasgroup.applicativo.service;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.LogAccessi;

public interface LogAccessiService {
	LogAccessi createOrUpdate(LogAccessi log);
	Optional<LogAccessi> findById(long id);
	List<LogAccessi> findByAdminId(long id);
	List<LogAccessi> findByAdminEmail(String email);
	void deleteById(long id);

}
