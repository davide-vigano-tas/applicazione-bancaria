package eu.tasgroup.applicativo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.LogAccessi;
import eu.tasgroup.applicativo.repository.LogAccessiRepository;
import eu.tasgroup.applicativo.service.LogAccessiService;
@Service
public class LogAccessiServiceImpl implements LogAccessiService {
	@Autowired
	LogAccessiRepository logAccessiRepository;

	@Override
	public LogAccessi createOrUpdate(LogAccessi log) {
		return logAccessiRepository.saveAndFlush(log);
	}

	@Override
	public Optional<LogAccessi> findById(long id) {
		return logAccessiRepository.findById(id);
	}

	@Override
	public List<LogAccessi> findByAdminId(long id) {
		return logAccessiRepository.findByAdminId(id);
	}

	@Override
	public List<LogAccessi> findByAdminEmail(String email) {
		return logAccessiRepository.findByAdminEmail(email);
	}

	@Override
	public void deleteById(long id) {
		logAccessiRepository.deleteById(id);
		
	}

}
