package eu.tasgroup.applicativo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.tasgroup.applicativo.businesscomponent.enumerated.StatoRichiestaPrestito;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.RichiestaPrestito;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RichiestePrestitoServiceTest {
	
	@Autowired
	RichiestePrestitoService richiestePrestitoService;
	
	@Autowired
	ClientiService clientiService;
	
	static Cliente cliente;
	
	static RichiestaPrestito r1;
	static RichiestaPrestito r2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		cliente = new Cliente();
		cliente.setNomeCliente("Samuel");
		cliente.setCognomeCliente("Mastrelli");
		cliente.setEmailCliente("sam@prova.com");
		cliente.setPasswordCliente("pass01");
		
		
		r1 = new RichiestaPrestito();
		r1.setCliente(cliente);
		r1.setImporto(200);
		r1.setStato(StatoRichiestaPrestito.IN_ATTESA);
		r1.setDataRichiesta(new GregorianCalendar(2024, 12, 4, 0, 0, 0).getTime());
		
		r2 = new RichiestaPrestito();
		r2.setCliente(cliente);
		r2.setImporto(450);
		r2.setStato(StatoRichiestaPrestito.APPROVATO);
		r2.setDataRichiesta(new GregorianCalendar(2024, 11, 4, 0, 0, 0).getTime());
		
	}

	@Test
	@Order(1)
	void testCreate() {
		try {
			cliente = clientiService.createOrUpdate(cliente);
			r1 = richiestePrestitoService.createOrUpdate(r1);
			r2 = richiestePrestitoService.createOrUpdate(r2);
			
			assertTrue(richiestePrestitoService.findById(r1.getCodRichiesta()).isPresent());
			assertTrue(richiestePrestitoService.findById(r2.getCodRichiesta()).isPresent());
		}catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(2)
	void testFindByStatus() {
		try {
			List<RichiestaPrestito> attesa =
					richiestePrestitoService.findByStatus(StatoRichiestaPrestito.IN_ATTESA);
			List<RichiestaPrestito> approvato =
					richiestePrestitoService.findByStatus(StatoRichiestaPrestito.APPROVATO);
			
			assertTrue(attesa.contains(r1));
			assertTrue(approvato.contains(r2));
		}catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	
	@Test
	@Order(3)
	void testDelete() {
		try {
			
			richiestePrestitoService.deletePrestitoById(r1.getCodRichiesta());
			richiestePrestitoService.deletePrestitoById(r2.getCodRichiesta());
			
			assertFalse(richiestePrestitoService.findById(r1.getCodRichiesta()).isPresent());
			assertFalse(richiestePrestitoService.findById(r2.getCodRichiesta()).isPresent());
			
			
			clientiService.deleteCliente(clientiService.findById(cliente.getCodCliente()).get());
		}catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
