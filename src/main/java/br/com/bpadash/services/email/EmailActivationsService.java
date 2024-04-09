package br.com.bpadash.services.email;


import br.com.bpadash.model.Administrator;
import br.com.bpadash.model.adm.EmailCodeAdministrator;
import br.com.bpadash.model.enumModel.ZoneTime;
import br.com.bpadash.repository.email.EmailActivationsRepository;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class EmailActivationsService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int TOKEN_LENGTH = 20;
    @Autowired
    private EmailActivationsRepository emailActivationsRepository;


    /**
     * Instância a classe EmailActivationsUser e salva.
     * @param administrator
     * @return
     */
    public String createAndSave(String token, Administrator administrator) {
        String code = this.generateCode();

        this.save(new EmailCodeAdministrator(EnCryptionAESService.encrypt(code), EnCryptionAESService.encrypt(token), administrator));

        return code;
    }

    /**
     * Gera um token de 20 caracteres usando caracteres alfanuméricos.
     * @return
     */
    private String generateCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(TOKEN_LENGTH);

        for (int i = 0; i < TOKEN_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(randomIndex));
        }

        return code.toString();
    }

    /**
     * Verifica se o código existe no banco de dados.
     * @param code
     * @return
     */
    public Optional<EmailCodeAdministrator> existe(String code) {
        return emailActivationsRepository.findByCode(EnCryptionAESService.encrypt(code));
    }

    /**
     * Verifica se o código é válido.
     * @param emailActivation
     * @return
     */
    public boolean isValid(EmailCodeAdministrator emailActivation) {

        boolean isValid = emailActivation.getDateExpires().isAfter(LocalDateTime.now(ZoneId.of(ZoneTime.BR.getBr())));

        if(isValid) {
            return true;
        }

        return false;
    }

    /**
     * invalida um código enviado
     * @param emailActivation
     */
    public void invalidate(EmailCodeAdministrator emailActivation) {
        emailActivation.setValid(false);

        this.save(emailActivation);
    }

    /**
     * Sava uma entidade EmailActivationsUser.
     * @param emailTokenAdministrator
     * @return
     */
    public EmailCodeAdministrator save(EmailCodeAdministrator emailTokenAdministrator) {
        return emailActivationsRepository.save(emailTokenAdministrator);
    }
}
