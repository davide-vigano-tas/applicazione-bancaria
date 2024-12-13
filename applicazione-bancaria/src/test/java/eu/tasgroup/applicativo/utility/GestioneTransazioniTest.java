package eu.tasgroup.applicativo.utility;

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
import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoMetodo;
import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoTransazione;
import eu.tasgroup.applicativo.businesscomponent.model.mongo.OperazioniBancarieMongo;
import eu.tasgroup.applicativo.businesscomponent.model.mongo.TransazioniMongo;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Conto;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.MovimentoConto;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Pagamento;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Transazione;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.TransazioneBancaria;
import eu.tasgroup.applicativo.service.ClientiService;
import eu.tasgroup.applicativo.service.ContiService;
import eu.tasgroup.applicativo.service.MovimentoContoService;
import eu.tasgroup.applicativo.service.OperazioniBancarieMongoService;
import eu.tasgroup.applicativo.service.PagamentoService;
import eu.tasgroup.applicativo.service.TransazioneBancariaService;
import eu.tasgroup.applicativo.service.TransazioneService;
import eu.tasgroup.applicativo.service.TransazioniMongoService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GestioneTransazioniTest {
	
	@Autowired
	ClientiService clientiService;
	@Autowired
	ContiService contiService;
	
	@Autowired
	TransazioneService transazioneService;
	
	@Autowired
	MovimentoContoService movimentoContoService;
	
	@Autowired
	TransazioniMongoService transazioniMongoService;
	
	@Autowired
	TransazioneBancariaService transazioneBancariaService;
	
	@Autowired
	OperazioniBancarieMongoService operazioniBancarieMongoService;
	
	@Autowired
	PagamentoService pagamentoService;
	
	@Autowired
	GestioneTransazioni gestioneTransazioni;
	
	private static Cliente cliente; 
	private static Cliente cliente2;
	private static Conto contoOrigin;
	private static Conto contoDest;
	private static TransazioneBancaria transazioneBancaria;
	private static Transazione t1;
	private static Transazione t2;
	

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		cliente = new Cliente();
		
		cliente.setNomeCliente("Paolo");
		cliente.setCognomeCliente("Rossi");
		cliente.setEmailCliente("mail@mail.it");
		cliente.setPasswordCliente("pass");
		cliente.setSaldoConto(1000);
		
		cliente2 = new Cliente();
		
		cliente2.setNomeCliente("Mario");
		cliente2.setCognomeCliente("Verdi");
		cliente2.setEmailCliente("mail2@mail.it");
		cliente2.setPasswordCliente("pass");
		cliente2.setSaldoConto(2000);
		
		contoOrigin = new Conto();
		contoOrigin.setSaldo(1000);
		contoOrigin.setTipoConto(TipoConto.CORRENTE);
		contoOrigin.setCliente(cliente);
		
		contoDest = new Conto();
		contoDest.setSaldo(2000);
		contoDest.setTipoConto(TipoConto.RISPARMIO);
		contoDest.setCliente(cliente2);
		
		t1 = new Transazione();
		t1.setConto(contoOrigin);
		t1.setDataTransazione(new Date());
		t1.setImporto(300);
		
		t2 = new Transazione();
		t2.setConto(contoOrigin);
		t2.setDataTransazione(new GregorianCalendar(2024, 12, 3, 0, 30, 2).getTime());
		t2.setImporto(450);
		
		transazioneBancaria = new TransazioneBancaria();
		transazioneBancaria.setImporto(100);
		transazioneBancaria.setContoDestinazione(contoDest);
		transazioneBancaria.setContoOrigine(contoOrigin);
		
		
	}

	@Test
	@Order(1)
	void testPrelievo() {
		try {
			
			cliente = clientiService.createOrUpdate(cliente);
			contoOrigin = contiService.createOrUpdate(contoOrigin);
			t1.setConto(contoOrigin);
			
			if(!gestioneTransazioni.prelievo(t1, cliente)) fail("PrelievoFallito");
			
			
			contoOrigin = contiService.findById(contoOrigin.getCodConto()).get();
			
			assertEquals(700.0, contoOrigin.getSaldo());
			
			cliente = clientiService.findById(cliente.getCodCliente()).get();
			
			assertEquals(700.0, cliente.getSaldoConto());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(2)
	void testDeposito() {
		try {
			
			t2.setConto(contoOrigin);
			
			if(!gestioneTransazioni.deposito(t2, cliente)) fail("DepositoFallito");
			
			
			contoOrigin = contiService.findById(contoOrigin.getCodConto()).get();
			
			assertEquals(1150.0, contoOrigin.getSaldo());
			
			cliente = clientiService.findById(cliente.getCodCliente()).get();
			
			assertEquals(1150.0, cliente.getSaldoConto());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(3)
	void testTrasferimento() {
		try {
			
			cliente2 = clientiService.createOrUpdate(cliente2);
			contoDest = contiService.createOrUpdate(contoDest);
			transazioneBancaria.setContoDestinazione(contoDest);
			transazioneBancaria.setContoOrigine(contoOrigin);
			
			Pagamento p = new Pagamento();
			p.setMetodoPagamento(TipoMetodo.BONIFICO);
			if(!gestioneTransazioni.transazioneBancaria(transazioneBancaria, p)) fail("TrasferimentoFallito");
			
			
			contoOrigin = contiService.findById(contoOrigin.getCodConto()).get();
			contoDest = contiService.findById(contoDest.getCodConto()).get();
			
			System.err.println(contoOrigin);
			System.err.println(contoDest);
			
			assertEquals(1050.0, contoOrigin.getSaldo());
			assertEquals(2100.0, contoDest.getSaldo());
			
			cliente = clientiService.findById(cliente.getCodCliente()).get();
			cliente2 = clientiService.findById(cliente2.getCodCliente()).get();

			System.err.println(cliente);
			System.err.println(cliente2);
			assertEquals(1050.0, cliente.getSaldoConto());
			assertEquals(2100.0, cliente2.getSaldoConto());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(4)
	void delete() {
		try {
			for(MovimentoConto m : movimentoContoService.findMovimentiContoByConto(contoOrigin)) {
				movimentoContoService.deleteMovimentoContoById(m.getCodMovimento());
			}
			for(MovimentoConto m : movimentoContoService.findMovimentiContoByConto(contoDest)) {
				movimentoContoService.deleteMovimentoContoById(m.getCodMovimento());
			}
			for(Transazione t : transazioneService.getAll()) {
				if(t.getConto().getCodConto() == contoOrigin.getCodConto() ||
						t.getConto().getCodConto() == contoDest.getCodConto()) {
					transazioneService.deleteTransazioneById(t.getCodTransazione());
					for(TransazioniMongo tm : transazioniMongoService.findAll()) {
						if(tm.getCodTransazione()==t.getCodTransazione()) 
							transazioniMongoService.deleteTransazioneMongo(tm);
					}
				}

				
			}
			for(TransazioneBancaria tbank : transazioneBancariaService.getAll()) {
				if(tbank.getContoOrigine().getCodConto() == contoOrigin.getCodConto() ||
						tbank.getContoOrigine().getCodConto() == contoDest.getCodConto()) {
					transazioneBancariaService.deleteTransazioneBancariaById(tbank.getCodTransazioneBancaria());
					for(OperazioniBancarieMongo opm : operazioniBancarieMongoService.findAll()) {
						if(opm.getCodOperazione() == tbank.getCodTransazioneBancaria()) 
							operazioniBancarieMongoService.delete(opm);
					}
				}
				
			}
			
			
			contiService.deleteContoById(contoOrigin.getCodConto());
			contiService.deleteContoById(contoDest.getCodConto());
			clientiService.deleteCliente(clientiService.findById(cliente.getCodCliente()).get());
			clientiService.deleteCliente(clientiService.findById(cliente2.getCodCliente()).get());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
