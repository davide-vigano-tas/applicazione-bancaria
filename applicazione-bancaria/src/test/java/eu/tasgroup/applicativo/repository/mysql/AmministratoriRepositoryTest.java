package eu.tasgroup.applicativo.repository.mysql;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;

@SpringBootTest
@DataJpaTest
class AmministratoriRepositoryTest {
	@Autowired 
	private AmministratoriRepository ar;
	@Test
	void testFindByEmailAdmin() {
		Amministratore admin = new Amministratore();
		
		admin.setNomeAdmin("Prova");
		admin.setCognomeAdmin("Prova");
		admin.setEmailAdmin("mail@mail.it");
		admin.setPasswordAdmin("pass");
		
		ar.save(admin);
		
		Optional<Amministratore> adminSalvato = ar.findByEmailAdmin(admin.getEmailAdmin());
		
		assertTrue(adminSalvato.isPresent());
		assertTrue(adminSalvato.get().getEmailAdmin().equals("mail@mail.it"));
		
	}

}
