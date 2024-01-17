package br.com.bpadash.api.adm.sigtap;

import br.com.bpadash.dto.professional.ProfessionalDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.ProfessionalComplete;
import br.com.bpadash.model.User;
import br.com.bpadash.params.fpo.ParamNewFpo;
import br.com.bpadash.params.professional.ParamNewProfessionals;
import br.com.bpadash.params.professional.ParamUpdateProfessional;
import br.com.bpadash.params.sigtap.ParamNewCep;
import br.com.bpadash.params.sigtap.ParamNewOccupation;
import br.com.bpadash.params.sigtap.ParamNewProcedure;
import br.com.bpadash.services.fpo.FpoService;
import br.com.bpadash.services.professional.ProfessionalService;
import br.com.bpadash.services.scanner.ScannerFile;
import br.com.bpadash.services.sigtap.CepService;
import br.com.bpadash.services.sigtap.ProcedureService;
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
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/adm/sigtap")
public class SigtapAdmApi {

    @Autowired
    private ScannerFile scannerFile;
    @Autowired
    private ProfessionalService professionalService;


    //CEP


    @PostMapping(value = "/create/cep", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createCep(@RequestPart("file") MultipartFile file, @RequestParam("paramNewCep") String paramNewCepJson, Authentication authentication) throws JsonProcessingException {
        StopWatch stopWatch = StopWatch.createStarted();

        ObjectMapper objectMapper = new ObjectMapper();
        ParamNewCep paramNewCep = objectMapper.readValue(paramNewCepJson, ParamNewCep.class);

        List<ErrorsFile> errorsFiles = new ArrayList<>();

        String response = scannerFile.createCep(file, paramNewCep, errorsFiles, stopWatch);

        if (!response.equals("CREATE"))  {

            if(response.equals("ERROR FILE")) {
                return ResponseEntity.badRequest().body(errorsFiles);
            }

            errorsFiles.add(new ErrorsFile(response));

            return ResponseEntity.badRequest().body(errorsFiles);
        }

        return ResponseEntity.ok(response);
    }


    //OCCUPATION

    @PostMapping(value = "/create/occupation", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createOccupation(@RequestPart("file") MultipartFile file, @RequestParam("paramNewOccupation") String paramNewOccupationJson, Authentication authentication) throws JsonProcessingException {
        List<ErrorsFile> errorsFiles = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        ParamNewOccupation paramNewOccupation = objectMapper.readValue(paramNewOccupationJson, ParamNewOccupation.class);

        String response = scannerFile.createOccupation(file, paramNewOccupation, errorsFiles);

        if (!response.equals("CREATE"))  {

            if(response.equals("ERROR FILE")) {
                return ResponseEntity.badRequest().body(errorsFiles);
            }

            errorsFiles.add(new ErrorsFile(response));

            return ResponseEntity.badRequest().body(errorsFiles);
        }

        return ResponseEntity.ok(response);
    }

    //PROCEDURE

    @PostMapping(value = "/create/procedure", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createProcedure(@RequestPart("file") MultipartFile file, @RequestParam("paramNewProcedure") String paramNewProcedureJson, Authentication authentication) throws IOException {
        List<ErrorsFile> errorsFiles = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        ParamNewProcedure paramNewProcedure = objectMapper.readValue(paramNewProcedureJson, ParamNewProcedure.class);

        String response = scannerFile.createProcedure(file, errorsFiles, paramNewProcedure);

        if (!response.equals("CREATE"))  {

            if(response.equals("ERROR FILE")) {
                return ResponseEntity.badRequest().body(errorsFiles);
            }

            errorsFiles.add(new ErrorsFile(response));

            return ResponseEntity.badRequest().body(errorsFiles);
        }

        return ResponseEntity.ok(response);
    }


    // PROFESSIONALS

    @PostMapping("/create/professionals")
    public ResponseEntity<List<ErrorsFile>> createProfFile(@RequestPart("file") MultipartFile file,  @RequestParam("paramNewProfessionals") String paramNewProfessionalsJson, Authentication authentication) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ParamNewProfessionals paramNewProfessionals = objectMapper.readValue(paramNewProfessionalsJson, ParamNewProfessionals.class);

        List<ErrorsFile> errorsFileList = new ArrayList<>();

        String response = scannerFile.createProfessionals(file, paramNewProfessionals);

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

    @PostMapping("/update")
    public ResponseEntity<Object> updateProfessional(@RequestBody @Valid ParamUpdateProfessional paramUpdateProfessional) {
        ProfessionalComplete professionalComplete = professionalService.get(paramUpdateProfessional.getId());

        if(professionalComplete != null) {
            professionalService.updateAndSave(professionalComplete, paramUpdateProfessional);

            return ResponseEntity.ok().body(new ProfessionalDTO(professionalComplete));
        }

        return ResponseEntity.badRequest().body("NOT FOUND ID");
    }

    // FPO

    @PostMapping("/create")
    public ResponseEntity<List<ErrorsFile>> createFpo(@RequestPart("file") MultipartFile file, @RequestParam("paramNewFpo") String paramNewBpaJson) throws JsonProcessingException {
        List<ErrorsFile> errorsFiles = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        ParamNewFpo paramNewFpo = objectMapper.readValue(paramNewBpaJson, ParamNewFpo.class);

        String response = scannerFile.createFpo(file, paramNewFpo, errorsFiles);

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

}
