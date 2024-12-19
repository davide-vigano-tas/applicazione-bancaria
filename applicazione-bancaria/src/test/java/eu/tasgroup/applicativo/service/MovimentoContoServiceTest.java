package eu.tasgroup.applicativo.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoConto;
import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoMovimento;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Conto;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.MovimentoConto;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovimentoContoServiceTest {
	
	@Autowired
	private ClientiService clienteService;
	@Autowired
	private ContiService contiService;
	@Autowired
	private MovimentoContoService mcs;
	
	private static Cliente cliente;
	private static Conto conto; 
	private static MovimentoConto movimentoConto;

	@BeforeAll
	static void setUpBeforeClass() {
		cliente = new Cliente();
		
		cliente.setNomeCliente("Paolo");
		cliente.setCognomeCliente("Rossi");
		cliente.setEmailCliente("mail@mail.it");
		cliente.setPasswordCliente("pass01$1");
		cliente.setSaldoConto(300);
		
		conto = new Conto();
		conto.setSaldo(1000);
		conto.setTipoConto(TipoConto.CORRENTE);
		
		movimentoConto = new MovimentoConto();
		movimentoConto.setDataMovimento(new Date());
		movimentoConto.setImporto(100);
		movimentoConto.setTipoMovimento(TipoMovimento.ACCREDITO);
	}
	@Test
	@Order(1)
	void testCreateOrUpdate() {
		cliente = clienteService.createOrUpdate(cliente);
		
		conto.setCliente(cliente);
		cliente.getConti().add(conto);
		cliente = clienteService.createOrUpdate(cliente);
		
		// recupero conto creato
		conto = contiService.getAll().get(0);
		
		movimentoConto.setConto(conto);
		movimentoConto = mcs.createOrUpdate(movimentoConto);
		
		assertNotNull(movimentoConto);
	}

	@Test
	@Order(2)
	void testGetMovimentoContoById() {
		assertTrue(mcs.getMovimentoContoById(movimentoConto.getCodMovimento()).isPresent());
	}

	@Test
	@Order(3)
	void testGetAll() {
		assertFalse(mcs.getAll().isEmpty());
	}
	
	@Test
	@Order(4)
	void testDeleteMovimentoContoById() {
		mcs.deleteMovimentoContoById(movimentoConto.getCodMovimento());
		assertTrue(mcs.getAll().isEmpty());
		
		conto = contiService.findById(conto.getCodConto()).get();
		contiService.deleteContoById(conto.getCodConto());
		assertTrue(contiService.getAll().isEmpty());
		
		cliente = clienteService.findById(cliente.getCodCliente()).get();
		clienteService.deleteCliente(cliente);
		assertTrue(clienteService.getClientiList().isEmpty());
	}

}
