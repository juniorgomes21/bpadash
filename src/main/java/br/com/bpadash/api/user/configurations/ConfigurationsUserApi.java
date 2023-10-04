package br.com.bpadash.api.user.configurations;

import br.com.bpadash.dto.error.ErrorResponseDTO;
import br.com.bpadash.params.ParamNewUser;
import br.com.bpadash.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/configurations")
public class ConfigurationsUserApi {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@Valid @RequestBody ParamNewUser paramNewUser) {
        try {
            userService.createUser(paramNewUser);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO());
        }
    }
}
