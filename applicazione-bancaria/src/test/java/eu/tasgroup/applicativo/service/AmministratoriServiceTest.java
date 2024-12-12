package eu.tasgroup.applicativo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AmministratoriServiceTest {

	@Autowired 
    private AmministratoriService as;
	
	private static Amministratore admin;

	@BeforeAll
	static void setUpBeforeClass() {
		admin = new Amministratore();
		admin.setNomeAdmin("Paolo");
		admin.setCognomeAdmin("Rossi");
		admin.setEmailAdmin("mail@mail.it");
		admin.setPasswordAdmin("pass");
	}
	
	@Test
	@Order(1)
	void testCreateOrUpdate() {
		admin = as.createOrUpdate(admin);		
		assertNotNull(admin);
	}
	
	@Test
	@Order(2)
	void testFindByEmailAdmin() {
		assertNotNull(as.findByEmailAdmin(admin.getEmailAdmin()));
	}

	@Test
	@Order(3)
	void testFindById() {
		assertNotNull(as.findById(admin.getCodAdmin()));
	}

	@Test
	@Order(4)
	void testFindAll() {
		assertNotNull(as.findAll());
	}
	
	@Test
	@Order(5)
	void testUpdate() {
		admin.setNomeAdmin("Lucia");
		as.createOrUpdate(admin);
		
		assertEquals("Lucia", as.findById(admin.getCodAdmin()).get().getNomeAdmin());
	}

	@Test
	@Order(6)
	void testDeleteAdmin() {
		as.deleteAdmin(admin);
		assertTrue(as.findAll().isEmpty());
	}

}
