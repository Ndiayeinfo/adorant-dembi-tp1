package com.isi.diti5.tp1_jee;

import com.isi.diti5.tp1_jee.entities.Utilisateur;
import com.isi.diti5.tp1_jee.repository.UtilisateurRepository;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@SpringBootApplication
public class Tp1JeeApplication implements CommandLineRunner {

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	public static void main(String[] args) {
		SpringApplication.run(Tp1JeeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		utilisateurRepository.save(new Utilisateur(null, "jeremie", "jeremie@gmail.com"));
		utilisateurRepository.save(new Utilisateur(null, "youssou", "youssou@gmail.com"));
		utilisateurRepository.save(new Utilisateur(null, "tidjane", "tidjane@gmail.com"));
		utilisateurRepository.save(new Utilisateur(null, "anida", "anida@gmail.com"));
		utilisateurRepository.save(new Utilisateur(null, "mohamed", "mohamed@gmail.com"));
		utilisateurRepository.save(new Utilisateur(null, "sidy", "sidy@gmail.com"));
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {

		Contact contact = new Contact();
		contact.setEmail("youssoundiaye.yn.yn@gmail.com");
		contact.setName("Youssou NDIAYE");
		contact.setUrl("https://www.bezkoder.com");

		return new OpenAPI()
				.components(new Components().addSecuritySchemes("basicScheme",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
				.info(new Info().title("User API Application").version(appVersion)
						.license(new License().name("Apache 2.0").url("http://springdoc.org"))
						.contact(contact));
	}
}
