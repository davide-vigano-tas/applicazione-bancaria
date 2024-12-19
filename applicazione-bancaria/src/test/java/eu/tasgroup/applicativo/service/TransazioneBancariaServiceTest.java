package eu.tasgroup.applicativo.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
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

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoConto;
import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoTransazione;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Conto;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.TransazioneBancaria;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransazioneBancariaServiceTest {
	
	@Autowired
	ClientiService clientiService;
	@Autowired
	ContiService contiService;
	@Autowired
	TransazioneBancariaService tbs;
	
	private static Cliente cliente; 
	private static Conto contoOrigin;
	private static Conto contoDest;
	private static TransazioneBancaria transazioneBancaria;
	
	
	@BeforeAll
	static void setUpBeforeClass() {
		cliente = new Cliente();
		
		cliente.setNomeCliente("Paolo");
		cliente.setCognomeCliente("Rossi");
		cliente.setEmailCliente("mailpaolo@mail.it");
		cliente.setPasswordCliente("pass01$1");
		cliente.setSaldoConto(300);
		
		contoOrigin = new Conto();
		contoOrigin.setSaldo(1000);
		contoOrigin.setTipoConto(TipoConto.CORRENTE);
		
		contoDest = new Conto();
		contoDest.setSaldo(1000);
		contoDest.setTipoConto(TipoConto.RISPARMIO);
		
		transazioneBancaria = new TransazioneBancaria();
		transazioneBancaria.setTipoTransazione(TipoTransazione.ACCREDITO);
		transazioneBancaria.setImporto(100);
		transazioneBancaria.setDataTransazione(new Date());
	}
	
	@Test
	@Order(1)
	void testCreateOrUpdate() {
		cliente = clientiService.createOrUpdate(cliente);
		
		contoOrigin.setCliente(cliente);
		contoDest.setCliente(cliente);
		
		cliente.getConti().add(contoOrigin);
		cliente.getConti().add(contoDest);
		
		System.out.println(cliente);
		
		cliente = clientiService.createOrUpdate(cliente);
		
		// recupero i conti 
		contoOrigin = contiService.getAll().get(1);
		contoDest = contiService.getAll().get(0);
		
		transazioneBancaria.setContoOrigine(contoOrigin);
		transazioneBancaria.setContoDestinazione(contoDest);
		transazioneBancaria = tbs.createOrUpdate(transazioneBancaria);
	}

	@Test
	@Order(2)
	void testGetTransazioneBancariaById() {
		assertTrue(tbs.getTransazioneBancariaById(transazioneBancaria.getCodTransazioneBancaria()).isPresent());
	}

	@Test
	@Order(3)
	void testGetAll() {
		assertFalse(tbs.getAll().isEmpty());
	}
	
	@Test
	@Order(4)
	void testDeleteTransazioneBancariaById() {
		tbs.deleteTransazioneBancariaById(transazioneBancaria.getCodTransazioneBancaria());
		assertTrue(tbs.getAll().isEmpty());
	}
	
	@AfterAll
	static void tearDownAfterClass(@Autowired ContiService contiService , @Autowired ClientiService clientiService) {
		for (Conto conto : contiService.getAll()) {
			contiService.deleteContoById(conto.getCodConto());
		}
		
		cliente = clientiService.findById(cliente.getCodCliente()).get();
		clientiService.deleteCliente(cliente);
	}

}
