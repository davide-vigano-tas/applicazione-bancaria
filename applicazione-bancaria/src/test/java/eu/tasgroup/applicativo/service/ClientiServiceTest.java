package eu.tasgroup.applicativo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.intThat;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoConto;
import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoMetodo;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Carta;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Conto;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Pagamento;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Prestito;
import eu.tasgroup.applicativo.service.CartaService;
import eu.tasgroup.applicativo.service.ClientiService;
import eu.tasgroup.applicativo.service.ContiService;
import eu.tasgroup.applicativo.service.PagamentoService;
import eu.tasgroup.applicativo.service.PrestitoService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientiServiceTest {
	
	@Autowired
	ClientiService clientiService;
	
	@Autowired
	ContiService contiService;
	
	@Autowired
	CartaService cartaService;
	
	@Autowired
	PrestitoService prestitoService;
	
	@Autowired
	PagamentoService pagamentoService;
	
	static Cliente cliente;
	static Conto conto1;
	static Conto conto2;
	
	static Carta carta1;
	static Carta carta2;
	
	static Prestito prestito;
	
	static Pagamento pagamento;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		cliente = new Cliente();
		cliente.setNomeCliente("Samuel");
		cliente.setCognomeCliente("Mastrelli");
		cliente.setEmailCliente("sam@prova.com");
		cliente.setPasswordCliente("pass01");

		conto1 = new Conto();
		conto1.setSaldo(300);
		conto1.setTipoConto(TipoConto.CORRENTE);
		conto1.setCliente(cliente);
		
		conto2 = new Conto();
		conto2.setSaldo(400);
		conto2.setTipoConto(TipoConto.CORRENTE);
		conto2.setCliente(cliente);
	
		cliente.setSaldoConto(700.0);
		
		carta1 = new Carta();
		carta1.setNumeroCarta("23435647765");
		carta1.setCvv("889");
		carta1.setDataScadenza(new GregorianCalendar(2025, 6, 30, 0, 0, 0).getTime());
		carta1.setCliente(cliente);
		
		carta2 = new Carta();
		carta2.setNumeroCarta("23435y467765");
		carta2.setCvv("899");
		carta2.setDataScadenza(new GregorianCalendar(2025, 6, 30, 0, 0, 0).getTime());
		carta2.setCliente(cliente);
		
		
		prestito = new Prestito();
		prestito.setDurataMesi(4);
		prestito.setImporto(340);
		prestito.setTassoInteresse(0.5);
		prestito.setCliente(cliente);
		
		pagamento = new Pagamento();
		pagamento.setImporto(400);
		pagamento.setDataPagamento(new Date());
		pagamento.setMetodoPagamento(TipoMetodo.BONIFICO);
		pagamento.setCliente(cliente);
	}

	@Test
	@Order(1)
	void testCreate() {
		try {
			
			cliente = clientiService.createOrUpdate(cliente);
	
			
			assertNotNull(cliente);
		}catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(2)
	void testNumeroCarte() {
		try {
			
			
			carta1 = cartaService.createOrUpdate(carta1);
			carta2 = cartaService.createOrUpdate(carta2);
			cliente.getCarte().add(carta1);
			cliente.getCarte().add(carta2);
			int numeroCarte = clientiService.numeroCarte(cliente.getCodCliente());
			assertEquals(2, numeroCarte);
			
		}catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(3)
	void testNumeroConti() {
		try {
			
			
			conto1 = contiService.createOrUpdate(conto1);
			conto2 = contiService.createOrUpdate(conto2);
			cliente.getConti().add(conto1);
			cliente.getConti().add(conto2);
			int numeroConti = clientiService.numeroConti(cliente.getCodCliente());
			assertEquals(2, numeroConti);
			
		}catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(4)
	void testListaPrestiti() {
		try {
			
	
			prestito = prestitoService.createOrUpdate(prestito);
			cliente.getPrestiti().add(prestito);
			List<Prestito> prestiti = clientiService.listaPrestitiClienti(cliente.getCodCliente());
			assertEquals(1, prestiti.size());
		}catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(5)
	void testListaPagamenti() {
		try {
			
	
			pagamento = pagamentoService.createOrUpdate(pagamento);
			cliente.getPagamenti().add(pagamento);
			
			List<Pagamento> pagamenti = clientiService.listaPagamentiClienti(cliente.getCodCliente());
			assertEquals(1, pagamenti.size());
			
		}catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(6)
	void testDelete() {
		try {
		
			System.out.println(cliente);
			
			clientiService.deleteCliente(cliente);
			
		}catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	

}
