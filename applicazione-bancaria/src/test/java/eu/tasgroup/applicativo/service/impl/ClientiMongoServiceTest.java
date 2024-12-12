package eu.tasgroup.applicativo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.tasgroup.applicativo.businesscomponent.model.mongo.ClienteMongo;
import eu.tasgroup.applicativo.service.ClientiMongoService;

@SpringBootTest
class ClientiMongoServiceTest{
	
	
	
	static ClienteMongo cliente;
	static ClienteMongo cliente2;
	
	@BeforeAll
	static void setUpBeforeClass(@Autowired ClientiMongoService clientiMongoService) {
		cliente = new ClienteMongo();
		cliente.setNomeCliente("Samuel");
		cliente.setCognomeCliente("Mastrelli");
		cliente.setEmailCliente("sam@prova.email");
		cliente.setPasswordCliente("pass01$");
		cliente.setSaldoConto(300);
		
		cliente2 = new ClienteMongo();
		cliente2.setNomeCliente("Samuel");
		cliente2.setCognomeCliente("Mastrelli");
		cliente2.setEmailCliente("sam2@prova.email");
		cliente2.setPasswordCliente("pass01$");
		cliente2.setSaldoConto(134);
		
	}

	@Test
	@Order(1)
	void testSommaConti(@Autowired ClientiMongoService clientiMongoService) {
		ClienteMongo result = clientiMongoService.createOrUpdate(cliente);
		clientiMongoService.createOrUpdate(cliente2);
		double somma = clientiMongoService.sommaSaldiClienti();

        assertNotNull(result);
        assertEquals((300+134), somma);
        
        cliente2.setSaldoConto(300);
        clientiMongoService.createOrUpdate(cliente2);
		
        List<ClienteMongo> lista=clientiMongoService.getClientiList();
        assertEquals(2, lista.size());
        
        somma = clientiMongoService.sommaSaldiClienti();
        
        assertEquals((300+300), somma);
	}
	
	@AfterAll
	static void tearDownAfterClass(@Autowired ClientiMongoService clientiMongoService) {
			
		
		clientiMongoService.deleteClienteMongo(cliente);
		clientiMongoService.deleteClienteMongo(cliente2);	
	}
	
	

}
