package br.com.bpadash.api.user.sigtap;

import br.com.bpadash.dto.bpa.TimeLineDTO;
import br.com.bpadash.dto.professional.ProfessionalDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.enumModel.ActionEmployee;
import br.com.bpadash.model.enumModel.ActionType;
import br.com.bpadash.model.sigtap.DatesSigtap;
import br.com.bpadash.model.sigtap.LinkProfessionals;
import br.com.bpadash.model.sigtap.ProfessionalComplete;
import br.com.bpadash.model.user.Employee;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.professional.ParamNewProfessionals;
import br.com.bpadash.params.professional.ParamUpdateProfessional;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import br.com.bpadash.services.professional.LinkProfessionalsService;
import br.com.bpadash.services.professional.ProfessionalService;
import br.com.bpadash.services.scanner.ScannerFile;
import br.com.bpadash.services.sigtap.DatesSigtapService;
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
import java.util.*;

@RestController
@RequestMapping("/api/prof")
public class ProfessionalApi {

    @Autowired
    private UserService userService;
    @Autowired
    private ScannerFile scannerFile;
    @Autowired
    private ProfessionalService professionalService;
    @Autowired
    private LinkProfessionalsService linkProfessionalsService;
    @Autowired
    private DatesSigtapService datesSigtapService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private StockHistoryService stockHistoryService;


    @GetMapping("/get/{key}/{employeeKey}")
    public ResponseEntity<Object> getProfessionals(@PathVariable String key, @PathVariable String employeeKey, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

        if(employeeOptional.isPresent()) {

            DatesSigtap datesSigtap = user.getDatesSigtap();

            Optional<LinkProfessionals> linkProfessionalsOptional;
            if(datesSigtap.isDateProfessionalsAuto()) {
                linkProfessionalsOptional = linkProfessionalsService.get(user);
            } else {
                linkProfessionalsOptional = linkProfessionalsService.get(datesSigtap.getDateProfessionals(), user);
            }

            if(linkProfessionalsOptional.isPresent()) {
                LinkProfessionals linkProfessionals = linkProfessionalsOptional.get();

                ProfessionalComplete professionalComplete = professionalService.get(linkProfessionals, key);

                if(professionalComplete == null) {
                    return ResponseEntity.badRequest().body("NOT FOUND");
                }

                EnCryptionAESService.decrypt(new ArrayList<>(List.of(professionalComplete)));

                return ResponseEntity.ok(new ProfessionalDTO(professionalComplete));
            }

            return ResponseEntity.badRequest().body("NOT EXIST DATE");
        }

        return ResponseEntity.status(401).body("FORBIDDEN");
    }

    @GetMapping("/timeline")
    public ResponseEntity<List<TimeLineDTO>> timeLineUser(Authentication authentication) {
        User user = userService.get(authentication);

        List<LinkProfessionals> linkProfessionalsList = linkProfessionalsService.getAll(user);

        return ResponseEntity.ok(userService.timeLineProfessionals(linkProfessionalsList));
    }

    @PostMapping("/create/{employeeKey}")
    public ResponseEntity<Object> createProfFile(@PathVariable String employeeKey, @RequestPart("file") MultipartFile file,  @RequestParam("paramNewProfessionals") String paramNewProfessionalsJson, Authentication authentication) throws JsonProcessingException {
        User user = userService.get(authentication);
        Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

        if(employeeOptional.isPresent() && employeeOptional.get().getPermissions().isAddProf()) {
            ObjectMapper objectMapper = new ObjectMapper();
            ParamNewProfessionals paramNewProfessionals = objectMapper.readValue(paramNewProfessionalsJson, ParamNewProfessionals.class);

            String response = scannerFile.createProfessionals(file, paramNewProfessionals, user, employeeOptional.get());

            switch (response) {
                case "CREATE" -> {
                    return ResponseEntity.ok().build();
                }
                default -> {
                    return ResponseEntity.badRequest().body(response);
                }
            }
        }

        return ResponseEntity.status(401).body("FORBIDDEN");
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Object> updateProfessional(@PathVariable Long id, @RequestBody @Valid ParamUpdateProfessional paramUpdateProfessional) {
        ProfessionalComplete professionalComplete = professionalService.get(id);

        if(professionalComplete != null) {
            professionalService.updateAndSave(professionalComplete, paramUpdateProfessional);

            return ResponseEntity.ok().body(new ProfessionalDTO(professionalComplete));
        }

        return ResponseEntity.badRequest().body("NOT FOUND ID");
    }

    @Transactional
    @PostMapping("/delete/{employeeKey}")
    public ResponseEntity<Object> deleteMany(@PathVariable String employeeKey, @RequestBody List<Long> ids, Authentication authentication) {
        User user = userService.get(authentication);
        Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

        if(employeeOptional.isPresent() && employeeOptional.get().getPermissions().isDeleteProf()) {
            ids.forEach( id -> {
                LinkProfessionals link = linkProfessionalsService.get(id);

                professionalService.delete(link);

                userService.updateStorageAndSave(user, link.getFileSizeInBytes(), true);

                linkProfessionalsService.delete(link);

                stockHistoryService.register(ActionEmployee.DELETE_PROF.getAction(), ActionType.DELETE.getAction(), link.getDate(), 0, user, employeeOptional.get());
            });

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(401).body("FORBIDDEN");
    }

}
