package eu.tasgroup.applicativo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.tasgroup.applicativo.businesscomponent.model.mongo.ClienteMongo;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientiMongoRepositoryTest {

	@Autowired
	private ClientiMongoRepository cmr;

	@BeforeAll
	static void setUpBeforeClass(@Autowired ClientiMongoRepository cmr) {
		ClienteMongo c1 = new ClienteMongo();
		c1.setCodCliente(1);
		c1.setNomeCliente("Mario");
		c1.setCognomeCliente("Rossi");
		c1.setEmailCliente("mario.rossi@mail.it");
		c1.setPasswordCliente("pass");
		c1.setSaldoConto(100);
		cmr.save(c1);

		ClienteMongo c2 = new ClienteMongo();
		c2.setCodCliente(2);
		c2.setNomeCliente("Lucia");
		c2.setCognomeCliente("Verdi");
		c2.setEmailCliente("lucia.verdi@mail.it");
		c2.setPasswordCliente("pass");
		c2.setSaldoConto(1000);
		cmr.save(c2);

		ClienteMongo c3 = new ClienteMongo();
		c3.setCodCliente(3);
		c3.setNomeCliente("Luca");
		c3.setCognomeCliente("Gialli");
		c3.setEmailCliente("luca.gialli@mail.it");
		c3.setPasswordCliente("pass");
		c3.setSaldoConto(10000);
		cmr.save(c3);
	}

	@Test
	@Order(1)
	void testSaldoMaggioreDi() {
		assertEquals(2, cmr.saldoMaggioreDi(500).size());
	}

	@Test
	@Order(2)
	void testSaldoMinoreDi() {
		assertEquals(1, cmr.saldoMinoreDi(500).size());
	}

	@Test
	@Order(3)
	void testCountSaldoClienteByIntervallo() {
		assertEquals(2, cmr.countSaldoClienteByIntervallo(5, 5000));
	}

	@AfterAll
	static void tearDownAfterClass(@Autowired ClientiMongoRepository cmr) {
		List<ClienteMongo> lista = cmr.findAll();
		for (ClienteMongo clienteMongo : lista) {
			cmr.delete(clienteMongo);
		}
	}
}