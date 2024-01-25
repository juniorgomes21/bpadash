package br.com.bpadash.api.user.sigtap;

import br.com.bpadash.dto.bpa.TimeLineDTO;
import br.com.bpadash.dto.professional.ProfessionalDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.sigtap.DatesSigtap;
import br.com.bpadash.model.sigtap.LinkProfessionals;
import br.com.bpadash.model.sigtap.ProfessionalComplete;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.professional.ParamNewProfessionals;
import br.com.bpadash.params.professional.ParamUpdateProfessional;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.professional.LinkProfessionalsService;
import br.com.bpadash.services.professional.ProfessionalService;
import br.com.bpadash.services.scanner.ScannerFile;
import br.com.bpadash.services.sigtap.DatesSigtapService;
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


    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getProfessionals(@PathVariable String id, Authentication authentication) {
        User user = userService.get(authentication);

        DatesSigtap datesSigtap = user.getDatesSigtap();

        Optional<LinkProfessionals> linkProfessionalsOptional;
        if(datesSigtap.isDateProfessionalsAuto()) {
            linkProfessionalsOptional = linkProfessionalsService.get(user);
        } else {
            linkProfessionalsOptional = linkProfessionalsService.get(datesSigtap.getDateProfessionals(), user);
        }

        if(linkProfessionalsOptional.isPresent()) {
            LinkProfessionals linkProfessionals = linkProfessionalsOptional.get();

            ProfessionalComplete professionalComplete = professionalService.get(linkProfessionals, id);

            if(professionalComplete == null) {
                return ResponseEntity.badRequest().body("NOT FOUND PROFESSIONAL");
            }

            EncryptionService.decrypt(new ArrayList<>(List.of(professionalComplete)));

            return ResponseEntity.ok(new ProfessionalDTO(professionalComplete));
        }

        return ResponseEntity.badRequest().body("NOT EXIST DATE PROFESSIONALS");
    }

    @GetMapping("/timeline")
    public ResponseEntity<List<TimeLineDTO>> timeLineUser(Authentication authentication) {
        User user = userService.get(authentication);

        List<LinkProfessionals> linkProfessionalsList = linkProfessionalsService.getAll(user);

        return ResponseEntity.ok(userService.timeLineProfessionals(linkProfessionalsList));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createProfFile(@RequestPart("file") MultipartFile file,  @RequestParam("paramNewProfessionals") String paramNewProfessionalsJson, Authentication authentication) throws JsonProcessingException {
        User user = userService.get(authentication);

        ObjectMapper objectMapper = new ObjectMapper();
        ParamNewProfessionals paramNewProfessionals = objectMapper.readValue(paramNewProfessionalsJson, ParamNewProfessionals.class);

        List<ErrorsFile> errorsFileList = new ArrayList<>();

        String response = scannerFile.createProfessionals(file, paramNewProfessionals, user);

        switch (response) {
            case "ERROR" -> {
                return ResponseEntity.badRequest().body("FILE INVALID");
            }
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
    @PostMapping("/delete")
    public ResponseEntity<Object> deleteMany(@RequestBody List<Long> ids, Authentication authentication) {
        User user = userService.userLogged(authentication);

        ids.forEach( id -> {
            LinkProfessionals link = linkProfessionalsService.get(id);

            professionalService.delete(link);

            userService.updateStorageAndSave(user, link.getFileSizeInBytes(), "add");

            linkProfessionalsService.delete(link);
        });


        return ResponseEntity.ok().build();
    }

}
