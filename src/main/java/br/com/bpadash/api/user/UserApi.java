package br.com.bpadash.api.user;

import br.com.bpadash.dto.StorageDTO;
import br.com.bpadash.dto.UserDTO;
import br.com.bpadash.dto.bpa.TimeLineUserDTO;
import br.com.bpadash.dto.bpa.ValidationsDTO;
import br.com.bpadash.dto.professional.CountProfessionalDTO;
import br.com.bpadash.model.User;
import br.com.bpadash.params.bpa.ParamValidationBpac;
import br.com.bpadash.params.bpa.ParamValidationBpai;
import br.com.bpadash.params.bpa.ParamValidationTitle;
import br.com.bpadash.params.user.ParamNewPassword;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.Validation;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserApi {

    @Autowired
    private UserService userService;

    @Autowired
    private BpaiService bpaiService;

    @GetMapping
    public ResponseEntity<UserDTO> userDTO(Authentication authentication) {
        User user = userService.get(authentication);

        UserDTO userDTO = new UserDTO(user);

        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/timeline")
    public ResponseEntity<List<TimeLineUserDTO>> timeLineUser(Authentication authentication) {
        User user = userService.get(authentication);

        return ResponseEntity.ok(userService.timeLine(user));
    }

    @GetMapping("/storage")
    public ResponseEntity<StorageDTO> storageDTO(Authentication authentication) {
        User user = userService.get(authentication);

        return ResponseEntity.ok(new StorageDTO(user));
    }


    @GetMapping("/get/validations")
    public ResponseEntity<Object> getValidations(Authentication authentication) {
        User user = userService.get(authentication);

        return ResponseEntity.ok(new ValidationsDTO(user));
    }

    @PostMapping("/set/validations/title")
    public ResponseEntity<Object> setValidationsTitle(@RequestBody ParamValidationTitle paramValidationTitle, Authentication authentication) {
        User user = userService.get(authentication);

        userService.setValidationTitle(user, paramValidationTitle);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/set/validations/bpac")
    public ResponseEntity<Object> setValidationsBpac(@RequestBody ParamValidationBpac paramValidationBpac, Authentication authentication) {
        User user = userService.get(authentication);

        userService.setValidationBpac(user, paramValidationBpac);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/set/validations/bpai")
    public ResponseEntity<Object> setValidationsBpai(@RequestBody ParamValidationBpai paramValidationBpai, Authentication authentication) {
        User user = userService.get(authentication);

        userService.setValidationBpai(user, paramValidationBpai);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/edit/password")
    public ResponseEntity<Object> editPassword(@RequestBody @Valid ParamNewPassword paramNewPasswordNewPassword, Authentication authentication) {
        try {
            User user = userService.get(authentication);
            String currentPassword = paramNewPasswordNewPassword.getNewPassword();

            if(!(currentPassword.equals(paramNewPasswordNewPassword.getConfPassword()))) {
                return ResponseEntity.badRequest().body("As senhas não são iguais!");
            }

            if (userService.testPassword(user, currentPassword)) {
                return ResponseEntity.badRequest().body("Sua senha está incorreta!");
            }

            UserDTO userDTO = userService.updatePassword(user, currentPassword);

            return ResponseEntity.ok(userDTO);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ops, Algo deu errado!");
        }
    }
}
