package eu.tasgroup.applicativo.conf;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import eu.tasgroup.applicativo.repository.AmministratoriRepository;
import eu.tasgroup.applicativo.repository.ClientiRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final AmministratoriRepository ar;
	private final ClientiRepository cr;

	public CustomAuthenticationSuccessHandler(AmministratoriRepository ar, ClientiRepository cr) {
		this.ar = ar;
		this.cr = cr;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String email = request.getParameter("email");
		System.err.println("Success");
		if (email.endsWith("@tasgroup.eu")) {
			ar.findByEmailAdmin(email).ifPresent(admin -> {
				if (admin.getTentativiErrati() > 0) {
					admin.setTentativiErrati(0);
					ar.save(admin);
				}
			});
			
			response.sendRedirect("/admin/");
		}else {
			cr.findByEmailCliente(email).ifPresent(cliente -> {
				if (cliente.getTentativiErrati() > 0) {
					cliente.setTentativiErrati(0);
					cr.save(cliente);
				}
			});
			
			response.sendRedirect("/user/");
		}
		
		
	}

	
}
