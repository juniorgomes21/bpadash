package br.com.bpadash.services.email;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    private static final Dotenv dotenv = Dotenv.load();

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(dotenv.get("EMAIL_USERNAME"));
        mailSender.setPassword(dotenv.get("EMAIL_PASSWORD"));

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", dotenv.get("SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH"));
        javaMailProperties.put("mail.smtp.starttls.enable", dotenv.get("SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE"));
        javaMailProperties.put("mail.smtp.connectiontimeout", dotenv.get("SPRING_MAIL_PROPERTIES_MAIL_SMTP_CONNECTIONTIMEOUT"));
        javaMailProperties.put("mail.smtp.timeout", dotenv.get("SPRING_MAIL_PROPERTIES_MAIL_SMTP_TIMEOUT"));
        javaMailProperties.put("mail.smtp.writetimeout", dotenv.get("SPRING_MAIL_PROPERTIES_MAIL_SMTP_WRITETIMEOUT"));

        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }
}