package br.com.bpadash.api.user.sigtap;

import br.com.bpadash.dto.DatesDTO;
import br.com.bpadash.dto.sigtap.DatesSigtapDTO;
import br.com.bpadash.dto.sigtap.ProcedureDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.*;
import br.com.bpadash.params.bpa.ParamNewBpa;
import br.com.bpadash.params.sigtap.ParamNewCep;
import br.com.bpadash.params.sigtap.ParamNewOccupation;
import br.com.bpadash.params.sigtap.ParamNewProcedure;
import br.com.bpadash.params.sigtap.ParamUpdateDateSigtap;
import br.com.bpadash.projections.DateProjection;
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
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/sigtap")
public class SigtapApi {

    @Autowired
    private UserService userService;
    @Autowired
    private ScannerFile scannerFile;
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
    private FpoService fpoService;
    @Autowired
    private ProfessionalService professionalService;
    @Autowired
    private CepService cepService;
    @Autowired
    private ProcedureService procedureService;
    @Autowired
    private DatesSigtapService datesSigtapService;


    @PostMapping(value = "/create/cep", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createCep(@RequestPart("file") MultipartFile file, @RequestParam("paramNewCep") String paramNewCepJson, Authentication authentication) throws JsonProcessingException {
        StopWatch stopWatch = StopWatch.createStarted();
        User user = userService.userInDb(1L);

        ObjectMapper objectMapper = new ObjectMapper();
        ParamNewCep paramNewCep = objectMapper.readValue(paramNewCepJson, ParamNewCep.class);

        List<ErrorsFile> errorsFiles = new ArrayList<>();

        String response = scannerFile.createCep(file, user, paramNewCep, errorsFiles, stopWatch);

        if (!response.equals("CREATE"))  {

            if(response.equals("ERROR FILE")) {
                return ResponseEntity.badRequest().body(errorsFiles);
            }

            errorsFiles.add(new ErrorsFile(response));

            return ResponseEntity.badRequest().body(errorsFiles);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/create/occupation", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createOccupation(@RequestPart("file") MultipartFile file, @RequestParam("paramNewOccupation") String paramNewOccupationJson, Authentication authentication) throws JsonProcessingException {
        User user = userService.userInDb(1L);
        List<ErrorsFile> errorsFiles = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        ParamNewOccupation paramNewOccupation = objectMapper.readValue(paramNewOccupationJson, ParamNewOccupation.class);

        String response = scannerFile.createOccupation(file, user, paramNewOccupation, errorsFiles);

        if (!response.equals("CREATE"))  {

            if(response.equals("ERROR FILE")) {
                return ResponseEntity.badRequest().body(errorsFiles);
            }

            errorsFiles.add(new ErrorsFile(response));

            return ResponseEntity.badRequest().body(errorsFiles);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/create/procedure", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createProcedure(@RequestPart("file")MultipartFile file, @RequestParam("paramNewProcedure") String paramNewProcedureJson, Authentication authentication) throws IOException {
        User user = userService.userInDb(1L);
        List<ErrorsFile> errorsFiles = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        ParamNewProcedure paramNewProcedure = objectMapper.readValue(paramNewProcedureJson, ParamNewProcedure.class);

        String response = scannerFile.createProcedure(file, user, errorsFiles, paramNewProcedure);

        if (!response.equals("CREATE"))  {

            if(response.equals("ERROR FILE")) {
                return ResponseEntity.badRequest().body(errorsFiles);
            }

            errorsFiles.add(new ErrorsFile(response));

            return ResponseEntity.badRequest().body(errorsFiles);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/procedure")
    public ResponseEntity<ProcedureDTO> getProcedure(Authentication authentication) {
        User user = userService.userInDb(1L);

        LocalDate date = LocalDate.of(2023, 11, 1);
        Optional<LinkProcedure> linkProcedureOptional = linkProcedureService.get(date, user);
        if(linkProcedureOptional.isPresent()) {
            LinkProcedure linkProcedure = linkProcedureOptional.get();
            return ResponseEntity.ok(new ProcedureDTO(linkProcedure.getProcedureList().get(0)));
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/get/all/dates")
    public ResponseEntity<List<DatesSigtapDTO>> datesSigtap(Authentication authentication) {
        User user = userService.userInDb(1L);
        DatesSigtap datesSigtap = datesSigtapService.get(user);

        DatesSigtapDTO dateOccupation = linkOccupationService.getDates(user, datesSigtap);
        DatesSigtapDTO dateFpo = linkFpoService.getDates(user, datesSigtap);
        DatesSigtapDTO dateProf = linkProfessionalsService.getDates(user, datesSigtap);
        DatesSigtapDTO dateCep = linkCepService.getDates(user, datesSigtap);
        DatesSigtapDTO dateProc = linkProcedureService.getDates(user, datesSigtap);

        return ResponseEntity.ok(new ArrayList<>(List.of(dateOccupation, dateFpo, dateProf, dateCep, dateProc)));
    }

    @PostMapping("/update/date")
    public ResponseEntity<Object> updateDate(@RequestBody @Valid ParamUpdateDateSigtap paramUpdateDateSigtap, Authentication authentication) {
        User user = userService.userInDb(1L);
        DatesSigtap datesSigtap = datesSigtapService.get(paramUpdateDateSigtap.getId(), user);

        datesSigtapService.update(datesSigtap, paramUpdateDateSigtap);

        return ResponseEntity.ok().build();
    }
}
