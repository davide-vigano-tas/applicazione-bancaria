package eu.tasgroup.applicativo.service;

import java.util.List;
import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.AuditLog;

public interface AuditLogService {
	AuditLog createOrUpdate(AuditLog log);
	Optional<AuditLog> findById(long id);
	List<AuditLog> findByAdminId(long id);
	List<AuditLog> findByAdminEmail(String email);
	void deleteById(long id);

}
