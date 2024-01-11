package br.com.bpadash.api.user.sigtap;

import br.com.bpadash.dto.DatesDTO;
import br.com.bpadash.dto.professional.ProfessionalDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.errorValidation.ProfessionalErrorValidation;
import br.com.bpadash.model.*;
import br.com.bpadash.params.professional.ParamNewProfessional;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
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
    private DatesSigtapService datesSigtapService;


    @GetMapping("/get/{idProfessional}")
    public ResponseEntity<Object> getProfessionals(@PathVariable String idProfessional, Authentication authentication) {
        User user = userService.get(authentication);
        DatesSigtap datesSigtap = user.getDatesSigtap();

        Optional<LinkProfessionals> linkProfessionalsOptional;
        if(datesSigtap.isDateProfessionalsAuto()) {
            linkProfessionalsOptional = linkProfessionalsService.get();
        } else {
            linkProfessionalsOptional = linkProfessionalsService.get(datesSigtap.getDateProfessionals());
        }

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

}
