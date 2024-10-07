package com.training.greetapi.shared.api.documentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

//http://localhost:8080/v3/api-docs
//http://localhost:8080/swagger-ui/index.html	
	
	@Bean
	public OpenAPI myOpenAPI() {

		Server devServer = new Server();
		devServer.setUrl("http://localhost:8080");
		devServer.setDescription("Server URL in Development environment");

		Server prodServer = new Server();
		prodServer.setUrl("greeting.api");
		prodServer.setDescription("Server URL in Production environment");

		Contact contact = new Contact();
		contact.setEmail("dasun.suranjaya@gmail.com");
		contact.setName("Dasun Samarasiri");
		contact.setUrl("test url");

		License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

		Info info = new Info().title("Greeting API").version("1.0").contact(contact)
				.description("This API exposes endpoints to manage greetings.")
				.termsOfService("test url").license(mitLicense);

		return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
	}

}
