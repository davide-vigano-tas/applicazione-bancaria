package eu.tasgroup.applicativo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.businesscomponent.enumerated.Role;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.PermessiAmministratori;
import eu.tasgroup.applicativo.repository.PermessiAmministratoriRepository;
import eu.tasgroup.applicativo.service.PermessiAmministratoriService;

@Service
public class PermessiAmministratoriServiceImpl implements PermessiAmministratoriService {
	
	@Autowired
	PermessiAmministratoriRepository permessiAmministratoriRepository;

	@Override
	public PermessiAmministratori createOrUpdate(PermessiAmministratori p) {
		return permessiAmministratoriRepository.save(p);
	}

	@Override
	public Optional<PermessiAmministratori> findById(long id) {
		return permessiAmministratoriRepository.findById(id);
	}

	@Override
	public List<PermessiAmministratori> getByAdminId(long id) {
		return permessiAmministratoriRepository.getByAdminId(id);
	}

	@Override
	public List<Amministratore> getAdminByRole(Role role) {
		return permessiAmministratoriRepository.getAdminByRole(role);
	}

}
