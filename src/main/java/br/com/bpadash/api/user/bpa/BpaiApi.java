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
import br.com.bpadash.services.EncryptionService;
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


    @GetMapping("/get/{date}")
    public ResponseEntity<Page<BpaiDTO>> getBpai(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable pageable,
            @PathVariable String date,
            Authentication authentication
    ) {
        User user = userService.get(authentication);
        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(date), user);

        if(bpaOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        Page<BpaiDTO> page = bpaiService.get(bpaOptional.get(), pageable);

        return ResponseEntity.ok(page);
    }

    @PostMapping(value = "/create/{month}/{year}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> bpaCreate(@RequestPart("file") MultipartFile file, @PathVariable int month, @PathVariable int year, Authentication authentication) {

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

            return ResponseEntity.ok().build();

        } catch (StringIndexOutOfBoundsException | IllegalArgumentException e) {
            errorsFiles.add(new ErrorsFile("FILE INVALID"));

            return ResponseEntity.badRequest().body(errorsFiles);
        }
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<List<ErrorValidationDTO>> editBpai(@PathVariable Long id, @RequestBody @Valid ParamUpdateBpai paramUpdateBpai, Authentication authentication) {
        try {
            User user = userService.get(authentication);

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

            Bpai bpai = optionalBpai.get();

            EncryptionService.decryptBpai(List.of(bpai), false);

            Bpa bpa = bpai.getBpa();

            String qt = bpai.getQt();
            String pa = bpai.getPa();
            String sex = bpai.getSexo();
            String age = bpai.getIdade();
            String race = bpai.getRaca();

            //TODO tem que atualizar o tamanho do arquivo
            bpaiService.editAndSave(bpai, paramUpdateBpai);

            List<Bpai> bpaiList = new ArrayList<>();

            boolean paDiff = !pa.equals(paramUpdateBpai.getPa());
            boolean qtDiff = !qt.equals(paramUpdateBpai.getQt());
            boolean sexDiff = !sex.equals(paramUpdateBpai.getSexo());
            boolean ageDiff = !age.equals(paramUpdateBpai.getIdade());
            boolean raceDiff = !race.equals(paramUpdateBpai.getRaca());

            if( paDiff || qtDiff || sexDiff || raceDiff || ageDiff ) {
                bpaiList = bpaiService.get(bpa);
            }

            if(sexDiff || raceDiff || ageDiff) {
                EncryptionService.decryptAgeAndSexAndRace(bpaiList);
            }

            if(paDiff || qtDiff){
                bpaService.calculateInvoicing(bpa, null, null, bpaiList, user, true);
            }

            if(sexDiff) {
                bpaService.calculateSex(bpa, bpaiList);
            }

            if(raceDiff){
                bpaService.calculateRace(bpa, bpaiList);
            }

            if(ageDiff){
                bpaService.calculateAge(bpa, bpaiList);
            }

            if(sexDiff || raceDiff || ageDiff) {
                bpaiService.EntityManagerDetach(bpaiList);

                bpaService.save(bpa);
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Atualiza o campo não nulo da requisição.
     * @param id
     * @param paramBpa
     * @return
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<List<ErrorValidationDTO>> updateBpai(@PathVariable Long id, @RequestBody @Valid ParamUpdateErrorsBpa paramBpa, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Bpa> bpaOptional = Optional.empty();
        List<ErrorValidationDTO> erros = new ArrayList<>();

        switch (paramBpa.getKey()) {
            case "pa", "qtService", "race", "sexProcedure", "ageMaxMin", "cbo" , "cnsmedProfessional" , "birthDate" -> bpaOptional = bpaService.get(Utilities.formatDate(paramBpa.getDateBpa()), user);
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

        String key = paramBpa.getKey();

        switch (key) {
            case "pa", "qtService" -> bpaService.calculateInvoicing(bpaOptional.get(), null, null, null, user, true);
            case "race", "sexProcedure", "ageMaxMin" -> {
                Bpa bpa = bpaOptional.get();

                List<Bpai> bpaiList = bpaiService.get(bpa);

                switch (key) {
                    case "race" -> {
                        EncryptionService.decryptRace(bpaiList);
                        bpaService.calculateRace(bpa, bpaiList);
                    }
                    case "sexProcedure" -> {
                        EncryptionService.decryptSex(bpaiList);
                        bpaService.calculateSex(bpa, bpaiList);
                    }
                    case "ageMaxMin" -> {
                        EncryptionService.decryptBpaiIdade(bpaiList);
                        bpaService.calculateAge(bpa, bpaiList);
                    }
                }

                bpaiService.EntityManagerDetach(bpaiList);

                bpaService.save(bpa);
            }
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete/{date}")
    public ResponseEntity<Object> editBpai(@PathVariable String date, @RequestBody @Valid ParamDeleteBpai paramDeleteBpai, Authentication authentication) {
        try {
            User user = userService.get(authentication);

            Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(date), user);

            if(bpaOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("NOT FOUND BPA");
            }

            Long size = bpaiService.sizeByte(paramDeleteBpai.getList());

            if(size == null) {
                return ResponseEntity.ok().build();
            }

            try {
                bpaiService.deleteById(paramDeleteBpai.getList());
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("ERROR");
            }

            Bpa bpa = bpaOptional.get();

            bpaService.calculateAll(user, bpa, null, null, true);

            bpaService.updateBytes(bpa, size, false);

            return ResponseEntity.ok(size);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ErrorResponseDTO());
        }
    }

}



