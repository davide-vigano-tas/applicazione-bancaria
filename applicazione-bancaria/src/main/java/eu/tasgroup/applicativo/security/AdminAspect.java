package eu.tasgroup.applicativo.security;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Aspect
@Component
public class AdminAspect {
	@Before("@annotation(eu.tasgroup.applicativo.security.AdminOnly)")
    public void checkAdminAccess() {
	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
        	System.err.println("Accesso negato: Utente non autenticato.");
            throw new SecurityException("Accesso negato: Utente non autenticato.");
        }

        // Verifica il ruolo dell'utente
        String email = ((UserDetails)authentication.getPrincipal()).getUsername();
        
        System.err.println("Email: " + email);
        
        if (!email.endsWith("@tasgroup.eu")) {
        	System.err.println("Accesso negato: Solo gli amministratori possono accedere a questa funzionalità.");
            throw new SecurityException("Accesso negato: Solo gli amministratori possono accedere a questa funzionalità.");
        }
        
        System.err.println("Autorizzato");
    }
}
