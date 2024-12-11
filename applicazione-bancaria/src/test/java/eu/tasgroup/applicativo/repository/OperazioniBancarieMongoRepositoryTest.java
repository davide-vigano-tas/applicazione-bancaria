package eu.tasgroup.applicativo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
class OperazioniBancarieMongoRepositoryTest {
	
	@Autowired
	OperazioniBancarieMongoRepository operazioniBancarieMongoRepository;
	
	static OperazioniBancarieMongo operazioniBancarieMongo;
	static OperazioniBancarieMongo operazioniBancarieMongo2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		operazioniBancarieMongo = new OperazioniBancarieMongo();
		
		operazioniBancarieMongo.setCodContoDestinazione(1);
		operazioniBancarieMongo.setCodContoOrigine(5);
		
		operazioniBancarieMongo.setCodOperazione(1);
		operazioniBancarieMongo.setDataOperazione(new Date());
		operazioniBancarieMongo.setImporto(250);
		
		operazioniBancarieMongo.setTipoOperazione(TipoOperazione.ACCREDITO);
		
		operazioniBancarieMongo2 = new OperazioniBancarieMongo();
		
		operazioniBancarieMongo2.setCodContoDestinazione(1);
		operazioniBancarieMongo2.setCodContoOrigine(5);
		
		operazioniBancarieMongo2.setCodOperazione(2);
		operazioniBancarieMongo2.setDataOperazione(new GregorianCalendar(2024, 12, 10, 0, 0, 0).getTime());
		operazioniBancarieMongo2.setImporto(340);
		
		operazioniBancarieMongo2.setTipoOperazione(TipoOperazione.ADDEBITO);
	}
	

	@Test
	@Order(1)
	void testImportoTotale() {
		try {
			operazioniBancarieMongoRepository.save(operazioniBancarieMongo);
			operazioniBancarieMongoRepository.save(operazioniBancarieMongo2);
			

			
			double totale = operazioniBancarieMongoRepository.importoTotale();
			assertEquals(590, totale);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Order(2)
	void testOperazioniPertipo() {
		try {
		
			
			List<OperazioniBancarieMongo> accrediti = operazioniBancarieMongoRepository
					.operazioniPerTipo("ACCREDITO");
			List<OperazioniBancarieMongo> addebiti = operazioniBancarieMongoRepository
					.operazioniPerTipo("ADDEBITO");
			
			assertEquals(1,  addebiti.size());
			assertEquals(1,  accrediti.size());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(3)
	void testUltimeOperazioni() {
		try {
		
			System.out.println(operazioniBancarieMongoRepository.findAll());
			List<OperazioniBancarieMongo> last = operazioniBancarieMongoRepository.ultimeOperazioni();
			
			assertEquals(1,  last.size());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(4)
	void testImportoMedio() {
		try {
			System.out.println(operazioniBancarieMongoRepository.findAll());
			double media = operazioniBancarieMongoRepository.importoMedioOperazioni();
			assertEquals(295.0, media);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(5)
	void testDelete() {
		try {
			System.out.println(operazioniBancarieMongoRepository.findAll());
			operazioniBancarieMongoRepository.delete(operazioniBancarieMongo);
			operazioniBancarieMongoRepository.delete(operazioniBancarieMongo2);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
