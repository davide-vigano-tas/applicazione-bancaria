package eu.tasgroup.applicativo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoMetodo;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Pagamento;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PagamentoRepositoryTest {
	@Autowired
	PagamentoRepository pr;
	
	@Autowired
	ClientiRepository clientiRepository;
	
	static Cliente cliente;
	static Pagamento pagamento1;
	static Pagamento pagamento2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		cliente = new Cliente();

		cliente.setAccountBloccato(false);
		cliente.setNomeCliente("Samuel");
		cliente.setCognomeCliente("Mastrelli");
		cliente.setEmailCliente("sam@prova.email");
		cliente.setPasswordCliente("pass01$");
		cliente.setSaldoConto(300);
		
		
		pagamento1 = new Pagamento();
		
		pagamento1.setDataPagamento(new Date());
		pagamento1.setImporto(300);
		pagamento1.setMetodoPagamento(TipoMetodo.BONIFICO);
		
		pagamento2 = new Pagamento();
		
		pagamento2.setDataPagamento(new Date());
		pagamento2.setImporto(300);
		pagamento2.setMetodoPagamento(TipoMetodo.CARTA_CREDITO);
		
		
		
		
	}

	@Test
	@Order(1)
	void testSumPagamentiByCliente() {
		try {
			Cliente clienteCreato = clientiRepository.save(cliente);
			pagamento1.setCliente(clienteCreato);
			pagamento2.setCliente(clienteCreato);
			
			pr.save(pagamento1);
			pr.save(pagamento2);
			
			double sum = pr.sumPagamentiByCliente(clienteCreato.getCodCliente());
			assertEquals(600.0, sum);
			
		}catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	@Order(2)
	void deleteTest() {
		try {

			pr.delete(pagamento1);
			pr.delete(pagamento2);
			
			clientiRepository.delete(cliente);
			
	
		}catch (Exception e) {
			fail(e.getMessage());
		}
	}
	

}
