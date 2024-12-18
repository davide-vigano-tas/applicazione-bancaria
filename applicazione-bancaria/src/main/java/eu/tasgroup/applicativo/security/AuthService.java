package eu.tasgroup.applicativo.security;

import javax.naming.AuthenticationException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.dto.LoginRequest;
import eu.tasgroup.applicativo.dto.LoginResponse;

@Service
public class AuthService {
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final JwtService jwtService;

	public AuthService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
			JwtService jwtService) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtService = jwtService;
	}

	public LoginResponse login(LoginRequest request) throws AuthenticationException {
		// Autentica l'utente 
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		
		// Carica dettagli utente
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		boolean isAdmin = userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
		
		if(!isAdmin)
			throw new AuthorizationDeniedException("Il ruolo del tuo utente non ha i permessi adeguati");
		// Genera Token
		String token = jwtService.generateToken(userDetails);
		
		// Genera risposta
		LoginResponse response = new LoginResponse();
		response.setToken(token);
		return response;
	}
}