package br.com.bpadash.api.user.bpa;

import br.com.bpadash.errorValidation.ErrorResponseDTO;
import br.com.bpadash.dto.bpa.BpacDTO;
import br.com.bpadash.errorValidation.ErrorValidationDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.Bpa;
import br.com.bpadash.model.Bpac;
import br.com.bpadash.model.Bpai;
import br.com.bpadash.model.User;
import br.com.bpadash.params.bpa.ParamDeleteBpac;
import br.com.bpadash.params.bpa.ParamUpdateBpac;
import br.com.bpadash.params.bpa.ParamUpdateErrorsBpa;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpacService;
import br.com.bpadash.services.scanner.ScannerFile;
import br.com.bpadash.services.user.UserService;
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
            User user = userService.userInDb(1L);
            List<ErrorsFile> errorsFiles = new ArrayList<>();

            LocalDate localDate = LocalDate.of(year, month, 1);

            Optional<Bpa> bpaOptional = bpaService.get(localDate, user);
            if(bpaOptional.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }


            String response = scannerFile.createBpac(user, file, bpaOptional.get(), errorsFiles);

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

            bpacService.editAndSave(bpac, paramUpdateBpac);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO());
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<String> editBpac(@PathVariable Long id, @RequestBody @Valid ParamUpdateErrorsBpa paramBpa) {

        Optional<Bpac> optionalBpac = bpacService.get(id);
        if(optionalBpac.isEmpty()) {
            return ResponseEntity.badRequest().body("O id não existe.");
        }

        Bpac bpac = optionalBpac.get();
        if(bpac.getPa().equals(paramBpa.getPa())) {
            return ResponseEntity.ok().build();
        }

        bpacService.editAndSave(bpac, paramBpa);

        return ResponseEntity.ok().build();
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
