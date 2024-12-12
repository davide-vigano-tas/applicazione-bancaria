package eu.tasgroup.applicativo.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Prestito;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PrestitoServiceTest {
	
	@Autowired
	PrestitoService prestitoService;
	
	@Autowired
	ClientiService clientiService;
	
	static Cliente cliente;
	
	
	static Prestito p1;
	static Prestito p2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		cliente = new Cliente();
		cliente.setNomeCliente("Samuel");
		cliente.setCognomeCliente("Mastrelli");
		cliente.setEmailCliente("sam@prova.com");
		cliente.setPasswordCliente("pass01");
		
		p1 = new Prestito();
		p1.setCliente(cliente);
		p1.setDurataMesi(4);
		p1.setImporto(300);
		p1.setTassoInteresse(0.3);
		
		p2 = new Prestito();
		p2.setCliente(cliente);
		p2.setDurataMesi(2);
		p2.setImporto(330);
		p2.setTassoInteresse(0.2);
	}

	@Test
	@Order(1)
	void testCreate() {
		try {
			cliente = clientiService.createOrUpdate(cliente);
			p1 = prestitoService.createOrUpdate(p1);
			p2 = prestitoService.createOrUpdate(p2);
			
			assertTrue(prestitoService.getPrestitoById(p1.getCodPrestito()).isPresent());
			assertTrue(prestitoService.getPrestitoById(p2.getCodPrestito()).isPresent());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(2)
	void testSumByPrestitiCliente() {
		try {
			double sum = prestitoService.sumPrestitiByCliente(cliente.getCodCliente());
			
			assertEquals(630.0, sum);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(3)
	void testDelete() {
		try {
	
			prestitoService.deletePrestitoById(p1.getCodPrestito());
			prestitoService.deletePrestitoById(p2.getCodPrestito());
			
			assertFalse(prestitoService.getPrestitoById(p1.getCodPrestito()).isPresent());
			assertFalse(prestitoService.getPrestitoById(p2.getCodPrestito()).isPresent());
			
			clientiService.deleteCliente(clientiService.findById(cliente.getCodCliente()).get());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
