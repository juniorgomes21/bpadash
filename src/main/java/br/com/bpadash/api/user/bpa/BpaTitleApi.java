package br.com.bpadash.api.user.bpa;

import br.com.bpadash.dto.bpa.TitleBpaDTO;
import br.com.bpadash.errorValidation.ErrorValidationDTO;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.TitleBpa;
import br.com.bpadash.model.enumModel.ActionEmployee;
import br.com.bpadash.model.enumModel.ActionType;
import br.com.bpadash.model.user.Employee;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.bpa.ParamUpdateTitle;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.TitleBpaService;
import br.com.bpadash.services.user.EmployeeService;
import br.com.bpadash.services.user.StockHistoryService;
import br.com.bpadash.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/title")
public class BpaTitleApi {

    @Autowired
    private UserService userService;
    @Autowired
    private BpaService bpaService;
    @Autowired
    private TitleBpaService titleBpaService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private StockHistoryService stockHistoryService;


    @GetMapping("/get/{identifier}")
    public ResponseEntity<TitleBpaDTO> titleBpaDTO(@PathVariable @Valid @NotBlank String identifier, Authentication authentication) {
        User user = userService.get(authentication);

        Bpa bpa = bpaService.get(identifier, user);

        TitleBpa titleBpa = titleBpaService.get(bpa);

        if(bpa == null) {
            return ResponseEntity.badRequest().body(null);
        }

        int countRules = userService.countRules(user);

        return ResponseEntity.ok(new TitleBpaDTO(titleBpa, countRules));
    }

    @GetMapping("/get/{month}/{year}")
    public ResponseEntity<TitleBpaDTO> titleBpaDTO(@PathVariable int month, @PathVariable int year, Authentication authentication) {
        User user = userService.get(authentication);
        Bpa bpa = bpaService.getForDate(month, year, user);

        if(bpa == null) {
            return ResponseEntity.badRequest().body(null);
        }

        TitleBpa titleBpa = titleBpaService.get(bpa);

        return ResponseEntity.ok(new TitleBpaDTO(titleBpa, 0));
    }

    @PostMapping("/edit/{id}/{employeeKey}")
    public ResponseEntity<Object> editBpai(@PathVariable Long id, @PathVariable String employeeKey, @RequestBody @Valid ParamUpdateTitle paramUpdateTitle, Authentication authentication) {
        try {
            User user = userService.get(authentication);
            Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

            if(employeeOptional.isPresent() && employeeOptional.get().getPermissions().isEditBpa()) {
                List<ErrorValidationDTO> erros = new ArrayList<>();

                Optional<TitleBpa> optionalTitle = titleBpaService.get(id);
                if(optionalTitle.isEmpty()) {
                    erros.add(new ErrorValidationDTO("id", "O id não existe."));
                    return ResponseEntity.badRequest().body(erros);
                }

                TitleBpaDTO titleBpaDTO = new TitleBpaDTO(titleBpaService.editAndSave(optionalTitle.get(), paramUpdateTitle), 0);

                stockHistoryService.register(ActionEmployee.UPDATE_TITLE.getAction(), ActionType.UPDATE.getAction(), optionalTitle.get().getBpa().getDate(), 1, user, employeeOptional.get());

                return ResponseEntity.ok(titleBpaDTO);
            }

            return ResponseEntity.status(401).body("FORBIDDEN");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }



}
