package br.com.bpadash.api.user.bpa;

import br.com.bpadash.dto.bpa.BpaiDTO;
import br.com.bpadash.dto.error.ErrorResponseDTO;
import br.com.bpadash.dto.error.ErrorValidationDTO;
import br.com.bpadash.model.Bpa;
import br.com.bpadash.model.Bpai;
import br.com.bpadash.model.User;
import br.com.bpadash.params.bpa.ParamDeleteBpai;
import br.com.bpadash.params.bpa.ParamUpdateBpai;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
    private UserService userService;

    @GetMapping("/get/{month}/{year}")
    public ResponseEntity<Page<BpaiDTO>> getBpai(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable pageable,
            @PathVariable int month,
            @PathVariable int year,
            Authentication authentication
    ) {
        User user = userService.userInDb(1L);
        Bpa bpa = bpaService.getForDate(month, year, user);

        Page<BpaiDTO> page = bpaiService.get(bpa, pageable);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/get/{identifier}")
    public ResponseEntity<Page<BpaiDTO>> getBpaiIdent(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable pageable,
            @PathVariable @Valid @NotBlank String identifier,
            Authentication authentication
    ) {
        User user = userService.userInDb(1L);
        Bpa bpa = bpaService.get(identifier, user);
        Page<BpaiDTO> page = bpaiService.get(bpa, pageable);

        return ResponseEntity.ok(page);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<List<ErrorValidationDTO>> editBpac(@PathVariable Long id, @RequestBody @Valid ParamUpdateBpai paramUpdateBpai) {
        try {
            List<ErrorValidationDTO> erros = new ArrayList<>();
            if(!paramUpdateBpai.getEtnia().trim().isEmpty()) {
                if(!paramUpdateBpai.getRaca().equals("05")) { // TODO A partir da competência Out/2010.
                    erros.add(new ErrorValidationDTO("etnia", "Preencher somente se o campo raça/cor for 05 - Indígena."));

                    return ResponseEntity.badRequest().body(erros);
                }
            }

            Optional<Bpai> optionalBpai = bpaiService.bpacId(id);
            if(!optionalBpai.isPresent()) {
                erros.add(new ErrorValidationDTO("id", "O id não existe."));
                return ResponseEntity.badRequest().body(erros);
            }

            Bpai bpacUpdated = bpaiService.edit(optionalBpai.get(), paramUpdateBpai);

            bpaiService.save(bpacUpdated);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/delete")
    public ResponseEntity<Object> editBpac(@RequestBody @Valid ParamDeleteBpai paramDeleteBpai, Authentication authentication) {
        try {
            User user = userService.userInDb(1L);
            Bpa bpa = bpaService.get(paramDeleteBpai.getIdentifier(), user);
            int size = bpaiService.sizeByte(paramDeleteBpai.getList());

            bpaiService.deleteById(paramDeleteBpai.getList());
            bpaService.updatebyte(bpa, size, false);

            return ResponseEntity.ok(size);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO());
        }
    }
}
