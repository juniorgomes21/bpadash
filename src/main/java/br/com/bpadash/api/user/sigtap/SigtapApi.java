package br.com.bpadash.api.user.sigtap;

import br.com.bpadash.dto.sigtap.DatesSigtapDTO;
import br.com.bpadash.dto.sigtap.ProcedureDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.*;
import br.com.bpadash.params.sigtap.ParamNewCep;
import br.com.bpadash.params.sigtap.ParamNewOccupation;
import br.com.bpadash.params.sigtap.ParamNewProcedure;
import br.com.bpadash.params.sigtap.ParamUpdateDateSigtap;
import br.com.bpadash.services.fpo.FpoService;
import br.com.bpadash.services.fpo.LinkFpoService;
import br.com.bpadash.services.professional.LinkProfessionalsService;
import br.com.bpadash.services.professional.ProfessionalService;
import br.com.bpadash.services.scanner.ScannerFile;
import br.com.bpadash.services.sigtap.*;
import br.com.bpadash.services.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/sigtap")
public class SigtapApi {

    @Autowired
    private UserService userService;
    @Autowired
    private LinkProcedureService linkProcedureService;
    @Autowired
    private LinkOccupationService linkOccupationService;
    @Autowired
    private LinkFpoService linkFpoService;
    @Autowired
    private LinkProfessionalsService linkProfessionalsService;
    @Autowired
    private LinkCepService linkCepService;
    @Autowired
    private DatesSigtapService datesSigtapService;


    @GetMapping("/get/procedure")
    public ResponseEntity<ProcedureDTO> getProcedure() {

        LocalDate date = LocalDate.of(2023, 11, 1);
        Optional<LinkProcedure> linkProcedureOptional = linkProcedureService.get(date);
        if(linkProcedureOptional.isPresent()) {
            LinkProcedure linkProcedure = linkProcedureOptional.get();
            return ResponseEntity.ok(new ProcedureDTO(linkProcedure.getProcedureList().get(0)));
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/get/all/dates")
    public ResponseEntity<List<DatesSigtapDTO>> datesSigtap(Authentication authentication) {
        User user = userService.get(authentication);
        DatesSigtap datesSigtap = user.getDatesSigtap();

        DatesSigtapDTO dateOccupation = linkOccupationService.getDates(datesSigtap);
        DatesSigtapDTO dateFpo = linkFpoService.getDates(datesSigtap);
        DatesSigtapDTO dateProf = linkProfessionalsService.getDates(datesSigtap);
        DatesSigtapDTO dateCep = linkCepService.getDates(datesSigtap);
        DatesSigtapDTO dateProc = linkProcedureService.getDates(datesSigtap);

        return ResponseEntity.ok(new ArrayList<>(List.of(dateOccupation, dateFpo, dateProf, dateCep, dateProc)));
    }

    @PostMapping("/update/date")
    public ResponseEntity<Object> updateDate(@RequestBody @Valid ParamUpdateDateSigtap paramUpdateDateSigtap, Authentication authentication) {
        User user = userService.get(authentication);
        DatesSigtap datesSigtap = datesSigtapService.get(paramUpdateDateSigtap.getId(), user);

        datesSigtapService.update(datesSigtap, paramUpdateDateSigtap);

        return ResponseEntity.ok().build();
    }
}
