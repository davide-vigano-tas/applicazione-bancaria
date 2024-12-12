package eu.tasgroup.applicativo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.repository.ClientiRepository;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientiRepositoryTest {
	
	@Autowired
	ClientiRepository clientRepository;
	
	private static Cliente cliente;
	private static Cliente cliente2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		cliente = new Cliente();

		cliente.setAccountBloccato(false);
		cliente.setNomeCliente("Samuel");
		cliente.setCognomeCliente("Mastrelli");
		cliente.setEmailCliente("sam@prova.email");
		cliente.setPasswordCliente("pass01$");
		cliente.setSaldoConto(300);
		
		
		cliente2 = new Cliente();

		cliente2.setAccountBloccato(false);
		cliente2.setNomeCliente("Davide");
		cliente2.setCognomeCliente("Vigano");
		cliente2.setEmailCliente("davide@prova.email");
		cliente2.setPasswordCliente("pass01$");
		cliente2.setSaldoConto(250);
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
	
	@Test
	@Order(2)
	void deletetest() {
		try {
			
			clientRepository.delete(cliente);
			clientRepository.delete(cliente2);
	
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
