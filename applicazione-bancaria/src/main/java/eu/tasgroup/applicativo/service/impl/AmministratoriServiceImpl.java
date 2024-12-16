package eu.tasgroup.applicativo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;
import eu.tasgroup.applicativo.repository.AmministratoriRepository;
import eu.tasgroup.applicativo.security.AdminOnly;
import eu.tasgroup.applicativo.service.AmministratoriService;

@Service
public class AmministratoriServiceImpl implements AmministratoriService {

	@Autowired
	private AmministratoriRepository amministratoriRepository;
	
	@AdminOnly
	@Override
	public Optional<Amministratore> findByEmailAdmin(String email) {
		return amministratoriRepository.findByEmailAdmin(email);
	}

	@Override
	public Optional<Amministratore> findById(long id) {
		return amministratoriRepository.findById(id);
	}

	@Override
	public Amministratore createOrUpdate(Amministratore amministratore) {
		return amministratoriRepository.save(amministratore);
	}

	@Override
	public List<Amministratore> findAll() {
		return amministratoriRepository.findAll();
	}

	@Override
	public void deleteAdmin(Amministratore amministratore) {
		amministratoriRepository.delete(amministratore);
	}

}
