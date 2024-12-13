package eu.tasgroup.applicativo.conf;

import java.util.Optional;

import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.repository.AmministratoriRepository;
import eu.tasgroup.applicativo.repository.ClientiRepository;

@Service
public class CostumerUserDetailsService implements UserDetailsService {

	private final AmministratoriRepository ar;
	private final ClientiRepository cr;

	public CostumerUserDetailsService(AmministratoriRepository ar, ClientiRepository cr) {
		this.ar = ar;
		this.cr = cr;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		if (email.endsWith("@tasgroup.eu")) {
			System.err.println("Sono entrato in admin");
			Optional<Amministratore> adminOptional = ar.findByEmailAdmin(email);

			return adminOptional.map((admin) -> {
				if (admin.isAccountBloccato()) {
					throw new LockedException("Account amministratore bloccato: " + email);
				}
				
                if (admin.getTentativiErrati() > 0) {
                    admin.setTentativiErrati(0);
                    ar.save(admin);
                }

				return User.builder()
						.username(admin.getEmailAdmin())
						.password(admin.getPasswordAdmin())
						.roles("ADMIN")
						.build();
				}).orElseThrow(() -> new UsernameNotFoundException("Amministratore non trovato " + email));
		}

		Optional<Cliente> clienteOptional = cr.findByEmailCliente(email);

		return clienteOptional.map((cliente) -> {
			
			if (cliente.isAccountBloccato()) {
				throw new LockedException("Account cliente bloccato: " + email);
			}
			
			if (cliente.getTentativiErrati() > 0) {
                cliente.setTentativiErrati(0);
                cr.save(cliente);
            }
			return User.builder()
					.username(cliente.getEmailCliente())
					.password(cliente.getPasswordCliente())
					.roles("USER").build();
			}).orElseThrow(() -> new UsernameNotFoundException("Cliente non trovato " + email));
	}

}
