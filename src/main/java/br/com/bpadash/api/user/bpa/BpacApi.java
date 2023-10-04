package br.com.bpadash.api.user.bpa;

import br.com.bpadash.dto.error.ErrorResponseDTO;
import br.com.bpadash.dto.bpa.BpacDTO;
import br.com.bpadash.model.Bpa;
import br.com.bpadash.model.Bpac;
import br.com.bpadash.model.User;
import br.com.bpadash.params.bpa.ParamDeleteBpac;
import br.com.bpadash.params.bpa.ParamUpdateBpac;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpacService;
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

@RestController
@RequestMapping("/api/bpac")
public class BpacApi {

    @Autowired
    private BpacService bpacService;

    @Autowired
    private BpaService bpaService;

    @Autowired
    private UserService userService;

    @GetMapping("/get/{identifier}")
    public ResponseEntity<Page<BpacDTO>> getBpa(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable pageable,
            @PathVariable @Valid @NotBlank String identifier,
            Authentication authentication
    ) {
        User user = userService.userInDb(1L);
        Bpa bpa = bpaService.get(identifier, user);
        Page<BpacDTO> page = bpacService.get(bpa, pageable);

        return ResponseEntity.ok(page);
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
            User user = userService.userInDb(1L);
            Bpa bpa = bpaService.get(paramDeleteBpac.getIdentifier(), user);
            int size = bpacService.sizeByte(paramDeleteBpac.getList());

            bpacService.delete(paramDeleteBpac.getList());
            bpaService.updatebyte(bpa, size, false);

            return ResponseEntity.ok(size);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO());
        }
    }
}
