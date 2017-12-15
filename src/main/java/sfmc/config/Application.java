package sfmc.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "sfmc.controller","sfmc.model", "sfmc.repository","sfmc.service", "sfmc.util"}) // define packages for scan
@EntityScan(basePackages = { "sfmc.model" })
@EnableJpaRepositories(basePackages = {"sfmc.repository"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
