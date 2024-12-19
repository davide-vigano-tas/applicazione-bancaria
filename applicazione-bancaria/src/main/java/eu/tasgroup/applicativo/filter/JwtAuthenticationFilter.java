package eu.tasgroup.applicativo.filter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import eu.tasgroup.applicativo.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	private Logger logger = Logger.getLogger("logger");

	public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		try {
			// Controlla che l'header Authorization sia presente e inizi con "Bearer "
			// JWT segua il formato standard, ovvero inizia sempre con la stringa "Bearer "
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				logger.log(Level.WARNING,"Header nullo oppure non inizia con Bearer");
				return;
			}

			// Estrai il token dall'header
			logger.log(Level.WARNING,"HEADER:\n" + authHeader);
			
			String jwtToken = authHeader.substring(7);
			logger.log(Level.WARNING,"TOKEN:\n" + jwtToken);
			
			// MI DA PROBLEMI AD ESTRARRE LO USERNAME
			String username = jwtService.extractUsername(jwtToken);
			logger.log(Level.WARNING,"USERNAME: " + username);
			
			// Valida il token ed esegui l'autenticazione
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				if (jwtService.isTokenValid(jwtToken, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"Errore durante l'autenticazione JWT: " + e.getMessage());
		}

		filterChain.doFilter(request, response);
	}

}
