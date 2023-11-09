package br.com.bpadash.api.user.bpa;

import br.com.bpadash.dto.bpa.BpaDTO;
import br.com.bpadash.dto.error.ErrorResponseDTO;
import br.com.bpadash.dto.bpa.BpacDTO;
import br.com.bpadash.dto.error.ErrorsFile;
import br.com.bpadash.model.Bpa;
import br.com.bpadash.model.Bpac;
import br.com.bpadash.model.User;
import br.com.bpadash.params.bpa.ParamDeleteBpac;
import br.com.bpadash.params.bpa.ParamNewBpa;
import br.com.bpadash.params.bpa.ParamUpdateBpac;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpacService;
import br.com.bpadash.services.bpa.ScannerFile;
import br.com.bpadash.services.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bpac")
public class BpacApi {

    @Autowired
    private BpacService bpacService;

    @Autowired
    private BpaService bpaService;

    @Autowired
    private ScannerFile scannerFile;

    @Autowired
    private UserService userService;

    @GetMapping("/get/{identifier}")
    public ResponseEntity<Page<BpacDTO>> getBpacForIndentifier(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable pageable,
            @PathVariable @Valid @NotBlank String identifier,
            Authentication authentication
    ) {
        User user = userService.userInDb(1L);
        Bpa bpa = bpaService.get(identifier, user);
        Page<BpacDTO> page = bpacService.get(bpa, pageable);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/get/{month}/{year}")
    public ResponseEntity<Page<BpacDTO>> getBpacForDate(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable pageable,
            @PathVariable int month,
            @PathVariable int year,
            Authentication authentication
    ) {
        User user = userService.userInDb(1L);
        Bpa bpa = bpaService.getForDate(month, year, user);

        if(bpa == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Page<BpacDTO> page = bpacService.get(bpa, pageable);

        return ResponseEntity.ok(page);
    }

    @PostMapping( value = "/create/{month}/{year}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ErrorsFile>> bpaCreate(@RequestPart("file") MultipartFile file, @PathVariable int month, @PathVariable int year, Authentication authentication) {
        try {
//            User user = userService.logged(authentication);
            User user = userService.userInDb(1L);
            List<ErrorsFile> errorsFileList = new ArrayList<>();

            LocalDate localDate = LocalDate.of(year, month, 1);

            Optional<Bpa> bpaOptional = bpaService.get(localDate, user);
            if(bpaOptional.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }


            Bpa bpa = scannerFile.createBpac(user, file, bpaOptional.get(), errorsFileList);

            if(bpa == null) {
                return ResponseEntity.badRequest().body(errorsFileList);
            }

            return ResponseEntity.ok().build();

        } catch (StringIndexOutOfBoundsException e) {
            throw new StringIndexOutOfBoundsException("A estrutura do arquivo está incorreta o erro se encontra em " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("A estrutura do arquivo está incorreta: " + e.getMessage());
        }
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Object> editBpac(@PathVariable Long id, @RequestBody @Valid ParamUpdateBpac paramUpdateBpac) {
        try {
            Bpac bpac = bpacService.bpacId(id);

            Bpac bpacUpdated = bpacService.edit(bpac, paramUpdateBpac);

            bpacService.save(bpacUpdated);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> editBpac(@RequestBody @Valid ParamDeleteBpac paramDeleteBpac, Authentication authentication) {
        try {
            Bpac bpac = bpacService.bpacId(paramDeleteBpac.getList().get(0));

            Long size = bpacService.sizeByte(paramDeleteBpac.getList());

            bpacService.delete(paramDeleteBpac.getList());
            bpaService.updatebyte(bpac.getBpa(), size, false);

            return ResponseEntity.ok(size);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO());
        }
    }
}
