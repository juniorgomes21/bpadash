package br.com.bpadash;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableTransactionManagement
public class BpadashApplication {

	public static void main(String[] args) throws IOException {

		SpringApplication app = new SpringApplication(BpadashApplication.class);
		app.run(args);

//		Properties properties = new Properties();
//		ClassPathResource resource = new ClassPathResource("application.properties");
//		properties.load(new FileInputStream(resource.getFile()));
//
//		Dotenv dotenv = Dotenv.load();
//		String dbUrl = dotenv.get("DB_URL");
//		String dbUsername = dotenv.get("DB_USERNAME");
//		String dbPassword = dotenv.get("DB_PASSWORD");
//
//		properties.setProperty("spring.datasource.url", "jdbc:" + dbUrl);
//		properties.setProperty("spring.datasource.username", dbUsername);
//		properties.setProperty("spring.datasource.password", dbPassword);
//
//		app.setDefaultProperties(properties);
	}
}
