package br.com.bpadash.api.user.sigtap;

import br.com.bpadash.dto.DatesDTO;
import br.com.bpadash.dto.bpa.TimeLineDTO;
import br.com.bpadash.dto.sigtap.DateDTO;
import br.com.bpadash.dto.sigtap.FpoDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.enumModel.ActionEmployee;
import br.com.bpadash.model.enumModel.ActionType;
import br.com.bpadash.model.sigtap.*;
import br.com.bpadash.model.user.Employee;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.fpo.ParamNewFpo;
import br.com.bpadash.params.fpo.ParamNewLineFpo;
import br.com.bpadash.services.fpo.FpoService;
import br.com.bpadash.services.fpo.LinkFpoService;
import br.com.bpadash.services.scanner.ScannerFile;
import br.com.bpadash.services.user.EmployeeService;
import br.com.bpadash.services.user.StockHistoryService;
import br.com.bpadash.services.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fpo")
public class FpoApi {
    @Autowired
    private ScannerFile scannerFile;
    @Autowired
    private UserService userService;
    @Autowired
    private FpoService fpoService;
    @Autowired
    private LinkFpoService linkFpoService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private StockHistoryService stockHistoryService;


    @GetMapping("/get/{pa}/{employeeKey}")
    public ResponseEntity<Object> getProfessionals(@PathVariable String pa, @PathVariable String employeeKey, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

        if(employeeOptional.isPresent()) {
            DatesSigtap datesSigtap = user.getDatesSigtap();

            Optional<LinkFpo> linkFpoOptional;
            if (datesSigtap.isDateProfessionalsAuto()) {
                linkFpoOptional = linkFpoService.get(user);
            } else {
                linkFpoOptional = linkFpoService.get(datesSigtap.getDateProfessionals() , user);
            }

            if (linkFpoOptional.isPresent()) {
                LinkFpo linkFpo = linkFpoOptional.get();

                Fpo fpo = fpoService.get(linkFpo , pa);

                if (fpo == null) {
                    return ResponseEntity.badRequest().body("NOT FOUND FPO");
                }

                return ResponseEntity.ok(new FpoDTO(fpo));
            }

            return ResponseEntity.badRequest().body("NOT EXIST DATE FPO");
        }

        return ResponseEntity.status(401).body("FORBIDDEN");
    }

    @GetMapping("/timeline")
    public ResponseEntity<List<TimeLineDTO>> timeLineUser(Authentication authentication) {
        User user = userService.get(authentication);

        List<LinkFpo> linkFpos = linkFpoService.getAll(user);

        return ResponseEntity.ok(userService.timeLineFpo(linkFpos));
    }

    @PostMapping("/create/{employeeKey}")
    public ResponseEntity<Object> createFpo(@PathVariable String employeeKey, @RequestPart("file") MultipartFile file, @RequestParam("paramNewFpo") String paramNewBpaJson, Authentication authentication) throws JsonProcessingException {
        User user = userService.get(authentication);

        Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

        if(employeeOptional.isPresent() && employeeOptional.get().getPermissions().isAddFpo()) {

            List<ErrorsFile> errorsFiles = new ArrayList<>();

            ObjectMapper objectMapper = new ObjectMapper();
            ParamNewFpo paramNewFpo = objectMapper.readValue(paramNewBpaJson, ParamNewFpo.class);

            String response = scannerFile.createFpo(file, paramNewFpo, errorsFiles, user, employeeOptional.get());

            switch (response) {
                case "CREATE" -> {
                    return ResponseEntity.ok().body(response);
                }
                case "ERROR FILE" -> {
                    return ResponseEntity.badRequest().body(errorsFiles);
                }
                default -> {
                    return ResponseEntity.badRequest().body(response);
                }
            }
        }

        return ResponseEntity.status(401).body("FORBIDDEN");
    }

    @PostMapping("/create/line")
    public ResponseEntity<Object> createLine(@RequestBody @Valid ParamNewLineFpo paramNewLineFpo) {

        return ResponseEntity.ok().build();
    }

    /**
     * Obtem a data atual do FPO de um usuário.
     * @param authentication
     * @return
     */
    @GetMapping("/get/date") //TODO concluido
    public ResponseEntity<Object> getDate(Authentication authentication) {
        User user = userService.get(authentication);

        Optional<LinkFpo> linkFpoOptional = linkFpoService.verify(user);

        return linkFpoOptional.<ResponseEntity<Object>>map(linkFpo -> ResponseEntity.ok(new DateDTO(linkFpo.getDate()))).orElseGet(() -> ResponseEntity.badRequest().body("NOT FOUND FPO"));
    }


    /**
     * Obtem todas as datas FPO de um usuário.
     * @param authentication
     * @return
     */
    @GetMapping("/dates") //TODO concluido
    public ResponseEntity<DatesDTO> dates(Authentication authentication) {
        User user = userService.get(authentication);

        DatesDTO datesDTO = linkFpoService.getDates(user);

        return ResponseEntity.ok(datesDTO);
    }

    @GetMapping("/exist")
    public ResponseEntity<Boolean> exist(Authentication authentication) {
        User user = userService.get(authentication);

        boolean b = linkFpoService.exist(user);

        return ResponseEntity.ok(b);
    }

    @Transactional
    @PostMapping("/delete/{employeeKey}")
    public ResponseEntity<Object> deleteFpo(@PathVariable String employeeKey, @RequestBody List<Long> ids, Authentication authentication) {
        User user = userService.get(authentication);
        Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

        if(employeeOptional.isPresent() && employeeOptional.get().getPermissions().isDeleteFpo()) {
            ids.forEach( id -> {
                LinkFpo link = linkFpoService.get(id);

                fpoService.delete(link);

                userService.updateStorageAndSave(user, link.getFileSizeInBytes(), true);

                linkFpoService.delete(link);

                stockHistoryService.register(ActionEmployee.DELETE_FPO.getAction(), ActionType.DELETE.getAction(), link.getDate(), 0, user, employeeOptional.get());
            });

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(401).body("FORBIDDEN");
    }
}
