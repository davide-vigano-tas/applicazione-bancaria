package eu.tasgroup.applicativo.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.PermessiAmministratori;
import eu.tasgroup.applicativo.repository.AmministratoriRepository;
import eu.tasgroup.applicativo.repository.ClientiRepository;
import eu.tasgroup.applicativo.repository.PermessiAmministratoriRepository;

@Configuration
public class CostumerUserDetailsService implements UserDetailsService {
	
	private final AmministratoriRepository ar;
	private final ClientiRepository cr;
	private final PermessiAmministratoriRepository pr;

	public CostumerUserDetailsService(AmministratoriRepository ar, ClientiRepository cr,
			PermessiAmministratoriRepository pr) {
		this.ar = ar;
		this.cr = cr;
		this.pr = pr;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		if (email.endsWith("@tasgroup.eu")) {
			System.err.println("Sono entrato in admin");
			
			try {
				Optional<Amministratore> adminOptional = ar.findByEmailAdmin(email);

				if (adminOptional.isPresent()) {
					
					Amministratore admin = adminOptional.get();
				        
				     List<String> roles = new ArrayList<String>();
				     roles.add("ADMIN");
				     for(PermessiAmministratori prm : pr.getByAdminId(admin.getCodAdmin())) {
				    	 roles.add(prm.getRuolo().name());
				     }
				  
				    String[] rolesArray = new String[roles.size()];
			
				
				    for(int i = 0; i<rolesArray.length; i++) {
				    	rolesArray[i] = roles.get(i);
				    }
					return User.withUsername(admin.getEmailAdmin())
							.accountLocked(admin.isAccountBloccato())
							.password(admin.getPasswordAdmin()).roles(rolesArray)
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
