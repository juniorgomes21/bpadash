package br.com.bpadash.api.user.sigtap;

import br.com.bpadash.dto.sigtap.DatesSigtapDTO;
import br.com.bpadash.dto.sigtap.ProcedureDTO;
import br.com.bpadash.model.sigtap.DatesSigtap;
import br.com.bpadash.model.sigtap.LinkFpo;
import br.com.bpadash.model.sigtap.LinkProcedure;
import br.com.bpadash.model.sigtap.LinkProfessionals;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.sigtap.ParamUpdateDateSigtap;
import br.com.bpadash.services.fpo.LinkFpoService;
import br.com.bpadash.services.professional.LinkProfessionalsService;
import br.com.bpadash.services.sigtap.*;
import br.com.bpadash.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

        DatesSigtapDTO dateFpo = linkFpoService.getDates(datesSigtap, user);
        DatesSigtapDTO dateProf = linkProfessionalsService.getDates(datesSigtap, user);

        return ResponseEntity.ok(new ArrayList<>(List.of(dateFpo, dateProf)));
    }

    @PostMapping("/update/date")
    public ResponseEntity<Object> updateDate(@RequestBody @Valid ParamUpdateDateSigtap paramUpdateDateSigtap, Authentication authentication) {
        User user = userService.get(authentication);
        DatesSigtap datesSigtap = datesSigtapService.get(user);

        datesSigtapService.update(datesSigtap, paramUpdateDateSigtap);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/have/files")
    public ResponseEntity<Boolean> getProcedure(Authentication authentication) {
        User user = userService.get(authentication);

        boolean isPresentfpo = linkFpoService.haveFile(user);
        boolean isPresent = linkProfessionalsService.haveFile(user);

        return ResponseEntity.ok(isPresentfpo && isPresent);
    }
}
