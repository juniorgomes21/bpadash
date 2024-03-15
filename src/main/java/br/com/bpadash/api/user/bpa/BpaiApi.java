package br.com.bpadash.api.user.bpa;

import br.com.bpadash.dto.bpa.BpaiDTO;
import br.com.bpadash.errorValidation.ErrorResponseDTO;
import br.com.bpadash.errorValidation.ErrorValidationDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.enumModel.ActionEmployee;
import br.com.bpadash.model.enumModel.ActionType;
import br.com.bpadash.model.user.Employee;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.bpa.ParamDeleteBpai;
import br.com.bpadash.params.bpa.ParamUpdateBpai;
import br.com.bpadash.params.bpa.ParamUpdateErrorsBpa;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.cache.CacheService;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import br.com.bpadash.services.scanner.ScannerFile;
import br.com.bpadash.services.user.EmployeeService;
import br.com.bpadash.services.user.StockHistoryService;
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
    @Autowired
    private StorageService storageService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private StockHistoryService stockHistoryService;


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

    @GetMapping("/get/{dateBPA}/{cnsPac}/{employeeKey}")
    public ResponseEntity<Object> getUserForCnsPac(@PathVariable String dateBPA, @PathVariable String employeeKey, @PathVariable String cnsPac, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

        if(employeeOptional.isPresent()) {
            Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(dateBPA), user);

            if(bpaOptional.isPresent()) {
                Bpa bpa = bpaOptional.get();

                List<Bpai> bpaiList = bpaiService.get(bpa);

                EnCryptionAESService.decryptCnsPac(bpaiList);

                Bpai bpaiOk = null;
                for(Bpai bpai: bpaiList) {
                    if(bpai.getCnspac().equals(cnsPac)) {
                        bpaiOk = bpai;
                    }
                }

                if(bpaiOk == null) {
                    return ResponseEntity.badRequest().body("NOT FOUND");
                }

                EnCryptionAESService.decryptBpai(List.of(bpaiOk), false);

                return ResponseEntity.ok(new BpaiDTO(bpaiOk, ""));
            }

            return ResponseEntity.badRequest().body("NOT EXIST DATE BPA");
        }

        return ResponseEntity.status(401).body("FORBIDDEN");
    }

    @PostMapping(value = "/create/{month}/{year}/{employeeKey}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> bpaCreate(@RequestPart("file") MultipartFile file, @PathVariable int month, @PathVariable int year, @PathVariable String employeeKey, Authentication authentication) {
        User user = userService.get(authentication);
        Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

        if(employeeOptional.isPresent() && employeeOptional.get().getPermissions().isAddBpa()) {
            List<ErrorsFile> errorsFiles = new ArrayList<>();
            try {
                LocalDate localDate = LocalDate.of(year, month, 1);
                Optional<Bpa> bpaOptional = bpaService.get(localDate, user);

                if(bpaOptional.isEmpty()) {
                    errorsFiles.add(new ErrorsFile("NOT EXIST DATE"));

                    return ResponseEntity.badRequest().body(errorsFiles);
                }

                Bpa bpa = bpaOptional.get();

                Bpai bpai = bpaiService.getLast(bpa);

                String response = scannerFile.createBpai(file, bpaOptional.get(), bpai, user, errorsFiles, employeeOptional.get());

                switch (response) {
                    case "CREATE" -> {
                        bpaService.calculateAll(user, bpa, null, null, true);

                        bpaService.updateStateManager(bpa, false);

                        return ResponseEntity.ok().body(response);
                    }
                    case "ERROR FILE" -> {
                        return ResponseEntity.badRequest().body(errorsFiles);
                    }
                    default -> {
                        return ResponseEntity.badRequest().body(response);
                    }
                }

            } catch (StringIndexOutOfBoundsException | IllegalArgumentException e) {
                errorsFiles.add(new ErrorsFile("FILE INVALID"));

                return ResponseEntity.badRequest().body(errorsFiles);
            }
        }

        return ResponseEntity.status(401).body("FORBIDDEN");
    }

    @PostMapping("/edit/{id}/{employeeKey}")
    public ResponseEntity<Object> editBpai(@PathVariable Long id, @PathVariable String employeeKey, @RequestBody @Valid ParamUpdateBpai paramUpdateBpai, Authentication authentication) {
        try {
            User user = userService.get(authentication);
            Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

            if(employeeOptional.isPresent() && employeeOptional.get().getPermissions().isEditBpa()) {
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

                EnCryptionAESService.decryptBpai(List.of(bpai), false);

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
                    EnCryptionAESService.decryptAgeAndSexAndRace(bpaiList);
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

                stockHistoryService.register(ActionEmployee.UPDATE_BPAI.getAction(), ActionType.UPDATE.getAction(), bpa.getDate(), 1, user, employeeOptional.get());

                return ResponseEntity.ok().build();
            }

            return ResponseEntity.status(401).body("FORBIDDEN");

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
    @PostMapping("/update/{id}/{employeeKey}")
    public ResponseEntity<Object> updateBpai(@PathVariable Long id, @PathVariable String employeeKey, @RequestBody @Valid ParamUpdateErrorsBpa paramBpa, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

        if(employeeOptional.isPresent() && employeeOptional.get().getPermissions().isEditBpa()) {
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

            if(count > 0) {
                String key = paramBpa.getKey();

                switch (key) {
                    case "pa", "qtService" -> bpaService.calculateInvoicing(bpaOptional.get(), null, null, null, user, true);
                    case "race", "sexProcedure", "ageMaxMin" -> {
                        Bpa bpa = bpaOptional.get();

                        List<Bpai> bpaiList = bpaiService.get(bpa);

                        switch (key) {
                            case "race" -> {
                                EnCryptionAESService.decryptRace(bpaiList);
                                bpaService.calculateRace(bpa, bpaiList);
                            }
                            case "sexProcedure" -> {
                                EnCryptionAESService.decryptSex(bpaiList);
                                bpaService.calculateSex(bpa, bpaiList);
                            }
                            case "ageMaxMin" -> {
                                EnCryptionAESService.decryptBpaiIdade(bpaiList);
                                bpaService.calculateAge(bpa, bpaiList);
                            }
                        }

                        bpaiService.EntityManagerDetach(bpaiList);

                        bpaService.save(bpa);
                    }
                }

                stockHistoryService.register(
                        ActionEmployee.UPDATE_BPAI.getAction(),
                        ActionType.UPDATE.getAction(),
                        bpaOptional.map(Bpa::getDate).orElse(null),
                        count,
                        user,
                        employeeOptional.get()
                );
            }

            return ResponseEntity.ok(count);
        }

        return ResponseEntity.status(401).body("FORBIDDEN");
    }

    @PostMapping("/delete/{date}/{employeeKey}")
    public ResponseEntity<Object> deleteBpai(@PathVariable String date, @PathVariable String employeeKey, @RequestBody @Valid ParamDeleteBpai paramDeleteBpai, Authentication authentication) {
        try {

            User user = userService.get(authentication);

            Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

            if(employeeOptional.isPresent() && employeeOptional.get().getPermissions().isDeleteBpa()) {

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

                int linesModified = paramDeleteBpai.getList().size();

                bpaService.calculateLineInTitle(bpa, linesModified, 0, false);

                bpaService.calculateAll(user, bpa, null, null, true);

                storageService.updateBytesBpaAndUser(user, true, bpa, false, size);

                stockHistoryService.register(ActionEmployee.DELETE_BPAI.getAction(), ActionType.DELETE_LINE.getAction(), bpa.getDate(), linesModified, user, employeeOptional.get());

                return ResponseEntity.ok(linesModified);
            }

            return ResponseEntity.status(401).body("FORBIDDEN");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ErrorResponseDTO());
        }
    }

}



