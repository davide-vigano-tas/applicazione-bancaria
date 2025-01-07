package eu.tasgroup.applicativo.security;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE) 
public class AdminAspect {
	
	private Logger logger = Logger.getLogger("logger");
    /**
     * Aspetto controllo admin
     * 
     */
	@Before("@annotation(eu.tasgroup.applicativo.security.AdminOnly)")
    public void checkAdminAccess() {
	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("Accesso negato: Utente non autenticato.");
        }

        // Verifica il ruolo dell'utente
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = a.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        
        if (!isAdmin) {
            throw new SecurityException("Accesso negato: Solo gli amministratori possono accedere a questa funzionalità.");
        }
        
        logger.log(Level.WARNING,"Autorizzato");
    }
	
    /**
     * Aspetto controllo per admin Creator (Crea utenti)
     *
     */
	@Before("@annotation(eu.tasgroup.applicativo.security.CreatorOnly)")
	public void checkCreator() {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        if (authentication == null || !authentication.isAuthenticated()) {
	            throw new SecurityException("Accesso negato: Utente non autenticato.");
	        }
	        
	        Authentication a = SecurityContextHolder.getContext().getAuthentication();
	        boolean isCreator = a.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_SUPER_ADMIN") ||
	        		auth.getAuthority().equals("ROLE_CREATOR"));
	        if (!isCreator) {
	            throw new SecurityException("Accesso negato: Solo gli amministratori  creator possono accedere a questa funzionalità.");
	        }
	        
	        logger.log(Level.WARNING,"Autorizzato creator");
	}
	
    /**
     * Aspetto controllo per admin Approver(Approva e rifiuta prestiti)
     *
     */
	@Before("@annotation(eu.tasgroup.applicativo.security.ApproverOnly)")
	public void checkAprrover() {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        if (authentication == null || !authentication.isAuthenticated()) {
	            throw new SecurityException("Accesso negato: Utente non autenticato.");
	        }
	        
	        Authentication a = SecurityContextHolder.getContext().getAuthentication();
	        boolean isCreator = a.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_SUPER_ADMIN") ||
	        		auth.getAuthority().equals("ROLE_APPROVER"));
	        if (!isCreator) {
	            throw new SecurityException("Accesso negato: Solo gli amministratori approver possono accedere a questa funzionalità.");
	        }
	        
	        logger.log(Level.WARNING,"Autorizzato approver");
	}
}
