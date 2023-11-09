package br.com.bpadash.api.user;

import br.com.bpadash.dto.StorageDTO;
import br.com.bpadash.dto.UserDTO;
import br.com.bpadash.dto.bpa.TimeLineUserDTO;
import br.com.bpadash.model.User;
import br.com.bpadash.params.bpa.ParamValidationBpac;
import br.com.bpadash.params.bpa.ParamValidationBpai;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserApi {

    @Autowired
    private UserService userService;

    @Autowired
    private BpaiService bpaiService;

    @GetMapping
    public ResponseEntity<UserDTO> userDTO() {
        User user = userService.userInDb(1L);

        UserDTO userDTO = new UserDTO(user);

        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/timeline")
    public ResponseEntity<List<TimeLineUserDTO>> timeLineUser(Authentication authentication) {
        User user = userService.userInDb(1L);

        return ResponseEntity.ok(userService.timeLine(user));
    }

    @GetMapping("/storage")
    public ResponseEntity<StorageDTO> storageDTO(Authentication authentication) {
        User user = userService.userInDb(1L);

        return ResponseEntity.ok(new StorageDTO(user));
    }

    @PostMapping("/set/validations/bpac")
    public ResponseEntity<Object> setValidationsBpac(@RequestBody ParamValidationBpac paramValidationBpac, Authentication authentication) {
        User user = userService.userInDb(1L);

        userService.setValidationBpac(user, paramValidationBpac);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/set/validations/bpai")
    public ResponseEntity<Object> setValidationsBpai(@RequestBody ParamValidationBpai paramValidationBpai, Authentication authentication) {
        User user = userService.userInDb(1L);

        userService.setValidationBpai(user, paramValidationBpai);

        return ResponseEntity.ok().build();
    }
}
