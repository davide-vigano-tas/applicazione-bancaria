package eu.tasgroup.applicativo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoMetodo;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Pagamento;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PagamentoServiceTest {

	@Autowired
	ClientiService clientiService;
	@Autowired
	PagamentoService pagamentoService;

	private static Cliente cliente;

	private static Pagamento p1;
	private static Pagamento p2;

	@BeforeAll
	static void setUpBeforeClass() {
		cliente = new Cliente();

		cliente.setNomeCliente("Paolo");
		cliente.setCognomeCliente("Rossi");
		cliente.setEmailCliente("mail@mail.it");
		cliente.setPasswordCliente("pass01$1");
		cliente.setSaldoConto(300);
		
		p1 = new Pagamento();
		p1.setDataPagamento(new Date());
		p1.setImporto(100);
		p1.setMetodoPagamento(TipoMetodo.CONTANTI);
		
		p2 = new Pagamento();
		p2.setDataPagamento(new Date());
		p2.setImporto(500);
		p2.setMetodoPagamento(TipoMetodo.BONIFICO);
	}

	@Test
	@Order(1)
	void testCreateOrUpdate() {
		cliente = clientiService.createOrUpdate(cliente);
		
		p1.setCliente(cliente);
		p2.setCliente(cliente);
		
		cliente.getPagamenti().add(p1);
		cliente.getPagamenti().add(p2);
		
		clientiService.createOrUpdate(cliente);
		
		p1 = pagamentoService.getAll().get(0);
		p2 = pagamentoService.getAll().get(1);
		
		assertNotNull(p1);
		assertNotNull(p2);
	}

	@Test
	@Order(2)
	void testGetPagamentoById() {
		assertTrue(pagamentoService.getPagamentoById(p1.getCodPagamento()).isPresent());
	}

	@Test
	@Order(3)
	void testGetAll() {
		assertFalse(pagamentoService.getAll().isEmpty());
	}

	@Test
	@Order(4)
	void testSumPagamentiByCliente() {
		assertEquals(600.00, pagamentoService.sumPagamentiByCliente(cliente.getCodCliente()));
	}
	
	@Test
	@Order(5)
	void testDelete() {
		pagamentoService.deletePagamentoById(p1.getCodPagamento());
		assertTrue(pagamentoService.getPagamentoById(p1.getCodPagamento()).isEmpty());
	}


	@AfterAll
	static void tearDownAfterClass(@Autowired PagamentoService pagamentoService, @Autowired ClientiService clientiService) {
		for (Pagamento pagamento : pagamentoService.getAll()) {
			pagamentoService.deletePagamentoById(pagamento.getCodPagamento());
		}
		
		cliente = clientiService.findById(cliente.getCodCliente()).get();
		clientiService.deleteCliente(cliente);
	}
}
