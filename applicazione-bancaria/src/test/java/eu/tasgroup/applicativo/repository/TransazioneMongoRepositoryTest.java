package eu.tasgroup.applicativo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoTransazione;
import eu.tasgroup.applicativo.businesscomponent.model.mongo.TransazioniMongo;

@SpringBootTest
class TransazioneMongoRepositoryTest {

	@Autowired
	private TransazioneMongoRepository tmr;

	@BeforeAll
	static void setUpBeforeClass(@Autowired TransazioneMongoRepository tmr) throws Exception {
		TransazioniMongo tm1 = new TransazioniMongo();

		tm1.setCodTransazione(1);
		tm1.setImporto(1000);
		tm1.setDataTransazione(new Date());
		tm1.setTipoTransazione(TipoTransazione.ACCREDITO);
		tm1.setCliente(1);

		tmr.save(tm1);

		TransazioniMongo tm2 = new TransazioniMongo();

		tm2.setCodTransazione(2);
		tm2.setImporto(3000);
		tm2.setDataTransazione(new Date());
		tm2.setTipoTransazione(TipoTransazione.ACCREDITO);
		tm2.setCliente(2);

		tmr.save(tm2);
		
		TransazioniMongo tm3 = new TransazioniMongo();

		tm3.setCodTransazione(3);
		tm3.setImporto(200);
		tm3.setDataTransazione(new Date());
		tm3.setTipoTransazione(TipoTransazione.ADDEBITO);
		tm3.setCliente(1);

		tmr.save(tm3);
	}

	@AfterAll
	static void tearDownAfterClass(@Autowired TransazioneMongoRepository tmr) throws Exception {
		List<TransazioniMongo> lista = tmr.findAll();
		
		for (TransazioniMongo transazioniMongo : lista) {
			tmr.delete(transazioniMongo);
		}
	}

	@Test
	void testCountByTipoTransazione() {
		assertEquals(2, tmr.countByTipoTransazione(TipoTransazione.ACCREDITO));
	}

	@Test
	void testCalcolaMediaTransazioniPerCliente() {
		assertEquals(600, tmr.calcolaMediaTransazioniPerCliente(1));
	}

	@Test
	void testNumeroMedioTransazioniPerCliente() {
		assertEquals(1.5, tmr.numeroMedioTransazioniPerCliente());
	}

	@Test
	void testTotaleImportoPerMese() {
		assertEquals(4200, tmr.totaleImportoPerMese(12));
	}

	@Test
	void testFindTopByOrderByDataTransazioneDesc() {
		assertNotNull(tmr.findTopByOrderByDataTransazioneDesc());
	}

	@Test
	void testFindByCliente() {
		assertEquals(2, tmr.findByCliente(1).size());
	}

}
