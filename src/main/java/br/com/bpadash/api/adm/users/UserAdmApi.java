package br.com.bpadash.api.adm.users;

import br.com.bpadash.dto.adm.UserAdmDTO;
import br.com.bpadash.model.Administrator;
import br.com.bpadash.model.user.User;
import br.com.bpadash.services.adm.AdmServices;
import br.com.bpadash.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/adm/users")
public class UserAdmApi {


    @Autowired
    private AdmServices admServices;
    @Autowired
    private UserService userService;


    @GetMapping("/get/all")
    private ResponseEntity<Object> getUsers() {
        List<User> userList = admServices.getUsers();

        List<UserAdmDTO> userAdmDTOList = new ArrayList<>();
        userList.forEach( user -> {
            userAdmDTOList.add(new UserAdmDTO(user));
        });

        return ResponseEntity.ok(userAdmDTOList);
    }


    @PostMapping("/active/{id}")
    private ResponseEntity<Object> getUsers(@PathVariable Long id) {
        User user = userService.get(id);

        admServices.changeActiveUser(user);

        return ResponseEntity.ok().build();
    }
}
