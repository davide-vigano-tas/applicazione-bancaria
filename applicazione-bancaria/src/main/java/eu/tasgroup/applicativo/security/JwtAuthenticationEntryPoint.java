package eu.tasgroup.applicativo.security;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws IOException {
        System.err.println("Accesso non autorizzato: token mancante o non valido.");
    	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Accesso non autorizzato: token mancante o non valido.");
    }
}
