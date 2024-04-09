package br.com.bpadash.api.user.auth;


import br.com.bpadash.model.enumModel.ActionEmployee;
import br.com.bpadash.model.enumModel.ActionType;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.user.*;
import br.com.bpadash.dto.DatesDTO;
import br.com.bpadash.dto.TokenDTO;
import br.com.bpadash.dto.UserDTO;
import br.com.bpadash.dto.user.EmployeeDTO;
import br.com.bpadash.model.user.Employee;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.user.ParamLoginEmployee;
import br.com.bpadash.params.user.ParamUserNameSession;
import br.com.bpadash.security.TokenApp;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthApi {
    private static final Dotenv dotenv = Dotenv.load();
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private TokenApp tokenApp;
    @Autowired
    private UserService userService;
    @Autowired
    private SessionUserService sessionUserService;
    @Autowired
    private BpaService bpaService;
    @Autowired
    private StockHistoryService stockHistoryService;
    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/login")
    public ResponseEntity<Object> autenticar(@RequestBody @Valid ParamLogin form) {

        UsernamePasswordAuthenticationToken dadosLogin = form.converter();

        try {
            Authentication authentication = authManager.authenticate(dadosLogin);

            User user = userService.userLogged(authentication);

            if(!user.isValid()) return ResponseEntity.badRequest().body("USER BLOCKED");

            String token = tokenApp.gerarToken(user);

            DatesDTO datesDTOS = bpaService.getDates(user);

            return ResponseEntity.ok(new TokenDTO(token, new UserDTO(user), datesDTOS));

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("BAD CREDENTIALS");
        }
    }

    @PostMapping("/login/employee")
    public ResponseEntity<Object> authenticateEmployee(@RequestBody @Valid ParamLoginEmployee form, Authentication authentication) {
        try {
            User user = userService.get(authentication);

            Optional<Employee> employeeOptional = user.getEmployeeRegistered()
                    .stream()
                    .filter( employee -> employee.getName().equals(form.getUserName()))
                    .findFirst();

            String password = EnCryptionAESService.hashString(form.getPassword());

            if(employeeOptional.isPresent() && employeeOptional.get().getPassword().equals(password)) {
                Employee employee = employeeOptional.get();

                String key = EncryptionService.encryptKey(dotenv.get("KEY_USER"));

                String response = sessionUserService.verify(user, employee, key);
                if(!response.equals("OK")) {
                    return ResponseEntity.badRequest().body(response);
                }

                stockHistoryService.register(ActionEmployee.LOGIN.getAction(), ActionType.LOGIN.getAction() , null, 0, user, employee);

                return ResponseEntity.ok(new EmployeeDTO(employee, key));
            }

            return ResponseEntity.badRequest().body("BAD CREDENTIALS");

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("INTERNAL ERROR");
        }
    }

    @PostMapping("/logout/{employeeKey}")
    public ResponseEntity<Object> autenticar(@PathVariable String employeeKey, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

        if(employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();

            String respose = sessionUserService.logoutEmployee(user, employee);

            if(!respose.equals("OK")) {
                return ResponseEntity.badRequest().body(respose);
            }

            stockHistoryService.register(ActionEmployee.LOGOUT.getAction(), ActionType.LOGOUT.getAction() , null, 0, user, employee);

            return ResponseEntity.ok().body(respose);
        }

        return ResponseEntity.status(401).body("UNAUTHORIZED");
    }
}
