package eu.tasgroup.applicativo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.LogAccessi;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LogAccessiServiceTest {
	
	@Autowired
	AmministratoriService amministratoriService;
	
	@Autowired
	LogAccessiService logAccessiService;
	
	static Amministratore amministratore;
	static LogAccessi log1;
	static LogAccessi log2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		amministratore = new Amministratore();
		amministratore.setNomeAdmin("sam");
		amministratore.setCognomeAdmin("mast");
		amministratore.setEmailAdmin("sam@mail.it");
		amministratore.setPasswordAdmin("pass");
		
		log1 = new LogAccessi();
		log1.setOperazione("LOGIN");
		
		log2 = new LogAccessi();
		log2.setOperazione("BLOCK_ACCOUNT");
	}

	@Test
	@Order(1)
	void testCreate() {
		try {
			amministratore = amministratoriService.createOrUpdate(amministratore);
			log1.setAdmin(amministratore);
			log1.setDataLog(new Date());
			log1 = logAccessiService.createOrUpdate(log1);
			
			
			log2.setAdmin(amministratore);
			log2.setDataLog(new Date());
			log2 = logAccessiService.createOrUpdate(log2);
			
			
			
			assertTrue(logAccessiService.findById(log1.getCodAccessi()).isPresent());
			assertTrue(logAccessiService.findById(log2.getCodAccessi()).isPresent());
			
			assertTrue(amministratoriService.findById(amministratore.getCodAdmin()).isPresent());
			
			
		}catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(2)
	void testFind() {
		try {
			
			List<LogAccessi> logsByAdminId = logAccessiService.findByAdminId(amministratore.getCodAdmin());
			List<LogAccessi> logsByAdminEmail = logAccessiService.findByAdminEmail(amministratore.getEmailAdmin());
			
			assertEquals(2, logsByAdminId.size());
			assertEquals(2, logsByAdminEmail.size());
			
		}catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	
	@Test
	@Order(3)
	void testDelete() {
		try {
			
			logAccessiService.deleteById(log1.getCodAccessi());
			logAccessiService.deleteById(log2.getCodAccessi());
			amministratoriService.deleteAdmin(amministratore);
			
			assertFalse(logAccessiService.findById(log1.getCodAccessi()).isPresent());
			assertFalse(logAccessiService.findById(log2.getCodAccessi()).isPresent());
			
			assertFalse(amministratoriService.findById(amministratore.getCodAdmin()).isPresent());
			
		}catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
