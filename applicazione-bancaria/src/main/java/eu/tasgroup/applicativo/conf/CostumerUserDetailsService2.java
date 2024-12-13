package eu.tasgroup.applicativo.conf;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.repository.ClientiRepository;

@Configuration
public class CostumerUserDetailsService2 implements UserDetailsService {

	private final ClientiRepository cr;

	public CostumerUserDetailsService2(ClientiRepository cr) {
		this.cr = cr;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.err.println("Email: " + email);
		try {
			Optional<Cliente> clienteOptional = cr.findByEmailCliente(email);

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
