package br.com.bpadash.api.user;

import br.com.bpadash.dto.StorageDTO;
import br.com.bpadash.dto.UserDTO;
import br.com.bpadash.dto.bpa.BpaiDTO;
import br.com.bpadash.dto.bpa.ValidationsDTO;
import br.com.bpadash.dto.professional.ProfessionalDTO;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.sigtap.DatesSigtap;
import br.com.bpadash.model.sigtap.LinkProfessionals;
import br.com.bpadash.model.sigtap.ProfessionalComplete;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.bpa.ParamValidationBpac;
import br.com.bpadash.params.bpa.ParamValidationBpai;
import br.com.bpadash.params.bpa.ParamValidationTitle;
import br.com.bpadash.params.user.ParamNewPassword;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.user.UserService;
import br.com.bpadash.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserApi {

    @Autowired
    private UserService userService;
    @Autowired
    private BpaService bpaService;
    @Autowired
    private BpaiService bpaiService;

    @GetMapping
    public ResponseEntity<UserDTO> userDTO(Authentication authentication) {
        User user = userService.get(authentication);

        UserDTO userDTO = new UserDTO(user);

        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/storage")
    public ResponseEntity<StorageDTO> storageDTO(Authentication authentication) {
        User user = userService.get(authentication);

        return ResponseEntity.ok(new StorageDTO(user));
    }

    @GetMapping("/get/{dateBPA}/{cnsPac}")
    public ResponseEntity<Object> getProfessionals(@PathVariable String dateBPA, @PathVariable String cnsPac, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(dateBPA), user);
        if(bpaOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();

            List<Bpai> bpaiList = bpaiService.get(bpa);

            EncryptionService.decryptCnsPac(bpaiList);

            Bpai bpaiOk = null;
            for(Bpai bpai: bpaiList) {
                if(bpai.getCnspac().equals(cnsPac)) {
                    bpaiOk = bpai;
                }
            }

            if(bpaiOk == null) {
                return ResponseEntity.badRequest().body("NOT FOUND");
            }

            EncryptionService.decryptBpai(List.of(bpaiOk), false);

            return ResponseEntity.ok(new BpaiDTO(bpaiOk, ""));
        }

        return ResponseEntity.badRequest().body("NOT EXIST DATE PROFESSIONALS");
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
