package br.com.bpadash.api.user.bpa;

import br.com.bpadash.dto.bpa.BpaDTO;
import br.com.bpadash.dto.error.ErrorValidationDTO;
import br.com.bpadash.dto.error.ErrorsFile;
import br.com.bpadash.model.*;
import br.com.bpadash.params.bpa.ParamNewBpa;
import br.com.bpadash.services.bpa.*;
import br.com.bpadash.services.user.StorageService;
import br.com.bpadash.services.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/bpa")
public class BpaApi {

    @Autowired
    private ScannerFile scannerFile;

    @Autowired
    private BpaService bpaService;

    @Autowired
    private UserService userService;

    @GetMapping("/get/all")
    public ResponseEntity<List<BpaDTO>> getAll(Authentication authentication) {
        User user = userService.userInDb(1L);

        List<BpaDTO> bpaDTOList = bpaService.getAll(user);

        return ResponseEntity.ok(bpaDTOList);
    }

    @GetMapping("/get/{month}/{year}")
    public ResponseEntity<BpaDTO> getBpa(@PathVariable int month, @PathVariable int year, Authentication authentication) {
        User user = userService.userInDb(1L);
        Bpa bpa = bpaService.getForDate(month, year, user);

        if(bpa == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(new BpaDTO(bpa));
    }

    @GetMapping("/generateFile/{identifier}")
    public ResponseEntity<byte[]> generateTextFile(@PathVariable String identifier, Authentication authentication) {
        try {
            User user = userService.userInDb(1L);
            Bpa bpa = bpaService.get(identifier, user);

            // Crie um objeto StringBuilder para construir o conteúdo do arquivo
            StringBuilder fileContent = bpaService.createFile(bpa);

            // Converta o conteúdo para um array de bytes
            byte[] fileBytes = fileContent.toString().getBytes();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "arquivo.txt");

            return ResponseEntity.ok().headers(headers).body(fileBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> deleteMany(@RequestBody List<String> identifiers, Authentication authentication) {
        User user = userService.userInDb(1L);

        identifiers.forEach(identifier -> {
            Bpa bpa = bpaService.get(identifier, user);
            bpaService.delete(bpa, user);
        });


        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete/{identifier}")
    public ResponseEntity<Object> deleteBPA(@PathVariable String identifier, Authentication authentication) {
        User user = userService.userInDb(1L);
        Bpa bpa = bpaService.get(identifier, user);

        bpaService.delete(bpa, user);

        return ResponseEntity.ok().build();
    }

    @PostMapping( value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ErrorsFile>> bpaCreate(@RequestPart("file") MultipartFile file, @RequestParam("paramNewBpa") String paramNewBpaJson, Authentication authentication) {
        try {
            User user = userService.userInDb(1L);

            ObjectMapper objectMapper = new ObjectMapper();
            ParamNewBpa paramNewBpa = objectMapper.readValue(paramNewBpaJson, ParamNewBpa.class);

            List<ErrorsFile> errorsFileList = new ArrayList<>();
            List<ErrorValidationDTO> errors = new ArrayList<>();

            String response = scannerFile.createBpa(file, user, paramNewBpa, errorsFileList);
            switch (response) {
                case "ERROR FILE" -> {
                    return ResponseEntity.badRequest().body(errorsFileList);
                }
                case "NOT STORAGE" -> {
                    errors.add(new ErrorValidationDTO("ARMAZENAMENTO" , "Espaço de armazenamento insuficiente."));
                    errorsFileList.add(new ErrorsFile(String.valueOf(0) , errors));

                    return ResponseEntity.badRequest().body(errorsFileList);
                }
                case "EXIST DATE" -> {
                    errors.add(new ErrorValidationDTO("EXIST DATE" , "A data do arquivo já existe."));
                    errorsFileList.add(new ErrorsFile(String.valueOf(0) , errors));

                    return ResponseEntity.badRequest().body(errorsFileList);
                }
            }

            return ResponseEntity.ok().build();

        } catch (StringIndexOutOfBoundsException e) {
            throw new StringIndexOutOfBoundsException("A estrutura do arquivo está incorreta o erro se encontra em " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("A estrutura do arquivo está incorreta: " + e.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
