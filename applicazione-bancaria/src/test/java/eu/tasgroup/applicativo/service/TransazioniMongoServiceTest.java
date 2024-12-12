package eu.tasgroup.applicativo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoTransazione;
import eu.tasgroup.applicativo.businesscomponent.model.mongo.TransazioniMongo;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransazioniMongoServiceTest {
	
	@Autowired
	TransazioniMongoService tms;
	
	static TransazioniMongo tm1;
	static TransazioniMongo tm2;
	static TransazioniMongo tm3;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		tm1 = new TransazioniMongo();
		tm1.setCliente(3);
		tm1.setCodTransazione(34);
		tm1.setDataTransazione(new Date());
		tm1.setImporto(45);
		tm1.setTipoTransazione(TipoTransazione.ACCREDITO);
		
		tm2 = new TransazioniMongo();
		tm2.setCliente(6);
		tm2.setCodTransazione(67);
		tm2.setDataTransazione(new Date());
		tm2.setImporto(500);
		tm2.setTipoTransazione(TipoTransazione.ACCREDITO);
		
		tm3 = new TransazioniMongo();
		tm3.setCliente(3);
		tm3.setCodTransazione(44);
		tm3.setDataTransazione(new GregorianCalendar(2024, 4, 22, 0, 0, 1).getTime());
		tm3.setImporto(5);
		tm3.setTipoTransazione(TipoTransazione.ADDEBITO);
	}

	@Test
	@Order(1)
	void testCreate() {
		try {
			tm1 = tms.createOrUpdate(tm1);
			tm2 = tms.createOrUpdate(tm2);
			tm3 = tms.createOrUpdate(tm3);
			
			assertTrue(tms.findById(tm1.get_id()).isPresent());
			assertTrue(tms.findById(tm2.get_id()).isPresent());
			assertTrue(tms.findById(tm3.get_id()).isPresent());
		}catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(2)
	void testNumeroPerTipo() {
		try {
			int accredito = tms.numeroTransazioniPerTipo(TipoTransazione.ACCREDITO);
			int addebito = tms.numeroTransazioniPerTipo(TipoTransazione.ADDEBITO);
			
			assertEquals(2, accredito);
			assertEquals(1, addebito);
		}catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(3)
	void testMediaTransazioniPerCliente() {
		try {
			double mediImportoTransazioni = tms.calcoloMediaTransazioniPerCliente(3);
			assertEquals(25.0, mediImportoTransazioni);
		}catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	
	@Test
	@Order(4)
	void testNumeroMedioTransazioniPerCliente() {
		try {
			double mediaTransazioni = tms.numeroMedioTransazioniPerCliente();
			assertEquals(1.5, mediaTransazioni);
		}catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(5)
	void testTotaleImportoPerMese() {
		try {
			double importoMese = tms.totaleImportoPerMese(12);
			assertEquals(545, importoMese);
		}catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(6)
	void testUltimaTransazione() {
		try {
				assertTrue(tms.ultimaTransazione().isPresent());
		}catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	
	@Test
	@Order(7)
	void testDelete() {
		try {
			tms.deleteTransazioneMongo(tm1);
			tms.deleteTransazioneMongo(tm2);
			tms.deleteTransazioneMongo(tm3);
			
			assertFalse(tms.findById(tm1.get_id()).isPresent());
			assertFalse(tms.findById(tm2.get_id()).isPresent());
			assertFalse(tms.findById(tm3.get_id()).isPresent());
		}catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
