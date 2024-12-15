package eu.tasgroup.applicativo.conf;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.repository.AmministratoriRepository;
import eu.tasgroup.applicativo.repository.ClientiRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private final AmministratoriRepository ar;
	private final ClientiRepository cr;

	public CustomAuthenticationFailureHandler(AmministratoriRepository ar, ClientiRepository cr) {
		this.ar = ar;
		this.cr = cr;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if (email.endsWith("@tasgroup.eu")) {
			ar.findByEmailAdmin(email).ifPresent(admin -> aggiornaTentativiLoginAdmin(admin));
			response.sendRedirect("/admin/admin-login?error=true&message=" 
			+ determinaMessaggioErroreAdmin(email,password) + "&tentativi=" + tentativiRimastiAdmin(email));
		} else {
			cr.findByEmailCliente(email).ifPresent(cliente -> aggiornaTentativiLogin(cliente));
			response.sendRedirect("/user/user-login?error=true&message=" 
			+ determinaMessaggioErrore(email, password) + "&tentativi=" + tentativiRimasti(email));
		}
	}

	private String tentativiRimastiAdmin(String email) {
		Optional<Amministratore> adminOpt = ar.findByEmailAdmin(email);
		if(adminOpt.isPresent()) {
			Amministratore admin = adminOpt.get();
			return String.valueOf(admin.isAccountBloccato() ? 0 : 5 - admin.getTentativiErrati());
		}
			
		return "5";
	}
	
	private String tentativiRimasti(String email) {
		Optional<Cliente> clienteOpt = cr.findByEmailCliente(email);
		if(clienteOpt.isPresent()) {
			Cliente cliente = clienteOpt.get();
			return String.valueOf(cliente.isAccountBloccato() ? 0 : 5 - cliente.getTentativiErrati());
		}
			
		return "5";
	}

	private void aggiornaTentativiLoginAdmin(Amministratore admin) {
		int nuoviTentativi = admin.getTentativiErrati() + 1;
		admin.setTentativiErrati(nuoviTentativi);

		if (nuoviTentativi >= 5) {
			admin.setAccountBloccato(true);
		}

		ar.save(admin);
	}

	private void aggiornaTentativiLogin(Cliente cliente) {
		int nuoviTentativi = cliente.getTentativiErrati() + 1;
		cliente.setTentativiErrati(nuoviTentativi);

		if (nuoviTentativi >= 5) {
			cliente.setAccountBloccato(true);
		}

		cr.save(cliente);
	}
	
	private String determinaMessaggioErroreAdmin(String email, String password ) {
		Optional<Amministratore> adminOpt = ar.findByEmailAdmin(email);

		if (adminOpt.isEmpty()) {
			return "email_non_esistente";
		}

		Amministratore admin = adminOpt.get();
		if (admin.isAccountBloccato()) {
			return "account_bloccato";
		}
		
		if (!BCryptEncoder.passwordMatch(password, admin.getPasswordAdmin())) {
			return "password_errata";
		}


		return null;
	}

	private String determinaMessaggioErrore(String email, String password) {
		Optional<Cliente> clienteOpt = cr.findByEmailCliente(email);

		if (clienteOpt.isEmpty()) {
			return "email_non_esistente";
		}

		Cliente cliente = clienteOpt.get();
		if (cliente.isAccountBloccato()) {
			return "account_bloccato";
		}

		if (!BCryptEncoder.passwordMatch(password, cliente.getPasswordCliente())) {
			return "password_errata";
		}

		return null;
	}


}
