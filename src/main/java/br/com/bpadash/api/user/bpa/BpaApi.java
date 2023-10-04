package br.com.bpadash.api.user.bpa;

import br.com.bpadash.dto.bpa.BpaDTO;
import br.com.bpadash.model.*;
import br.com.bpadash.params.bpa.ParamNewBpa;
import br.com.bpadash.params.bpa.ParamSearchDateBpa;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.ScannerFile;
import br.com.bpadash.services.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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

    @GetMapping("/get/{month}/{year}")
    public ResponseEntity<BpaDTO> getBpa(@PathVariable int month, @PathVariable int year, Authentication authentication) {
        User user = userService.userInDb(1L);
        Bpa bpa = bpaService.getForDate(month, year, user);

        if(bpa == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(new BpaDTO(bpa));
    }


    @PostMapping( value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BpaDTO> bpaCreate(@RequestPart("file") MultipartFile file, @RequestParam("paramNewBpa") String paramNewBpaJson, Authentication authentication) {
        try {
//            User user = userService.logged(authentication);
            User user = userService.userInDb(1L);
            ObjectMapper objectMapper = new ObjectMapper();
            ParamNewBpa paramNewBpa = objectMapper.readValue(paramNewBpaJson, ParamNewBpa.class);

            Bpa bpa = scannerFile.createBpa(file, user, paramNewBpa);

            return ResponseEntity.ok(new BpaDTO(bpa));

        } catch (StringIndexOutOfBoundsException e) {
            throw new StringIndexOutOfBoundsException("A estrutura do arquivo está incorreta o erro se encontra em " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("A estrutura do arquivo está incorreta: " + e.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
