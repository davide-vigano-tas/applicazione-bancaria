package eu.tasgroup.applicativo.repository;

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
class PrestitoRepositoryTest {
	@Autowired
	PrestitoRepository prestitoRepository;
	
	@Autowired
	ClientiRepository clientiRepository;
	
	static Cliente cliente;
	static Prestito prestito1;
	static Prestito prestito2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		cliente = new Cliente();

		cliente.setAccountBloccato(false);
		cliente.setNomeCliente("Samuel");
		cliente.setCognomeCliente("Mastrelli");
		cliente.setEmailCliente("sam@prova.email");
		cliente.setPasswordCliente("pass01$");
		cliente.setSaldoConto(300);
		
		
		prestito1 = new Prestito();
		
		prestito1.setDurataMesi(4);
		prestito1.setImporto(300);
		prestito1.setTassoInteresse(0.3);
		
		prestito2 = new Prestito();
		
		prestito2.setDurataMesi(7);
		prestito2.setImporto(300);
		prestito2.setTassoInteresse(0.4);
		
		
		
		
	}

	@Test
	@Order(1)
	void testSumPrestitiByCliente() {
		try {
			Cliente clienteCreato = clientiRepository.save(cliente);
			prestito1.setCliente(clienteCreato);
			prestito2.setCliente(clienteCreato);
			
			prestitoRepository.save(prestito1);
			prestitoRepository.save(prestito2);
			
			double sum = prestitoRepository.sumPrestitiByCliente(clienteCreato.getCodCliente());
			assertEquals(600.0, sum);
			
		}catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	@Order(2)
	void deleteTest() {
		try {

			prestitoRepository.delete(prestito1);
			prestitoRepository.delete(prestito2);
			
			clientiRepository.delete(cliente);
			
	
		}catch (Exception e) {
			fail(e.getMessage());
		}
	}
	

}
