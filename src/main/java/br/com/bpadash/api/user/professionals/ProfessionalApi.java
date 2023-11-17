package br.com.bpadash.api.user.professionals;

import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.errorValidation.ProfessionalErrorValidation;
import br.com.bpadash.model.Professional;
import br.com.bpadash.model.ProfessionalComplete;
import br.com.bpadash.model.User;
import br.com.bpadash.params.professional.ParamNewProfessional;
import br.com.bpadash.services.professional.ProfessionalService;
import br.com.bpadash.services.scanner.ScannerFile;
import br.com.bpadash.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@RestController
@Validated
@RequestMapping("/api/prof")
public class ProfessionalApi {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfessionalService professionalService;

    @Autowired
    private ScannerFile scannerFile;

    @PostMapping("/create")
    public ResponseEntity<ProfessionalErrorValidation> createProf(@RequestBody @Valid List<ParamNewProfessional> paramNewProfessionals, Authentication authentication) {
        try {
            User user = userService.userInDb(1L);

            if(user.getProfissionalNumberFree() < paramNewProfessionals.size()) {
                return ResponseEntity.badRequest().body(new ProfessionalErrorValidation("NOT STORAGE"));
            }

            List<Integer> professionalsInvalids = professionalService.exist(paramNewProfessionals);
            if(professionalsInvalids.size() > 0) {
                return ResponseEntity.badRequest().body(new ProfessionalErrorValidation("INVALID PROF", professionalsInvalids));
            }

            List<Professional> professionals = professionalService.create(paramNewProfessionals, user);

//            professionalService.save(professionals);
//            userService.addProfessionals(professionals, user);

            return ResponseEntity.ok().build();

        } catch (ConstraintViolationException e) {
            System.out.println("Erro de validação em add profissional");
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create/file")
    public ResponseEntity<List<ProfessionalComplete>> createProfFile(@RequestPart MultipartFile file, Authentication authentication) {
        User user = userService.userInDb(1L);

        List<ErrorsFile> errorsFileList = new ArrayList<>();

        String response = scannerFile.createProfessionals(file, user, errorsFileList);

        return ResponseEntity.ok().build();
    }
}
