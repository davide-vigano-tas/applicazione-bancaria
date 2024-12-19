package eu.tasgroup.applicativo;

import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.event.EventListener;


@Component
public class SecurityEventListener {
	
	private Logger logger = Logger.getLogger("logger");

	//Log in caso di credenziali errate
    @EventListener
    public void handleBadCredentials(AuthenticationFailureBadCredentialsEvent event) {
    	logger.log(Level.WARNING, event.toString());
    	logger.log(Level.WARNING, "Login fallito per utente: " + event.getException());
    	logger.log(Level.WARNING, "Login fallito per utente: " + event.getAuthentication());
    	logger.log(Level.WARNING, "Login fallito per utente: " + event.getSource());
    }
}
