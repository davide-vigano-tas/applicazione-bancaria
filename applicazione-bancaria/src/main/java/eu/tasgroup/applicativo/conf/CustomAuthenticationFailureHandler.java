package eu.tasgroup.applicativo.conf;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

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
		String email = request.getParameter("username");

		if (email.endsWith("@tasgroup.eu")) {
			ar.findByEmailAdmin(email).ifPresent(admin -> {
				int nuoviTentativi = admin.getTentativiErrati() + 1;
				admin.setTentativiErrati(nuoviTentativi);
				if (nuoviTentativi >= 5) {
					admin.setAccountBloccato(true);
				}
				ar.save(admin);
			});
		} else {
			cr.findByEmailCliente(email).ifPresent(cliente -> {
				int nuoviTentativi = cliente.getTentativiErrati() + 1;
				cliente.setTentativiErrati(nuoviTentativi);
				if (nuoviTentativi >= 5) {
					cliente.setAccountBloccato(true);
				}
				cr.save(cliente);
			});
		}

		response.sendRedirect("/login?error=true");
	}
}
