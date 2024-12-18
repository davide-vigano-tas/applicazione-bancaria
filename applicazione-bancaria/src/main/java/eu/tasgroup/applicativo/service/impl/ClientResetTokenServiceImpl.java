package eu.tasgroup.applicativo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.ClientResetToken;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.repository.ClientResetTokenRepository;
import eu.tasgroup.applicativo.service.ClientResetTokenService;

@Service
public class ClientResetTokenServiceImpl implements ClientResetTokenService {
	@Autowired
	ClientResetTokenRepository clientResetTokenRepository;

	@Override
	public ClientResetToken create(ClientResetToken token) {
		return clientResetTokenRepository.save(token);
	}

	@Override
	public Optional<ClientResetToken> findByClientId(long id) {
		return clientResetTokenRepository.findByClientId(id);
	}

	@Override
	public Optional<ClientResetToken> findByClientEmail(String email) {
		return clientResetTokenRepository.findByClientEmail(email);
	}

	@Override
	public void delete(ClientResetToken token) {
		clientResetTokenRepository.delete(token);
	}

	@Override
	public Optional<ClientResetToken> findByToken(String token) {
		return clientResetTokenRepository.findByToken(token);
	}

	@Override
	public Optional<Cliente> getClientByToken(String token) {
		return clientResetTokenRepository.getClientByToken(token);
	}

}
