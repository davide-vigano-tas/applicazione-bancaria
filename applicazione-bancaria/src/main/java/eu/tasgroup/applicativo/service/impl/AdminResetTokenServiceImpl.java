package eu.tasgroup.applicativo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.AdminResetToken;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;
import eu.tasgroup.applicativo.repository.AdminResetTokenRepository;
import eu.tasgroup.applicativo.service.AdminResetTokenService;

@Service
public class AdminResetTokenServiceImpl implements AdminResetTokenService {
	
	@Autowired
	AdminResetTokenRepository adminResetTokenRepository;

	@Override
	public AdminResetToken create(AdminResetToken token) {
		return adminResetTokenRepository.save(token);
	}

	@Override
	public Optional<AdminResetToken> findByAdminId(long id) {
		return adminResetTokenRepository.findByAdminId(id);
	}

	@Override
	public Optional<AdminResetToken> findByAdminEmail(String email) {
		return adminResetTokenRepository.findByAdminEmail(email);
	}

	@Override
	public void delete(AdminResetToken token) {
		adminResetTokenRepository.delete(token);

	}

	@Override
	public Optional<AdminResetToken> findByToken(String token) {
		return adminResetTokenRepository.findByToken(token);
	}

	@Override
	public Optional<Amministratore> getAdminByToken(String token) {
		return adminResetTokenRepository.getAdminByToken(token);
	}

}
