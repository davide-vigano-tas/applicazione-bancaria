package eu.tasgroup.applicativo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Carta;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartaServiceTest {

	@Autowired 
    private CartaService cartaService;
	@Autowired
	private ClientiService clienteService;
	
	private static Carta carta;
	private static Cliente cliente;

	@BeforeAll
	static void setUpBeforeClass() {
		cliente = new Cliente();
		
		cliente.setNomeCliente("Paolo");
		cliente.setCognomeCliente("Rossi");
		cliente.setEmailCliente("mail@mail.it");
		cliente.setPasswordCliente("pass");
		cliente.setSaldoConto(300);
		
		carta = new Carta();
		carta.setCvv("111");
		carta.setNumeroCarta("12345-67890-09876-54321");
		carta.setDataScadenza(new Date());
	}
	
	@Test
	@Order(1)
	void testCreateOrUpdate() {
		cliente = clienteService.createOrUpdate(cliente);
		
		cliente.getCarte().add(carta);
		carta.setCliente(cliente);
		
		cliente = clienteService.createOrUpdate(cliente);
		
		// mi serve per recuperare l'ID della Carta
		carta = cartaService.getAll().get(0); 
		
		assertTrue(cartaService.getCartaById(carta.getCodCarta()).isPresent());
	}
	
	@Test
	@Order(2)
	void testGetCartaById() {
		assertTrue(cartaService.getCartaById(carta.getCodCarta()).isPresent());
	}

	@Test
	@Order(3)
	void testGetAll() {
		assertTrue(!cartaService.getAll().isEmpty());
	}
	
	@Test
	@Order(4)
	void testUpdate() {
		carta.setCvv("999");
		cartaService.createOrUpdate(carta);
		assertEquals("999", cartaService.getCartaById(carta.getCodCarta()).get().getCvv());
	}
	
	@Test
	@Order(5)
	void testDeleteCartaById() {
		cartaService.deleteCartaById(carta.getCodCarta());
		
		cliente = clienteService.findById(cliente.getCodCliente()).get();
		clienteService.deleteCliente(cliente);
		
		assertTrue(cartaService.getAll().isEmpty());
		assertTrue(clienteService.getClientiList().isEmpty());
	}
}
