package br.com.bpadash.services.email;

import br.com.bpadash.model.Administrator;
import br.com.bpadash.model.email.SendEmailRecordAdministrator;
import br.com.bpadash.repository.email.SendEmailRecordAdministratorRepository;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConfigEmailService {

    @Autowired
    private final JavaMailSender javaMailSender;

    @Autowired
    private SendEmailRecordAdministratorRepository sendEmailRecordAdministratorRepository;


    public ConfigEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public boolean sendCodeLogin(Administrator administrator, String title, String content, String type) {
        boolean error = false;
        SendEmailRecordAdministrator sendEmailRecord = new SendEmailRecordAdministrator(type, title, content, administrator);

        try {
            System.out.println("Enviar Email");
            System.out.println(LocalDateTime.now());

            SimpleMailMessage msg = new SimpleMailMessage();

            msg.setTo(EnCryptionAESService.decrypt(administrator.getEmail()));
            msg.setSubject(title);
            msg.setText(content);
            try {
                javaMailSender.send(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(LocalDateTime.now());
            System.out.println("Enviado com sucesso!");

        } catch (Exception e) {
            sendEmailRecord.setMsgError(e.getCause().toString());
            sendEmailRecord.setError(true);
            error = true;
        }

        sendEmailRecordAdministratorRepository.save(sendEmailRecord);

        return error;
    }

    public boolean sendCodeTestUser(Administrator administrator, String type, String email, String title, String content) {
        boolean error = false;

        SendEmailRecordAdministrator sendEmailRecord = new SendEmailRecordAdministrator(type, title, content, administrator);

        try {
            SimpleMailMessage msg = new SimpleMailMessage();

            msg.setTo(email);
            msg.setSubject(title);
            msg.setText(content);
            try {
                javaMailSender.send(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            sendEmailRecord.setMsgError(e.getCause().toString());
            sendEmailRecord.setError(true);
            error = true;
        }

        sendEmailRecordAdministratorRepository.save(sendEmailRecord);

        return error;
    }
}
