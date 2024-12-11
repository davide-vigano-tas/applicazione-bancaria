package eu.tasgroup.applicativo.repository.mysql;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.repository.ClientiRepository;

class ClientiRepositoryTest {
	
	@Autowired
	ClientiRepository clientRepository;
	
	private static Cliente cliente;
	private static Cliente cliente2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		cliente = new Cliente();
		cliente.setCodCliente(1);
		cliente.setAccountBloccato(false);
		cliente.setNomeCliente("Samuel");
		cliente.setCognomeCliente("Mastrelli");
		cliente.setEmailCliente("sam@prova.email");
		cliente.setPasswordCliente("pass01$");
		cliente.setSaldoConto(300);
		
		
		cliente2 = new Cliente();
		cliente2.setCodCliente(2);
		cliente2.setAccountBloccato(false);
		cliente2.setNomeCliente("Davide");
		cliente2.setCognomeCliente("Vigano");
		cliente2.setEmailCliente("davide@prova.email");
		cliente2.setPasswordCliente("pass01$");
		cliente2.setSaldoConto(250);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	@Order(1)
	void testClientiSaldoMax() {
		try {
		
			clientRepository.save(cliente);
			clientRepository.save(cliente2);
			List<Cliente> cliSaldoMax = clientRepository.clientiSaldoMax();
			assertEquals(1, cliSaldoMax.size());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
