package eu.tasgroup.applicativo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoConto;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Conto;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContiServiceTest {
	
	@Autowired
	ClientiService clientiService;
	
	@Autowired
	ContiService contiService;
	
	static Cliente cliente;
	static Conto conto;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		cliente = new Cliente();
		cliente.setNomeCliente("Samuel");
		cliente.setCognomeCliente("Mastrelli");
		cliente.setEmailCliente("sam@prova.com");
		cliente.setPasswordCliente("pass01");
	
		conto = new Conto();
		conto.setSaldo(0);
		conto.setTipoConto(TipoConto.CORRENTE);
		conto.setCliente(cliente);
		
		cliente.getConti().add(conto);
		
	}

	@Test
	@Order(1)
	void testCreate() {
		try {
			cliente = clientiService.createOrUpdate(cliente);
			conto = contiService.createOrUpdate(conto);
		
			assertNotNull(conto);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	
	@Test
	@Order(2)
	void testContiSaldoZero() {
		try {
			List<Conto> czero = contiService.getContiSaldoZero();
			assertEquals(1, czero.size());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(3)
	void testDelete() {
		try {
	
			
			contiService.deleteContoById(conto.getCodConto());
			System.err.println(contiService.findById(conto.getCodConto()));
			
			assertFalse(contiService.findById(conto.getCodConto()).isPresent());
			clientiService.deleteCliente(clientiService.findById(cliente.getCodCliente()).get());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	

}
