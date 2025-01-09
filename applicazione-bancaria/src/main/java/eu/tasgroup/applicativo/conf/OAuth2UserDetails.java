package eu.tasgroup.applicativo.conf;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class OAuth2UserDetails implements OAuth2User, UserDetails {

	
	private static final long serialVersionUID = 4323930552518475974L;
	private OAuth2User user;
	
	

	public OAuth2UserDetails(OAuth2User user) {
		this.user = user;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return user.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getAuthorities();
	}

	@Override
	public String getName() {
		return user.getName();
	}

	@Override
	public String getPassword() {
		return "null";
	}

	@Override
	public String getUsername() {
		return user.getAttribute("email");
	}

}
