package br.com.bpadash.api.adm.login;


import br.com.bpadash.dto.TokenDTO;
import br.com.bpadash.dto.error.ErrorDTO;
import br.com.bpadash.model.Administrator;
import br.com.bpadash.model.adm.EmailCodeAdministrator;
import br.com.bpadash.params.adm.ParamCode;
import br.com.bpadash.security.TokenApp;
import br.com.bpadash.services.adm.AdmServices;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import br.com.bpadash.services.email.EmailActivationsService;
import br.com.bpadash.services.email.SendEmailService;
import br.com.bpadash.services.user.ParamLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/adm")
public class LoginAdm {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private TokenApp tokenApp;
    @Autowired
    private AdmServices admServices;
    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    private EmailActivationsService emailActivationsService;


    @PostMapping("/auth")
    public ResponseEntity<Object> autenticarAdm(@RequestBody @Valid ParamLogin form) {
        UsernamePasswordAuthenticationToken dadosLogin = form.converter();

        try {
            Authentication authentication = authManager.authenticate(dadosLogin);

            Administrator administrator = (Administrator) authentication.getPrincipal();

            String token = tokenApp.gerarTokenAdm(administrator);

            String tokenTemp = tokenApp.gerarTokenTemp();

            System.out.println(LocalDateTime.now());

            String code = emailActivationsService.createAndSave(token, administrator);

            sendEmailService.sendCodeLogin(code, administrator);

            return ResponseEntity.ok(tokenTemp);

        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("BAD CREDENTIALS");

        }
    }


    /**
     * Testa o código enviado.
     * @param paramToken
     * @return
     */
    @PostMapping("/verify/code")
    public ResponseEntity<Object> verifyToken(@RequestBody @Valid ParamCode paramToken) {
        try {
            Optional<EmailCodeAdministrator> emailActivationsUserOptional = emailActivationsService.existe(paramToken.getCode());

            if(emailActivationsUserOptional.isEmpty()) return ResponseEntity.badRequest().body("INVALID CODE");

            EmailCodeAdministrator emailActivation = emailActivationsUserOptional.get();

            if (!emailActivation.isValid()) return ResponseEntity.badRequest().body("INVALID CODE");

            if (!emailActivationsService.isValid(emailActivation)) return ResponseEntity.badRequest().body("EXPIRED CODE");

            emailActivationsService.invalidate(emailActivation);

            return ResponseEntity.ok().body(new TokenDTO(EnCryptionAESService.decrypt(emailActivation.getToken())));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ErrorDTO());
        }
    }
}
