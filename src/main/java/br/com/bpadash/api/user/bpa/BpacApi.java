package br.com.bpadash.api.user.bpa;

import br.com.bpadash.errorValidation.ErrorResponseDTO;
import br.com.bpadash.dto.bpa.BpacDTO;
import br.com.bpadash.errorValidation.ErrorValidationDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpac;
import br.com.bpadash.model.enumModel.ActionEmployee;
import br.com.bpadash.model.enumModel.ActionType;
import br.com.bpadash.model.user.Employee;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.bpa.ParamDeleteBpac;
import br.com.bpadash.params.bpa.ParamUpdateBpac;
import br.com.bpadash.params.bpa.ParamUpdateErrorsBpa;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpacService;
import br.com.bpadash.services.cache.CacheService;
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
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private StockHistoryService stockHistoryService;


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

    @PostMapping( value = "/create/{month}/{year}/{employeeKey}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> bpaCreate(@RequestPart("file") MultipartFile file, @PathVariable int month, @PathVariable int year, @PathVariable String employeeKey, Authentication authentication) {
        try {
            User user = userService.get(authentication);
            Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

            if(employeeOptional.isPresent() && employeeOptional.get().getPermissions().isAddBpa()) {

                List<ErrorsFile> errorsFiles = new ArrayList<>();

                LocalDate localDate = LocalDate.of(year, month, 1);

                Optional<Bpa> bpaOptional = bpaService.get(localDate, user);
                if(bpaOptional.isEmpty()) {
                    errorsFiles.add(new ErrorsFile("NOT STORAGE"));

                    return ResponseEntity.badRequest().body(errorsFiles);
                }

                Bpa bpa = bpaOptional.get();

                Bpac bpac = bpacService.getLast(bpa);

                String response = scannerFile.createBpac(user, file, bpa, bpac, errorsFiles, employeeOptional.get());

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
            }

            return ResponseEntity.status(401).body("FORBIDDEN");

        } catch (StringIndexOutOfBoundsException e) {
            throw new StringIndexOutOfBoundsException("A estrutura do arquivo está incorreta o erro se encontra em " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("A estrutura do arquivo está incorreta: " + e.getMessage());
        }
    }

    @PostMapping("/edit/{id}/{employeeKey}")
    public ResponseEntity<Object> editBpac(@PathVariable Long id, @PathVariable String employeeKey, @RequestBody @Valid ParamUpdateBpac paramUpdateBpac, Authentication authentication) {
        try {
            User user = userService.get(authentication);

            Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

            if(employeeOptional.isPresent() && employeeOptional.get().getPermissions().isEditBpa()) {
                Bpac bpac = bpacService.bpacId(id);

                String pa = bpac.getPa();
                String qt = bpac.getQt();

                bpacService.editAndSave(bpac, paramUpdateBpac);

                Bpa bpa = bpac.getBpa();

                if(!pa.equals(paramUpdateBpac.getPa()) || !qt.equals(paramUpdateBpac.getQt())){
                    List<Bpac> bpacList = bpacService.get(bpa);

                    bpaService.calculateInvoicing(bpa, null, bpacList, null, user, true);
                }

                stockHistoryService.register(ActionEmployee.UPDATE_BPAC.getAction(), ActionType.UPDATE.getAction(), bpa.getDate(), 1, user, employeeOptional.get());

                return ResponseEntity.ok().build();
            }

            return ResponseEntity.status(401).body("FORBIDDEN");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO());
        }
    }

    @PostMapping("/update/{id}/{employeeKey}")
    public ResponseEntity<Object> updateBpac(@PathVariable Long id, @PathVariable String employeeKey, @RequestBody @Valid ParamUpdateErrorsBpa paramBpa, Authentication authentication) {
        User user = userService.get(authentication);
        Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

        if(employeeOptional.isPresent() && employeeOptional.get().getPermissions().isEditBpa()) {

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

            int count = bpacService.editAndSave(bpac, paramBpa, bpa);

            if(count > 0) {
                String key = paramBpa.getKey();

                if (key.equals("pa")) {
                    bpaService.calculateInvoicing(bpa , null , null , null , user , true);

                    bpaService.save(bpa);
                }

                stockHistoryService.register(
                        ActionEmployee.UPDATE_BPAC.getAction(),
                        ActionType.UPDATE.getAction(),
                        bpaOptional.map(Bpa::getDate).orElse(null),
                        count,
                        user,
                        employeeOptional.get()
                );
            }

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(401).body("FORBIDDEN");
    }

    @PostMapping("/delete/{date}/{employeeKey}")
    public ResponseEntity<Object> deleteBpac(@PathVariable String date, @PathVariable String employeeKey, @RequestBody @Valid ParamDeleteBpac paramDeleteBpac, Authentication authentication) {
        try {
            User user = userService.get(authentication);

            Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

            if(employeeOptional.isPresent() && employeeOptional.get().getPermissions().isDeleteBpa()) {

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

                int linesModifed = paramDeleteBpac.getList().size();

                bpaService.calculateLineInTitle(bpa, 0, linesModifed, false);

                bpaService.calculateAll(user, bpa, null, null, true);

                storageService.updateBytesBpaAndUser(user, true, bpa, false, size);

                stockHistoryService.register(ActionEmployee.DELETE_BPAC.getAction(), ActionType.DELETE_LINE.getAction(), bpa.getDate(), linesModifed, user, employeeOptional.get());

                return ResponseEntity.ok(linesModifed);
            }

            return ResponseEntity.status(401).body("FORBIDDEN");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO());
        }
    }

}
