package eu.tasgroup.applicativo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoOperazione;
import eu.tasgroup.applicativo.businesscomponent.model.mongo.OperazioniBancarieMongo;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OperazioniBancarieMongoServiceTest {
	
	@Autowired
	OperazioniBancarieMongoService operazioniBancarieMongoService;
	
	static OperazioniBancarieMongo op;
	static OperazioniBancarieMongo op2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		op = new OperazioniBancarieMongo();
		op.setCodContoDestinazione(4);
		op.setCodContoOrigine(5);
		op.setCodOperazione(3);
		op.setDataOperazione(new Date());
		op.setImporto(500);
		op.setTipoOperazione(TipoOperazione.ACCREDITO);
		
		op2 = new OperazioniBancarieMongo();
		op2.setCodContoDestinazione(3);
		op2.setCodContoOrigine(8);
		op2.setCodOperazione(7);
		op2.setDataOperazione(new GregorianCalendar(2024, 11, 2, 0, 0, 0).getTime());
		op2.setImporto(60);
		op2.setTipoOperazione(TipoOperazione.ADDEBITO);
	}

	@Test
	@Order(1)
	void testCreate() {
		try {
			op = operazioniBancarieMongoService.createOrUpdate(op);
			op2 = operazioniBancarieMongoService.createOrUpdate(op2);
			
			System.out.println(operazioniBancarieMongoService.findAll());
			
			assertTrue(operazioniBancarieMongoService.findById(op.get_id()).isPresent());
			assertTrue(operazioniBancarieMongoService.findById(op2.get_id()).isPresent());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	
	@Test
	@Order(2)
	void testImportoTotale() {
		try {
			double importoTotale = operazioniBancarieMongoService.totaleImporto();
			
			assertEquals(560.0, importoTotale);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(3)
	void testImportoMedio() {
		try {
			double importoMedio = operazioniBancarieMongoService.importoMedioOperazione();
			
			assertEquals(280.0, importoMedio);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	
	
	@Test
	@Order(4)
	void testOperazioniPerTipo() {
		try {
			List<OperazioniBancarieMongo> accrediti = operazioniBancarieMongoService.operazioniPerTipo(
					TipoOperazione.ACCREDITO.name());
			List<OperazioniBancarieMongo> addebiti = operazioniBancarieMongoService.operazioniPerTipo(
					TipoOperazione.ADDEBITO.name());
			assertTrue(accrediti.contains(op));
			assertTrue(addebiti.contains(op2));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	
	@Test
	@Order(5)
	void testUltimeOperazioni() {
		try {
			Optional<OperazioniBancarieMongo> last = operazioniBancarieMongoService.ultimeOperazioni();
			
			assertTrue(last.isPresent());
			assertEquals(op, last.get());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(6)
	void testDelete() {
		try {
			operazioniBancarieMongoService.delete(op);
			operazioniBancarieMongoService.delete(op2);
			
			assertFalse(operazioniBancarieMongoService.findById(op.get_id()).isPresent());
			assertFalse(operazioniBancarieMongoService.findById(op2.get_id()).isPresent());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
