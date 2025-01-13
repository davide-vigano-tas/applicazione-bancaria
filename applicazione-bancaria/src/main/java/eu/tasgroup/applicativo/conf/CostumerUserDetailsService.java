package eu.tasgroup.applicativo.conf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sound.midi.SysexMessage;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.PermessiAmministratori;
import eu.tasgroup.applicativo.repository.AmministratoriRepository;
import eu.tasgroup.applicativo.repository.ClientiRepository;
import eu.tasgroup.applicativo.repository.PermessiAmministratoriRepository;

@Configuration
@Service
public class CostumerUserDetailsService extends DefaultOAuth2UserService implements  UserDetailsService {
	
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
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        RestTemplate restTemplate = new RestTemplate(); 
        HttpHeaders headers = new HttpHeaders(); 
        String accessToken = userRequest.getAccessToken().getTokenValue();
        System.err.println(accessToken);
        headers.set("Authorization", "Bearer " + accessToken); 
        // Fetch access token
        HttpEntity<String> request = new HttpEntity<>(headers); 
        
        String email = user.getAttribute("email");
        if (email == null) { 
            ResponseEntity<List> emailsResponse = restTemplate.exchange( 
                    "https://api.github.com/user/emails", 
                    HttpMethod.GET, 
                    request, 
                    List.class); 
 
            List<Map<String, Object>> emails = emailsResponse.getBody(); 
            if (emails != null && !emails.isEmpty()) { 
                // Recupera la prima email principale 
                for (Map<String, Object> emailEntry : emails) { 
                    if ((boolean) emailEntry.get("primary")) { 
                    	System.err.println(email);
                        email = (String) emailEntry.get("email"); 
                        break; 
                    } 
                } 
            } 
        } 
       
        System.out.println("QUIIII");
        System.err.println("User: "+ user);
        System.err.println("Email :"+email);
        Map<String, Object> attributes = new HashMap<String, Object>(user.getAttributes());
        attributes.put("email", email);
        user = new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), attributes, "login");
        Optional<Cliente> cliente = cr.findByEmailCliente(email);
        if(!cliente.isPresent()) {
        	Cliente c = new Cliente();
        	c.setAccountBloccato(false);
        	c.setEmailCliente(email);
        	c.setNomeCliente(user.getAttribute("login"));
        	c.setCognomeCliente(user.getAttribute("login"));
        	c.setTentativiErrati(0);
        	c.setSaldoConto(0);
        	c.setPasswordCliente(BCryptEncoder.encode(accessToken));
        	cr.save(c);
        }
       
        OAuth2UserDetails us = new OAuth2UserDetails(user);
        System.err.println(us.getUsername());
        return us;
	}

}
