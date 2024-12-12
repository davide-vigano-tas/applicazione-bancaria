package eu.tasgroup.applicativo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoConto;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Conto;
import eu.tasgroup.applicativo.repository.ClientiRepository;
import eu.tasgroup.applicativo.repository.ContiRepository;

@SpringBootTest
class ContiRepositoryTest {

    @Autowired
    private ContiRepository contiRepository;
    
    @Autowired
    private ClientiRepository clientiRepository;

    @Test
    void testConto() {
        Cliente cliente = new Cliente();
        cliente.setAccountBloccato(false);
        cliente.setNomeCliente("Samuel");
        cliente.setCognomeCliente("Mastrelli");
        cliente.setEmailCliente("sam@prova.email");
        cliente.setPasswordCliente("pass01$");
        cliente.setSaldoConto(300);

        Cliente clienteSalvato = clientiRepository.save(cliente);

        Conto conto = new Conto();
        conto.setTipoConto(TipoConto.CORRENTE);
        conto.setSaldo(1200);
        conto.setCliente(clienteSalvato);
        
        Conto conto2 = new Conto();
        conto2.setTipoConto(TipoConto.RISPARMIO);
        conto2.setSaldo(800);
        conto2.setCliente(clienteSalvato);
        
        Conto contoZero = new Conto();
        contoZero.setTipoConto(TipoConto.CORRENTE);
        contoZero.setSaldo(0);
        contoZero.setCliente(clienteSalvato);
        
        Conto contoZero2 = new Conto();
        contoZero2.setTipoConto(TipoConto.RISPARMIO);
        contoZero2.setSaldo(0);
        contoZero2.setCliente(clienteSalvato);

        contiRepository.save(conto);
        contiRepository.save(conto2);
        contiRepository.save(contoZero);
        contiRepository.save(contoZero2);

        // Test saldo medio
        double saldoMedio = contiRepository.saldoMedio();
        double saldoCalcolato = (1200+800)/4;
        assertEquals(saldoCalcolato, saldoMedio);
        
        //test saldo zero
        List<Conto> listaZero = contiRepository.getContiSaldoZero();
        
        assertEquals(listaZero.size(), 2);
        
        for(Conto c : listaZero) {
        	assertEquals(c.getSaldo(), 0);
        }

        contiRepository.delete(conto);
        contiRepository.delete(conto2);
        contiRepository.delete(contoZero);
        contiRepository.delete(contoZero2);
        clientiRepository.delete(clienteSalvato);
    }
}
