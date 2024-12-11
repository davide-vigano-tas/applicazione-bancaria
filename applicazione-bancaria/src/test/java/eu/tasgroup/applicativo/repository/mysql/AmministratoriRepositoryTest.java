package eu.tasgroup.applicativo.repository.mysql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;
import eu.tasgroup.applicativo.repository.AmministratoriRepository;

@SpringBootTest
class AmministratoriRepositoryTest {

    @Autowired 
    private AmministratoriRepository ar;

    @Test
    void testFindByEmailAdmin() {
        Amministratore admin = new Amministratore();
        
        admin.setNomeAdmin("Prova");
        admin.setCognomeAdmin("Prova");
        admin.setEmailAdmin("mail@mail.it");
        admin.setPasswordAdmin("pass");
        
        ar.save(admin);
        
        Optional<Amministratore> adminSalvato = ar.findByEmailAdmin(admin.getEmailAdmin());
        
        assertTrue(adminSalvato.isPresent());
        assertEquals("mail@mail.it", adminSalvato.get().getEmailAdmin());
        
        ar.deleteById(adminSalvato.get().getCodAdmin());
    }
}
