package br.com.bpadash.api.user.fpo;

import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.dto.DatesDTO;
import br.com.bpadash.model.User;
import br.com.bpadash.params.fpo.ParamNewFpo;
import br.com.bpadash.params.fpo.ParamNewLineFpo;
import br.com.bpadash.services.scanner.ScannerFile;
import br.com.bpadash.services.fpo.FpoService;
import br.com.bpadash.services.fpo.LinkFpoService;
import br.com.bpadash.services.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/fpo")
public class FpoAPi {

    @Autowired
    private ScannerFile scannerFile;

    @Autowired
    private UserService userService;

    @Autowired
    private FpoService fpoService;

    @Autowired
    private LinkFpoService linkFpoService;

    @PostMapping("/create")
    public ResponseEntity<List<ErrorsFile>> createFpo(@RequestPart("file") MultipartFile file, @RequestParam("paramNewFpo") String paramNewBpaJson, Authentication authentication) throws JsonProcessingException {
        User user = userService.userInDb(1L);
        List<ErrorsFile> errorsFiles = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        ParamNewFpo paramNewFpo = objectMapper.readValue(paramNewBpaJson, ParamNewFpo.class);

        String response = scannerFile.createFpo(file, paramNewFpo, user, errorsFiles);

        switch (response) {
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
        User user = userService.userInDb(1L);

        DatesDTO datesDTO = linkFpoService.getDates(user);

        return ResponseEntity.ok(datesDTO);
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> delete() {

        fpoService.deleteAll();

        return ResponseEntity.ok().build();
    }
}
