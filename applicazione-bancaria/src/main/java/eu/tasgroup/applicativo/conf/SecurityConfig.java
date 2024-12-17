package eu.tasgroup.applicativo.conf;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import eu.tasgroup.applicativo.filet.JwtAuthenticationFilter;
import eu.tasgroup.applicativo.repository.AmministratoriRepository;
import eu.tasgroup.applicativo.repository.ClientiRepository;
import eu.tasgroup.applicativo.security.JwtAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final ClientiRepository cr;
	private final AmministratoriRepository ar;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	public SecurityConfig(ClientiRepository cr, AmministratoriRepository ar,
			JwtAuthenticationFilter jwtAuthenticationFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
		this.cr = cr;
		this.ar = ar;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
	}

	@Bean

	@Order(1)
	SecurityFilterChain adminFilterChain(HttpSecurity http, CustomAuthenticationFailureHandler failureHandler,
			CustomAuthenticationSuccessHandler successHandler) throws Exception {
		http.securityMatcher("/admin/**")
				.authorizeHttpRequests(
						(authorize) -> authorize.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
								.permitAll().requestMatchers("/admin/admin-registrazione").permitAll()
								.requestMatchers("/admin/admin-form-registrazione").permitAll()
								.requestMatchers("/admin/**").hasRole("ADMIN"))
				.formLogin((form) -> form.loginPage("/admin/admin-login").failureHandler(failureHandler)
						.successHandler(successHandler).usernameParameter("email").permitAll())
				.logout((logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/admin/admin-logout"))
						.logoutSuccessUrl("/").invalidateHttpSession(true).clearAuthentication(true).permitAll());
		return http.build();
	}

	@Bean
	@Order(2)
	SecurityFilterChain userFilterChain(HttpSecurity http, CustomAuthenticationFailureHandler failureHandler,
			CustomAuthenticationSuccessHandler successHandler) throws Exception {
		http.securityMatcher("/user/**")
				.authorizeHttpRequests(
						authorize -> authorize.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
								.permitAll().requestMatchers("/user/user-registrazione").permitAll()
								.requestMatchers("/user/user-form-registrazione").permitAll()
								.requestMatchers("/user/**").hasRole("USER"))
				.formLogin(form -> form.loginPage("/user/user-login").usernameParameter("email")
						.failureHandler(failureHandler).successHandler(successHandler).permitAll())
				.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/user/user-logout"))
						.logoutSuccessUrl("/").permitAll());
		return http.build();
	}

	@Bean
	@Order(3)
	SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
		http.securityMatcher("/api/**")
				// Abilita CORS e disabilita CSRF
				.cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf(csrf -> csrf.disable())
				// setto sessione stateless
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				// gestisco eccezioni per request non autorizzate
				.exceptionHandling(handling -> handling.authenticationEntryPoint(jwtAuthenticationEntryPoint))
				// setto permessi per endpoints pubblici
				// setto endpoint privati
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/login").permitAll().anyRequest().authenticated())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

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

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	UrlBasedCorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*"); // Consenti tutte le origini
		config.addAllowedHeader("*"); // Consenti tutte le intestazioni
		config.addAllowedMethod("*"); // Consenti tutti i metodi (GET, POST, ecc.)

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

}
