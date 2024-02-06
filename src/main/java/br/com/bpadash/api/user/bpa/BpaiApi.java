package br.com.bpadash.api.user.bpa;

import br.com.bpadash.dto.bpa.BpaiDTO;
import br.com.bpadash.errorValidation.ErrorResponseDTO;
import br.com.bpadash.errorValidation.ErrorValidationDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.bpa.ParamDeleteBpai;
import br.com.bpadash.params.bpa.ParamUpdateBpai;
import br.com.bpadash.params.bpa.ParamUpdateErrorsBpa;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.cache.CacheService;
import br.com.bpadash.services.scanner.ScannerFile;
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
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bpai")
public class BpaiApi {

    @Autowired
    private BpaiService bpaiService;
    @Autowired
    private BpaService bpaService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private ScannerFile scannerFile;
    @Autowired
    private UserService userService;


    @GetMapping("/get/{month}/{year}")
    public ResponseEntity<Page<BpaiDTO>> getBpai(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable pageable,
            @PathVariable int month,
            @PathVariable int year,
            Authentication authentication
    ) {
        User user = userService.get(authentication);
        Bpa bpa = bpaService.getForDate(month, year, user);

        if(bpa == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Page<BpaiDTO> page = bpaiService.get(bpa, pageable);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/get/{identifier}")
    public ResponseEntity<Page<BpaiDTO>> getBpaiIdent(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable pageable,
            @PathVariable @Valid @NotBlank String identifier,
            Authentication authentication
    ) {
        User user = userService.get(authentication);
        Bpa bpa = bpaService.get(identifier, user);
        Page<BpaiDTO> page = bpaiService.get(bpa, pageable);

        return ResponseEntity.ok(page);
    }


    @PostMapping(value = "/create/{month}/{year}/{cacheId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> bpaCreate(@RequestPart("file") MultipartFile file, @PathVariable int month, @PathVariable int year, @PathVariable String cacheId, Authentication authentication) {

        List<ErrorsFile> errorsFiles = new ArrayList<>();
        try {
            User user = userService.get(authentication);
            LocalDate localDate = LocalDate.of(year, month, 1);
            Optional<Bpa> bpaOptional = bpaService.get(localDate, user);

            if(bpaOptional.isEmpty()) {
                errorsFiles.add(new ErrorsFile("NOT EXIST DATE"));

                return ResponseEntity.badRequest().body(errorsFiles);
            }

            Bpa bpa = bpaOptional.get();

            Bpai bpai = bpaiService.getLast(bpa);

            String response = scannerFile.createBpai(file, bpaOptional.get(), bpai, user, errorsFiles);

            switch (response) {
                case "ERROR FILE" -> {
                    return ResponseEntity.badRequest().body(errorsFiles);
                }
                case "FILE FULL" -> {
                    errorsFiles.add(new ErrorsFile("FILE FULL"));

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

            bpaService.updateStateManager(bpa, false);

            cacheService.evictAll(cacheId);

            return ResponseEntity.ok().build();

        } catch (StringIndexOutOfBoundsException | IllegalArgumentException e) {
            errorsFiles.add(new ErrorsFile("FILE INVALID"));

            return ResponseEntity.badRequest().body(errorsFiles);
        }
    }

    @PostMapping("/edit/{id}/{cacheId}")
    public ResponseEntity<List<ErrorValidationDTO>> editBpai(@PathVariable Long id, @PathVariable String cacheId, @RequestBody @Valid ParamUpdateBpai paramUpdateBpai) {
        try {
            List<ErrorValidationDTO> erros = new ArrayList<>();

            if(!paramUpdateBpai.getEtnia().trim().isEmpty()) {
                if(!paramUpdateBpai.getRaca().equals("05")) {
                    erros.add(new ErrorValidationDTO("etnia", "Preencher somente se o campo raça/cor for 05 - Indígena."));

                    return ResponseEntity.badRequest().body(erros);
                }
            }

            Optional<Bpai> optionalBpai = bpaiService.get(id);
            if(optionalBpai.isEmpty()) {
                erros.add(new ErrorValidationDTO("id", "O id não existe."));
                return ResponseEntity.badRequest().body(erros);
            }

            //TODO tem que atualizar o tamanho do arquivo
            bpaiService.editAndSave(optionalBpai.get(), paramUpdateBpai);

            bpaService.updateStateManager(optionalBpai.get().getBpa(), false);

            cacheService.evictAll(cacheId);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Atualiza o campo não nulo da requisição.
     * @param id
     * @param paramBpa
     * @return
     */
    @PostMapping("/update/{id}/{cacheId}")
    public ResponseEntity<List<ErrorValidationDTO>> updateBpai(@PathVariable Long id, @PathVariable String cacheId, @RequestBody @Valid ParamUpdateErrorsBpa paramBpa, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Bpa> bpaOptional = Optional.empty();
        List<ErrorValidationDTO> erros = new ArrayList<>();

        switch (paramBpa.getKey()) {
            case "cbo" , "cnsmedProfessional" , "birthDate", "pa" -> bpaOptional = bpaService.get(Utilities.formatDate(paramBpa.getDateBpa()), user);
            case "dateBpaInvalid" -> bpaOptional = bpaService.get(Utilities.formatDate(paramBpa.getDateBpaInvalid()), user);
        }


        Bpai bpai = null;
        if(id != 0) {
            Optional<Bpai> optionalBpai = bpaiService.get(id);
            if(optionalBpai.isEmpty()) {
                erros.add(new ErrorValidationDTO("ID", "O id não existe."));
                return ResponseEntity.badRequest().body(erros);
            }

            bpai = optionalBpai.get();
        }

        int count = bpaiService.editAndSave(bpai, paramBpa, bpaOptional.orElse(null), user);

        cacheService.evictAll(cacheId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete/{cacheId}")
    public ResponseEntity<Object> editBpai(@PathVariable String cacheId, @RequestBody @Valid ParamDeleteBpai paramDeleteBpai, Authentication authentication) {
        try {
            User user = userService.get(authentication);

            Bpai bpai = bpaiService.get(paramDeleteBpai.getList().get(0)).get();

            Long size = bpaiService.sizeByte(paramDeleteBpai.getList());

            bpaiService.deleteById(paramDeleteBpai.getList());

            bpaService.updatebyte(bpai.getBpa(), size, false);

            userService.updateStorageAndSave(user, size, true);

            bpaService.updateStateManager(bpai.getBpa(), true);

            cacheService.evictAll(cacheId);

            return ResponseEntity.ok(size);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO());
        }
    }

}



