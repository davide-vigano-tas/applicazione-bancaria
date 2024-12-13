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
        http.authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
        )
        .formLogin((form) -> form
                        .loginPage("/admin-login")
                        .failureUrl("/admin-login?error=true")
                        .failureHandler(failureHandler)
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
    SecurityFilterChain filterChain(HttpSecurity http, CustomAuthenticationFailureHandler failureHandler) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/user/**").hasRole("USER")
        )
        .formLogin((form) -> form
                        .loginPage("/user-login")
                        .failureUrl("/user-login?error=true")
                        .failureHandler(failureHandler)
                        .defaultSuccessUrl("/user/", true)
                        .permitAll()
        )
        .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
        );
        return http.build();
    }
	
	 @Bean
	 @Order(3)
	    SecurityFilterChain defaultFilterChain(HttpSecurity http,CustomAuthenticationFailureHandler failureHandler) throws Exception {
	        http.authorizeHttpRequests(authorize -> authorize
	            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
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
