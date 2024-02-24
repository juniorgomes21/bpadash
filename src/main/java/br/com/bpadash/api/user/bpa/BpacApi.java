package br.com.bpadash.api.user.bpa;

import br.com.bpadash.errorValidation.ErrorResponseDTO;
import br.com.bpadash.dto.bpa.BpacDTO;
import br.com.bpadash.errorValidation.ErrorValidationDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpac;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.bpa.ParamDeleteBpac;
import br.com.bpadash.params.bpa.ParamUpdateBpac;
import br.com.bpadash.params.bpa.ParamUpdateErrorsBpa;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpacService;
import br.com.bpadash.services.cache.CacheService;
import br.com.bpadash.services.scanner.ScannerFile;
import br.com.bpadash.services.user.StorageService;
import br.com.bpadash.services.user.UserService;
import br.com.bpadash.utilities.Utilities;
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
    private CacheService cacheService;
    @Autowired
    private ScannerFile scannerFile;
    @Autowired
    private UserService userService;
    @Autowired
    private StorageService storageService;

    @GetMapping("/get/{date}")
    public ResponseEntity<Page<BpacDTO>> getBpacForDate(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable pageable,
            @PathVariable String date,
            Authentication authentication
    ) {
        User user = userService.get(authentication);
        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(date), user);

        if(bpaOptional.isPresent()) {
            Page<BpacDTO> page = bpacService.get(bpaOptional.get(), pageable);

            return ResponseEntity.ok(page);
        }

        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping( value = "/create/{month}/{year}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> bpaCreate(@RequestPart("file") MultipartFile file, @PathVariable int month, @PathVariable int year, Authentication authentication) {
        try {
            User user = userService.get(authentication);
            List<ErrorsFile> errorsFiles = new ArrayList<>();

            LocalDate localDate = LocalDate.of(year, month, 1);

            Optional<Bpa> bpaOptional = bpaService.get(localDate, user);
            if(bpaOptional.isEmpty()) {
                errorsFiles.add(new ErrorsFile("NOT STORAGE"));

                return ResponseEntity.badRequest().body(errorsFiles);
            }

            Bpa bpa = bpaOptional.get();

            Bpac bpac = bpacService.getLast(bpa);

            String response = scannerFile.createBpac(user, file, bpa, bpac, errorsFiles);

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

            bpaService.calculateAll(user, bpa, null, null, true);

            bpaService.updateStateManager(bpa, false);

            return ResponseEntity.ok().build();

        } catch (StringIndexOutOfBoundsException e) {
            throw new StringIndexOutOfBoundsException("A estrutura do arquivo está incorreta o erro se encontra em " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("A estrutura do arquivo está incorreta: " + e.getMessage());
        }
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Object> editBpac(@PathVariable Long id, @RequestBody @Valid ParamUpdateBpac paramUpdateBpac, Authentication authentication) {
        try {
            User user = userService.userLogged(authentication);

            Bpac bpac = bpacService.bpacId(id);

            String pa = bpac.getPa();
            String qt = bpac.getQt();

            bpacService.editAndSave(bpac, paramUpdateBpac);

            Bpa bpa = bpac.getBpa();

            if(!pa.equals(paramUpdateBpac.getPa()) || !qt.equals(paramUpdateBpac.getQt())){
                List<Bpac> bpacList = bpacService.get(bpa);

                bpaService.calculateInvoicing(bpa, null, bpacList, null, user, true);
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO());
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Object> updateBpac(@PathVariable Long id, @RequestBody @Valid ParamUpdateErrorsBpa paramBpa, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Bpac> optionalBpac = bpacService.get(id);
        if(optionalBpac.isEmpty()) {
            return ResponseEntity.badRequest().body("NOT FOUND ID");
        }

        Bpac bpac = optionalBpac.get();

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramBpa.getDateBpa()), user);

        List<ErrorValidationDTO> erros = new ArrayList<>();

        if(bpaOptional.isEmpty()) {
            erros.add(new ErrorValidationDTO("DATE BPA", "A data informada não existe."));

            return ResponseEntity.badRequest().body(erros);
        }

        Bpa bpa = bpaOptional.get();

        bpacService.editAndSave(bpac, paramBpa, bpa);

        String key = paramBpa.getKey();

        if (key.equals("pa")) {
            bpaService.calculateInvoicing(bpa , null , null , null , user , true);

            bpaService.save(bpa);
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete/{date}")
    public ResponseEntity<Object> deleteBpac(@PathVariable String date, @RequestBody @Valid ParamDeleteBpac paramDeleteBpac, Authentication authentication) {
        try {
            User user = userService.get(authentication);

            Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(date), user);

            if(bpaOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("NOT FOUND BPA");
            }

            Long size = bpacService.sizeByte(paramDeleteBpac.getList());

            if(size == null) {
                return ResponseEntity.ok().build();
            }

            try {
                bpacService.deleteByIds(paramDeleteBpac.getList());
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("ERROR");
            }

            Bpa bpa = bpaOptional.get();

            bpaService.calculateLineInTitle(bpa, 0, paramDeleteBpac.getList().size(), false);

            bpaService.calculateAll(user, bpa, null, null, true);

            storageService.updateBytesBpaAndUser(user, true, bpa, false, size);

            return ResponseEntity.ok(size);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO());
        }
    }

}
