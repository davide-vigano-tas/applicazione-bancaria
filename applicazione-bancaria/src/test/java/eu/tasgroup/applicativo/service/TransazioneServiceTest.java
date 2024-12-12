package eu.tasgroup.applicativo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.GregorianCalendar;

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
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Transazione;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransazioneServiceTest {
	
	@Autowired
	TransazioneService transazioneService;
	
	@Autowired
	ClientiService clientiService;
	
	@Autowired
	ContiService contiService;
	
	static Cliente cliente;
	static Conto conto;
	
	static Transazione t1;
	static Transazione t2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		cliente = new Cliente();
		cliente.setNomeCliente("Samuel");
		cliente.setCognomeCliente("Mastrelli");
		cliente.setEmailCliente("sam@prova.com");
		cliente.setPasswordCliente("pass01");
	
		conto = new Conto();
		conto.setSaldo(30);
		conto.setTipoConto(TipoConto.CORRENTE);
		conto.setCliente(cliente);
		
		t1 = new Transazione();
		t1.setConto(conto);
		t1.setDataTransazione(new Date());
		t1.setImporto(300);
		t1.setTipoTransazione(TipoTransazione.ACCREDITO);
		
		t2 = new Transazione();
		t2.setConto(conto);
		t2.setDataTransazione(new GregorianCalendar(2024, 12, 3, 0, 30, 2).getTime());
		t2.setImporto(450);
		t2.setTipoTransazione(TipoTransazione.ADDEBITO);
	}

	@Test
	@Order(1)
	void testCreate() {
		try {
			
			cliente = clientiService.createOrUpdate(cliente);
			conto = contiService.createOrUpdate(conto);
			
			t1 = transazioneService.createOrUpdate(t1);
			t2 = transazioneService.createOrUpdate(t2);
			
			assertTrue(transazioneService.getTransazioneById(t1.getCodTransazione()).isPresent());
			assertTrue(transazioneService.getTransazioneById(t2.getCodTransazione()).isPresent());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(2)
	void testDelete() {
		try {
			
		
			
			transazioneService.deleteTransazioneById(t1.getCodTransazione());
			transazioneService.deleteTransazioneById(t2.getCodTransazione());
			
			assertFalse(transazioneService.getTransazioneById(t1.getCodTransazione()).isPresent());
			assertFalse(transazioneService.getTransazioneById(t2.getCodTransazione()).isPresent());
			
			contiService.deleteContoById(conto.getCodConto());
			clientiService.deleteCliente(clientiService.findById(cliente.getCodCliente()).get());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
