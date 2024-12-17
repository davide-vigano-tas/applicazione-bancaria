package eu.tasgroup.applicativo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.AuditLog;
import eu.tasgroup.applicativo.repository.AuditLogRepository;
import eu.tasgroup.applicativo.service.AuditLogService;
@Service
public class AuditLogServiceImpl implements AuditLogService {
	@Autowired
	AuditLogRepository auditLogRepository;

	@Override
	public AuditLog createOrUpdate(AuditLog log) {
		return auditLogRepository.saveAndFlush(log);
	}

	@Override
	public Optional<AuditLog> findById(long id) {
		return auditLogRepository.findById(id);
	}

	@Override
	public List<AuditLog> findByAdminId(long id) {
		return auditLogRepository.findByAdminId(id);
	}

	@Override
	public List<AuditLog> findByAdminEmail(String email) {
		return auditLogRepository.findByAdminEmail(email);
	}

	@Override
	public void deleteById(long id) {
		auditLogRepository.deleteById(id);
		
	}


}
