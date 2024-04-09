package br.com.bpadash.services.email;

import br.com.bpadash.model.Administrator;
import br.com.bpadash.model.email.EmailType;
import br.com.bpadash.model.email.MessageModel;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

    @Autowired
    private ConfigEmailService configEmailService;

    public boolean sendCodeLogin(String code, Administrator administrator) {
        String type = "email";

        return configEmailService.sendCodeLogin(
                administrator,
                MessageModel.createTitle(EnCryptionAESService.decrypt(administrator.getName())),
                MessageModel.codeConfirm(code),
                EmailType.WELCOME.getType()
        );
    }

    public boolean sendCodeTestUser(String email, String code, Administrator administrator) {
        String type = "TEST_USER";

        return configEmailService.sendCodeTestUser(
                administrator,
                type,
                email,
                MessageModel.createTitle("Cliente BPADASH"),
                MessageModel.codeTestEmail(code)
        );
    }

}
