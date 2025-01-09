package eu.tasgroup.applicativo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Conto;

@SpringBootTest
class TransazioneRepositoryTest {

	@Autowired
	private TransazioneRepository tr;
	@Autowired
	private ContiRepository cr;

	@Test
	void testDate() {
		Date ieri= new Date(System.currentTimeMillis() - 86400000);
		Date oggi= new Date();
		Optional<Conto> conto = cr.findById((long) 557);
		//ho inserito manualmente due transazioni tra ieri e oggi
		assertEquals(2, tr.findByContoAndDataTransazioneBetween(conto.get(), ieri,oggi).size() );
	}

}
