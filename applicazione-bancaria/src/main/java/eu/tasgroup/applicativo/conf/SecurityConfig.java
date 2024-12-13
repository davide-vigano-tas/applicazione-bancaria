package eu.tasgroup.applicativo.conf;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import eu.tasgroup.applicativo.repository.ClientiRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final ClientiRepository cr;

    public SecurityConfig(ClientiRepository cr) {
		this.cr = cr;
	}                    

	@Bean
	SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests(authorize -> authorize
	        	.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
	        	.requestMatchers("/user/user-registrazione").permitAll()
	        	.requestMatchers("/user/user-form-registrazione").permitAll()
	        	.requestMatchers("/user/**").hasRole("USER")
	        )
	        .formLogin(form -> form
	            .loginPage("/user/user-login")
	            .defaultSuccessUrl("/user/", true)
	            .usernameParameter("email")
	            .permitAll()
	        )
	        .logout(logout -> logout
	        		.logoutUrl("/user/logout")
	                .logoutSuccessUrl("/")
	                .permitAll()
	        );
	    return http.build();
	}

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(new CostumerUserDetailsService2(cr));
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
