package br.com.bpadash.api.user.login;


import br.com.bpadash.api.adm.login.LoginForm;
import br.com.bpadash.dto.DatesDTO;
import br.com.bpadash.dto.TokenDTO;
import br.com.bpadash.dto.UserDTO;
import br.com.bpadash.dto.bpa.BpaDTO;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.user.User;
import br.com.bpadash.security.TokenApp;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.user.UserService;
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
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiLogin {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private TokenApp tokenApp;
    @Autowired
    private UserService userService;
    @Autowired
    private BpaService bpaService;

    @PostMapping("/auth")
    public ResponseEntity<TokenDTO> autenticar(@RequestBody @Valid LoginForm form) {

        UsernamePasswordAuthenticationToken dadosLogin = form.converter();

        try {
            Authentication authentication = authManager.authenticate(dadosLogin);

            String token = tokenApp.gerarToken(authentication);

            User user = userService.userLogged(authentication);

            DatesDTO datesDTOS = bpaService.getDates(user);

            UserDTO userDTO = new UserDTO(user);


            return ResponseEntity.ok(new TokenDTO(token, userDTO, datesDTOS));

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
