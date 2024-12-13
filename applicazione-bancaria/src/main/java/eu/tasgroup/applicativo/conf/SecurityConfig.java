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

	private final AmministratoriRepository ar;
	private final ClientiRepository cr;

    public SecurityConfig(AmministratoriRepository ar, ClientiRepository cr) {
		this.ar = ar;
		this.cr = cr;
	}                    

	@Bean
	@Order(1)
    SecurityFilterChain adminFilterChain(HttpSecurity http, CustomAuthenticationFailureHandler failureHandler) throws Exception {
        http
	        .securityMatcher("/admin/**")
	        .authorizeHttpRequests((authorize) -> authorize
	        				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
	                        .requestMatchers("/admin/**").hasRole("ADMIN")
	                        .anyRequest().authenticated()
	        )
	        .formLogin((form) -> form
	                        .loginPage("/admin-login")
	                        .failureUrl("/admin-login?error=true")
	                        .failureHandler(failureHandler)
	                        .usernameParameter("email")
	                        .defaultSuccessUrl("/admin/", true)
	                        .permitAll()
	        )
	        .logout((logout) -> logout
	                        .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
	                        .logoutSuccessUrl("/")
	                        .invalidateHttpSession(true)
	                        .clearAuthentication(true)
	                        .permitAll()
	        );
       return http.build();
    }
	
	
	@Bean
	@Order(2)
	SecurityFilterChain userFilterChain(HttpSecurity http, CustomAuthenticationFailureHandler failureHandler) throws Exception {
	    http
	    	.securityMatcher("/user/**")
	        .authorizeHttpRequests(authorize -> authorize
	        	.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
	        	.requestMatchers("/user/user-registrazione").permitAll()
	        	.requestMatchers("/user/user-form-registrazione").permitAll()
	        	.requestMatchers("/user/**").hasRole("USER")
	            .anyRequest().authenticated()
	        )
	        .formLogin(form -> form
	            .loginPage("/user/user-login")
	            .usernameParameter("email")
	            .defaultSuccessUrl("/user/", true)
	            .failureHandler(failureHandler)
	            .permitAll()
	        )
	        .logout(logout -> logout
	            .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
	            .logoutSuccessUrl("/")
	            .invalidateHttpSession(true)
	            .clearAuthentication(true)
	        );
	    return http.build();
	}

	  
	@Bean
	@Order(3)
	SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests(authorize -> authorize
	        		.anyRequest().permitAll()
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
