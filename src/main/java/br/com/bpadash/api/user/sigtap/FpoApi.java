package br.com.bpadash.api.user.sigtap;

import br.com.bpadash.dto.DatesDTO;
import br.com.bpadash.dto.bpa.TimeLineDTO;
import br.com.bpadash.dto.professional.ProfessionalDTO;
import br.com.bpadash.dto.sigtap.FpoDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.sigtap.*;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.fpo.ParamNewFpo;
import br.com.bpadash.params.fpo.ParamNewLineFpo;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.fpo.FpoService;
import br.com.bpadash.services.fpo.LinkFpoService;
import br.com.bpadash.services.scanner.ScannerFile;
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

    @GetMapping("/get/{pa}")
    public ResponseEntity<Object> getProfessionals(@PathVariable String pa, Authentication authentication) {
        User user = userService.get(authentication);

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

    @GetMapping("/timeline")
    public ResponseEntity<List<TimeLineDTO>> timeLineUser(Authentication authentication) {
        User user = userService.get(authentication);

        List<LinkFpo> linkFpos = linkFpoService.getAll(user);

        return ResponseEntity.ok(userService.timeLineFpo(linkFpos));
    }

    @PostMapping("/create")
    public ResponseEntity<List<ErrorsFile>> createFpo(@RequestPart("file") MultipartFile file, @RequestParam("paramNewFpo") String paramNewBpaJson, Authentication authentication) throws JsonProcessingException {
        User user = userService.get(authentication);
        List<ErrorsFile> errorsFiles = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        ParamNewFpo paramNewFpo = objectMapper.readValue(paramNewBpaJson, ParamNewFpo.class);

        String response = scannerFile.createFpo(file, paramNewFpo, errorsFiles, user);

        switch (response) {
            case "FILE INVALID" -> {
                errorsFiles.add(new ErrorsFile("FILE INVALID"));

                return ResponseEntity.badRequest().body(errorsFiles);
            }
            case "ERROR FILE" -> {
                return ResponseEntity.badRequest().body(errorsFiles);
            }
            case "NOT STORAGE" -> {
                errorsFiles.add(new ErrorsFile("NOT STORAGE"));

                return ResponseEntity.badRequest().body(errorsFiles);
            }
            case "EXIST DATE" -> {
                errorsFiles.add(new ErrorsFile("EXIST DATE"));

                return ResponseEntity.badRequest().body(errorsFiles);
            }
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/create/line")
    public ResponseEntity<Object> createLine(@RequestBody @Valid ParamNewLineFpo paramNewLineFpo) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/dates") //TODO concluido
    public ResponseEntity<DatesDTO> dates(Authentication authentication) {
        User user = userService.get(authentication);

        DatesDTO datesDTO = linkFpoService.getDates(user);

        return ResponseEntity.ok(datesDTO);
    }

    @Transactional
    @PostMapping("/delete")
    public ResponseEntity<Object> deleteFpo(@RequestBody List<Long> ids, Authentication authentication) {

        User user = userService.userLogged(authentication);

        ids.forEach( id -> {
            LinkFpo link = linkFpoService.get(id);

            fpoService.delete(link);

            userService.updateStorageAndSave(user, link.getFileSizeInBytes(), "add");

            linkFpoService.delete(link);
        });

        return ResponseEntity.ok().build();
    }
}
