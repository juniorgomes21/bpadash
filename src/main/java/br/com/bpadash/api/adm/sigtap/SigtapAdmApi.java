package br.com.bpadash.api.adm.sigtap;

import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.params.fpo.ParamNewFpo;
import br.com.bpadash.params.sigtap.ParamNewCep;
import br.com.bpadash.params.sigtap.ParamNewOccupation;
import br.com.bpadash.params.sigtap.ParamNewProcedure;
import br.com.bpadash.services.professional.ProfessionalService;
import br.com.bpadash.services.scanner.ScannerFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

}
