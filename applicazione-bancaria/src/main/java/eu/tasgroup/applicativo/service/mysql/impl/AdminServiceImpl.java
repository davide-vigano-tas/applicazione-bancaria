package eu.tasgroup.applicativo.service.mysql.impl;

import java.util.Optional;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;
import eu.tasgroup.applicativo.repository.mysql.AmministratoriRepository;
import eu.tasgroup.applicativo.service.mysql.AdminService;

public class AdminServiceImpl implements AdminService {

	private AmministratoriRepository amministratoriRepository;

	public AdminServiceImpl(AmministratoriRepository amministratoriRepository) {
		this.amministratoriRepository = amministratoriRepository;
	}

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

}
