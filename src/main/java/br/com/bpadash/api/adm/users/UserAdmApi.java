package br.com.bpadash.api.adm.users;

import br.com.bpadash.dto.adm.UserAdmDTO;
import br.com.bpadash.model.Administrator;
import br.com.bpadash.model.email.TestEmailUser;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.adm.ParamTestEmailUser;
import br.com.bpadash.services.adm.AdmServices;
import br.com.bpadash.services.email.SendEmailService;
import br.com.bpadash.services.email.TestEmailUserService;
import br.com.bpadash.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/adm/users")
public class UserAdmApi {


    @Autowired
    private AdmServices admServices;
    @Autowired
    private UserService userService;
    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    private TestEmailUserService testEmailUserService;


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

    @PostMapping("/test/email")
    private ResponseEntity<Object> testEmailUser(@RequestBody ParamTestEmailUser paramTestEmailUser, Authentication authentication) {
        Administrator administrator = admServices.logged(authentication);

        sendEmailService.sendCodeTestUser(paramTestEmailUser.getEmail(), paramTestEmailUser.getCode(), administrator);

        testEmailUserService.save(new TestEmailUser(paramTestEmailUser));

        return ResponseEntity.ok().build();
    }


    @GetMapping("/test/email/sent")
    private ResponseEntity<Object> testEmailUserSent(@RequestBody ParamTestEmailUser paramTestEmailUser) {

        return ResponseEntity.ok(testEmailUserService.getAll());
    }
}
