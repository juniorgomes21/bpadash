package br.com.bpadash.api.adm.login;


import br.com.bpadash.dto.TokenDTO;
import br.com.bpadash.security.TokenApp;
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

@RestController
@RequestMapping("/api/adm")
public class LoginAdm {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenApp tokenApp;

    @PostMapping("/auth")
    public ResponseEntity<Object> autenticarAdm(@RequestBody @Valid ParamLogin form) {
        UsernamePasswordAuthenticationToken dadosLogin = form.converter();

        try {
            Authentication authentication = authManager.authenticate(dadosLogin);
            String token = tokenApp.gerarTokenAdm(authentication);

            return ResponseEntity.ok(new TokenDTO(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("BAD CREDENTIALS");

        }
    }
}
