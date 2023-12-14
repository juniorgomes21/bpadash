package br.com.bpadash.api.user.sigtap;

import br.com.bpadash.dto.DatesDTO;
import br.com.bpadash.dto.professional.ProfessionalDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.errorValidation.ProfessionalErrorValidation;
import br.com.bpadash.model.LinkProfessionals;
import br.com.bpadash.model.Professional;
import br.com.bpadash.model.ProfessionalComplete;
import br.com.bpadash.model.User;
import br.com.bpadash.params.fpo.ParamNewFpo;
import br.com.bpadash.params.professional.ParamIdProfessional;
import br.com.bpadash.params.professional.ParamNewProfessional;
import br.com.bpadash.params.professional.ParamNewProfessionals;
import br.com.bpadash.params.professional.ParamUpdateProfessional;
import br.com.bpadash.projections.DateProjection;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.professional.LinkProfessionalsService;
import br.com.bpadash.services.professional.ProfessionalService;
import br.com.bpadash.services.scanner.ScannerFile;
import br.com.bpadash.services.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@Validated
@RequestMapping("/api/prof")
public class ProfessionalApi {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfessionalService professionalService;
    @Autowired
    private LinkProfessionalsService linkProfessionalsService;
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

    @PostMapping("/create/file") //TODO concluído
    public ResponseEntity<List<ErrorsFile>> createProfFile(@RequestPart("file") MultipartFile file,  @RequestParam("paramNewProfessionals") String paramNewProfessionalsJson, Authentication authentication) throws JsonProcessingException {
        User user = userService.userInDb(1L);

        ObjectMapper objectMapper = new ObjectMapper();
        ParamNewProfessionals paramNewProfessionals = objectMapper.readValue(paramNewProfessionalsJson, ParamNewProfessionals.class);

        List<ErrorsFile> errorsFileList = new ArrayList<>();

        String response = scannerFile.createProfessionals(file, user, paramNewProfessionals);

        switch (response) {
            case "NOT STORAGE" -> {
                errorsFileList.add(new ErrorsFile("NOT STORAGE"));

                return ResponseEntity.badRequest().body(errorsFileList);
            }
            case "EXIST DATE" -> {
                errorsFileList.add(new ErrorsFile("EXIST DATE"));

                return ResponseEntity.badRequest().body(errorsFileList);
            }
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{month}/{year}/{idProfessional}")
    public ResponseEntity<Object> getProfessionals(@PathVariable int month, @PathVariable int year, @PathVariable String idProfessional, Authentication authentication) {
        User user = userService.userInDb(1L);
        LocalDate date = LocalDate.of(year, month, 1);

        Optional<LinkProfessionals> linkProfessionalsOptional = linkProfessionalsService.get(date, user);

        if(linkProfessionalsOptional.isPresent()) {
            LinkProfessionals linkProfessionals = linkProfessionalsOptional.get();

            ProfessionalComplete professionalComplete = professionalService.get(linkProfessionals, idProfessional);

            if(professionalComplete == null) {
                return ResponseEntity.badRequest().body("NOT FOUND PROFESSIONAL");
            }

            EncryptionService.decrypt(new ArrayList<>(List.of(professionalComplete)));

            return ResponseEntity.ok(new ProfessionalDTO(professionalComplete));
        }

        return ResponseEntity.badRequest().body("NOT EXIST DATE PROFESSIONALS");
    }

    @GetMapping("/dates")
    public ResponseEntity<DatesDTO> getDatesProfessionals(Authentication authentication) {
        User user = userService.userInDb(1L);

        return ResponseEntity.ok(linkProfessionalsService.getDates(user));
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateProfessional(@RequestBody @Valid ParamUpdateProfessional paramUpdateProfessional) {
        ProfessionalComplete professionalComplete = professionalService.get(paramUpdateProfessional.getId());

        if(professionalComplete != null) {
            professionalService.updateAndSave(professionalComplete, paramUpdateProfessional);

            return ResponseEntity.ok().body(new ProfessionalDTO(professionalComplete));
        }

        return ResponseEntity.badRequest().body("NOT FOUND ID");
    }
}
