package eu.tasgroup.applicativo;

import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;

//TODO: Da rimuovere 
@Component
public class SecurityEventListener {

    @EventListener
    public void handleBadCredentials(AuthenticationFailureBadCredentialsEvent event) {
    	System.err.println(event);
        System.err.println("Login fallito per utente: " + event.getException());
        System.err.println("Login fallito per utente: " + event.getAuthentication());
        System.err.println("Login fallito per utente: " + event.getSource());
    }
}
