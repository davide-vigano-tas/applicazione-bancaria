package eu.tasgroup.applicativo.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
//Classe di configuraizione per API JWT
@Configuration
public class OpenApiConfig {
	@Bean
	 OpenAPI CustomOpenAPI() {
		SecurityScheme bearerScheme = new SecurityScheme()
				.type(SecurityScheme.Type.HTTP)
				.scheme("bearer")
				.bearerFormat("JWT");
		
		return new OpenAPI()
				.components(new Components().addSecuritySchemes("bearerAuth", bearerScheme))
				.addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
	}
}
