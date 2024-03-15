package br.com.bpadash.api.user.session;

import br.com.bpadash.model.user.SessionUser;
import br.com.bpadash.model.user.User;
import br.com.bpadash.services.user.SessionUserService;
import br.com.bpadash.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/session")
public class SessionApi {

    @Autowired
    private UserService userService;
    @Autowired
    private SessionUserService sessionUserService;

    @GetMapping("/get/logged")
    public ResponseEntity<Object> getLoggedUser(Authentication authentication) {
        User user = userService.userLogged(authentication);

        Optional<SessionUser> sessionUserOptional = sessionUserService.get(user);

        if(sessionUserOptional.isPresent()) {
            SessionUser sessionUser = sessionUserOptional.get();

            sessionUserService.updateSession(sessionUser);

            return ResponseEntity.ok().body(sessionUser.getAccountLoggeds());
        }

        return ResponseEntity.badRequest().body("NOT FOUND SESSION");
    }

}
