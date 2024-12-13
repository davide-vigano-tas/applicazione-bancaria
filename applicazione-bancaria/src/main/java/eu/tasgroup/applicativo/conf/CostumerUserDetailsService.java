package eu.tasgroup.applicativo.conf;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.repository.AmministratoriRepository;
import eu.tasgroup.applicativo.repository.ClientiRepository;

@Configuration
public class CostumerUserDetailsService implements UserDetailsService {
	
	private final AmministratoriRepository ar;
	private final ClientiRepository cr;

	public CostumerUserDetailsService(AmministratoriRepository ar, ClientiRepository cr) {
		this.ar = ar;
		this.cr = cr;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.err.println("Sono dentro");
		System.err.println("Email: " + email);
		if (email.endsWith("@tasgroup.eu")) {
			System.err.println("Sono entrato in admin");
			
			try {
				Optional<Amministratore> adminOptional = ar.findByEmailAdmin(email);

				if (adminOptional.isPresent()) {
					
					Amministratore admin = adminOptional.get();
					
					System.err.println("Admin: \n" + admin);
					return User.withUsername(admin.getEmailAdmin())
							.accountLocked(admin.isAccountBloccato())
							.password(admin.getPasswordAdmin()).roles("ADMIN")
							.build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			throw new UsernameNotFoundException(email);

			
		}

		try {
			Optional<Cliente> clienteOptional = cr.findByEmailCliente(email);
			
			System.err.println(clienteOptional);
			if (clienteOptional.isPresent()) {
				
				Cliente cliente = clienteOptional.get();
				
				System.err.println("Cliente: \n" + cliente);
				return User.withUsername(cliente.getEmailCliente())
						.accountLocked(cliente.isAccountBloccato())
						.password(cliente.getPasswordCliente()).roles("USER")
						.build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new UsernameNotFoundException(email);
	}

}
