package br.com.bpadash.api.user;

import br.com.bpadash.dto.StorageDTO;
import br.com.bpadash.dto.UserDTO;
import br.com.bpadash.dto.bpa.BpaiDTO;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.enumModel.ActionEmployee;
import br.com.bpadash.model.enumModel.ActionType;
import br.com.bpadash.model.user.Employee;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.user.ParamNewPassword;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import br.com.bpadash.services.fpo.LinkFpoService;
import br.com.bpadash.services.professional.LinkProfessionalsService;
import br.com.bpadash.services.user.EmployeeService;
import br.com.bpadash.services.user.SessionUserService;
import br.com.bpadash.services.user.StockHistoryService;
import br.com.bpadash.services.user.UserService;
import br.com.bpadash.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/user")
public class UserApi {

    @Autowired
    private UserService userService;
    @Autowired
    private BpaService bpaService;
    @Autowired
    private BpaiService bpaiService;
    @Autowired
    private SessionUserService sessionUserService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private LinkFpoService linkFpoService;
    @Autowired
    private LinkProfessionalsService linkProfessionalsService;
    @Autowired
    private StockHistoryService stockHistoryService;


    @GetMapping
    public ResponseEntity<Object> getUser(Authentication authentication) {
        User user = userService.get(authentication);

        UserDTO userDTO = new UserDTO(user);

        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/exist/fpo")
    public ResponseEntity<Boolean> getExistFpo(Authentication authentication) {
        User user = userService.userLogged(authentication);

        return ResponseEntity.ok(linkFpoService.exist(user));
    }

    @GetMapping("/exist/prof")
    public ResponseEntity<Boolean> getExistProf(Authentication authentication) {
        User user = userService.userLogged(authentication);

        return ResponseEntity.ok(linkProfessionalsService.exist(user));
    }

    @GetMapping("/storage")
    public ResponseEntity<StorageDTO> storageDTO(Authentication authentication) {
        User user = userService.get(authentication);

        return ResponseEntity.ok(new StorageDTO(user));
    }

    @GetMapping("/get/total/rules")
    public ResponseEntity<List<String>> getTotalRules(Authentication authentication) {
        User user = userService.get(authentication);

        List<String> rulesDTO = new ArrayList<>();

        int rules = user.getTreatmentFile().getRuleTreatmentPaList().size();
        int rules1 = user.getTreatmentFile().getRuleTreatmentPaCboList().size();
        int rules2 = user.getTreatmentFile().getRuleTreatmentPaDeleteList().size();
        int rules3 = user.getTreatmentFile().getRuleReplacementCustoms().size();
        int total = rules + rules1 + rules2 + rules3;

        rulesDTO.add(String.valueOf(total));
        rulesDTO.add(EnCryptionAESService.decrypt(user.getPackageNumberRules()));

        return ResponseEntity.ok(rulesDTO);
    }

    @GetMapping("/get/total/bpa")
    public ResponseEntity<Integer> getTotalBpas(Authentication authentication) {
        User user = userService.get(authentication);

        return ResponseEntity.ok(user.getBpas().size());
    }

    @PostMapping("/edit/password/{employeeKey}")
    public ResponseEntity<Object> editPassword(@PathVariable String employeeKey, @RequestBody @Valid ParamNewPassword paramNewPassword, Authentication authentication) {
        try {
            User user = userService.get(authentication);

            Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

            if(employeeOptional.isPresent() && employeeOptional.get().isMaster()) {
                String currentPassword = paramNewPassword.getNewPassword();

                if(!(currentPassword.equals(paramNewPassword.getConfPassword()))) {
                    return ResponseEntity.badRequest().body("NOT EQUALS");
                }

                if (userService.testPassword(user, currentPassword)) {
                    return ResponseEntity.badRequest().body("INCORRECT PASSWORD");
                }

                userService.updatePassword(user, currentPassword);

                stockHistoryService.register(ActionEmployee.UPDATE_PASSWORD.getAction(), ActionType.UPDATE_PASSWORD.getAction(), null, 0, user, employeeOptional.get());

                return ResponseEntity.ok().build();
            }

            return ResponseEntity.status(401).body("FORBIDDEN");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ops, Algo deu errado!");
        }
    }
}
