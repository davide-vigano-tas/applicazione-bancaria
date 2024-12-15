package eu.tasgroup.applicativo.conf;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import eu.tasgroup.applicativo.repository.AmministratoriRepository;
import eu.tasgroup.applicativo.repository.ClientiRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final ClientiRepository cr;
	private final AmministratoriRepository ar;

	public SecurityConfig(ClientiRepository cr, AmministratoriRepository ar) {
		super();
		this.cr = cr;
		this.ar = ar;
	}
	
	@Bean
	@Order(1)
    SecurityFilterChain adminFilterChain(HttpSecurity http, CustomAuthenticationFailureHandler failureHandler,
    		CustomAuthenticationSuccessHandler successHandler) throws Exception {
        http
	        .securityMatcher("/admin/**")
	        .authorizeHttpRequests((authorize) -> authorize
	        				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
	        				.requestMatchers("/admin/admin-registrazione").permitAll()
	        	        	.requestMatchers("/admin/admin-form-registrazione").permitAll()
	        	        	.requestMatchers("/admin/**").hasRole("ADMIN")
	        )
	        .formLogin((form) -> form
	                        .loginPage("/admin/admin-login")
	                        .failureHandler(failureHandler)
	        	            .successHandler(successHandler)
	                        .usernameParameter("email")
	                        .defaultSuccessUrl("/admin/", true)
	                        .permitAll()
	        )
	        .logout((logout) -> logout
	        				.logoutRequestMatcher(new AntPathRequestMatcher("/admin/admin-logout"))
	                        .logoutSuccessUrl("/")
	                        .invalidateHttpSession(true)
	                        .clearAuthentication(true)
	                        .permitAll()
	        );
       return http.build();
    }
	
	
	@Bean
	@Order(2)
	SecurityFilterChain userFilterChain(HttpSecurity http, CustomAuthenticationFailureHandler failureHandler,
			CustomAuthenticationSuccessHandler successHandler) throws Exception {
	    http
	    	.securityMatcher("/user/**")
	        .authorizeHttpRequests(authorize -> authorize
        		.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
	        	.requestMatchers("/user/user-registrazione").permitAll()
	        	.requestMatchers("/user/user-form-registrazione").permitAll()
	        	.requestMatchers("/user/**").hasRole("USER")
	        )
	        .formLogin(form -> form
        		.loginPage("/user/user-login")
	            .defaultSuccessUrl("/user/", true)
	            .failureUrl("/user/user-login?error=true")
	            .failureHandler(failureHandler)
	            .successHandler(successHandler)
	            .usernameParameter("email")
	            .permitAll()
	        )
	        .logout(logout -> logout
	        	.logoutRequestMatcher(new AntPathRequestMatcher("/user/user-logout"))
                .logoutSuccessUrl("/")
                .permitAll()
	        );
	    return http.build();
	}

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(new CostumerUserDetailsService(ar, cr));
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
