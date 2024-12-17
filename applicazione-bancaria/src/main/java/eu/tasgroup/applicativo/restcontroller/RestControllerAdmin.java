package eu.tasgroup.applicativo.restcontroller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.dto.LoginRequest;
import eu.tasgroup.applicativo.dto.LoginResponse;
import eu.tasgroup.applicativo.service.ClientiService;
import eu.tasgroup.applicativo.service.impl.AuthService;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class RestControllerAdmin {
	
	@Autowired
	private ClientiService clientiService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException | javax.naming.AuthenticationException e) {
            return ResponseEntity.status(401).body("Credenziali non valide");
        }
    }

    @GetMapping("/clienti/{id}")
    public ResponseEntity<?> getClienteConToken(
            @PathVariable long id) {
      
            Optional<Cliente> cliente = clientiService.findById(id);
            if(cliente.isEmpty())
            	return ResponseEntity.status(404).body("Cliente non trovato");
            
            return ResponseEntity.ok(cliente.get());
    }   
}
