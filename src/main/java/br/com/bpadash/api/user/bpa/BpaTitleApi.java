package br.com.bpadash.api.user.bpa;

import br.com.bpadash.dto.bpa.TitleBpaDTO;
import br.com.bpadash.model.Bpa;
import br.com.bpadash.model.TitleBpa;
import br.com.bpadash.model.User;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.TitleBpaService;
import br.com.bpadash.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/title")
public class BpaTitleApi {

    @Autowired
    private UserService userService;

    @Autowired
    private BpaService bpaService;

    @Autowired
    private TitleBpaService titleBpaService;

    @GetMapping("/get/{identifier}")
    public ResponseEntity<TitleBpaDTO> titleBpaDTO(@PathVariable @Valid @NotBlank String identifier) {
        User user = userService.userInDb(1L);
        Bpa bpa = bpaService.get(identifier, user);
        TitleBpa titleBpa = titleBpaService.get(bpa);

        if(bpa == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(new TitleBpaDTO(titleBpa));
    }

    @GetMapping("/get/{month}/{year}")
    public ResponseEntity<TitleBpaDTO> titleBpaDTO(@PathVariable int month, @PathVariable int year) {
        User user = userService.userInDb(1L);
        Bpa bpa = bpaService.getForDate(month, year, user);
        TitleBpa titleBpa = titleBpaService.get(bpa);

        return ResponseEntity.ok(new TitleBpaDTO(titleBpa));
    }
}
