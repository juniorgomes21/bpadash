package br.com.bpadash.api.user.Employeer;

import br.com.bpadash.dto.user.AccountLoggedDTO;
import br.com.bpadash.dto.user.EmployeeDTO;
import br.com.bpadash.model.enumModel.ActionEmployee;
import br.com.bpadash.model.enumModel.ActionType;
import br.com.bpadash.model.user.Employee;
import br.com.bpadash.model.user.SessionUser;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.user.*;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import br.com.bpadash.services.user.EmployeeService;
import br.com.bpadash.services.user.SessionUserService;
import br.com.bpadash.services.user.StockHistoryService;
import br.com.bpadash.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeApi {

    @Autowired
    private UserService userService;
    @Autowired
    private SessionUserService sessionUserService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private StockHistoryService stockHistoryService;

    @GetMapping("/get/{employeeKey}")
    public ResponseEntity<Object> getEmployee(@PathVariable String employeeKey, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

        if(employeeOptional.isPresent()) {
            EmployeeDTO employeeDTOList = new EmployeeDTO(employeeOptional.get(), employeeKey);

            return ResponseEntity.ok(employeeDTOList);
        }

        return ResponseEntity.status(401).body("FORBIDDEN");
    }

    @GetMapping("/master/get/{employeeKey}")
    public ResponseEntity<Object> getEmployeeMaster(@PathVariable String employeeKey, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

        if(employeeOptional.isPresent() && employeeOptional.get().isMaster()) {
            Optional<SessionUser> sessionUserOptional = sessionUserService.get(user);

            if(sessionUserOptional.isEmpty()) return ResponseEntity.badRequest().body("NOT FOUND SESSION USER");
            SessionUser sessionUser = sessionUserOptional.get();

            List<EmployeeDTO> employeeDTOList = user.getEmployeeRegistered().stream().map(EmployeeDTO::new).toList();

            sessionUserService.updateSession(sessionUser);
            List<AccountLoggedDTO> sessionUserDTO = sessionUser.getAccountLoggeds().stream().map( accountLogged -> new AccountLoggedDTO(accountLogged.getEmployee())).toList();

            Map<String, Object> mapEmployee = new HashMap<>();
            mapEmployee.put("maxEmployee", user.getCountMaxEmployee());
            mapEmployee.put("sessionUserDTO", sessionUserDTO);
            mapEmployee.put("employees", employeeDTOList);

            return ResponseEntity.ok(mapEmployee);
        }

        return ResponseEntity.status(401).body("FORBIDDEN");
    }

    @PostMapping("/create/{employeeKey}")
    public ResponseEntity<String> createEmployee(@PathVariable String employeeKey, @RequestBody @Valid ParamCreateEmployee paramCreateEmployee, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

        if(employeeOptional.isPresent() && employeeOptional.get().isMaster()) {

            String response = employeeService.newEmployeeAndSave(user, paramCreateEmployee);

            if(!response.equals("CREATE")) {
                return ResponseEntity.badRequest().body(response);
            }

            return ResponseEntity.ok().body(response);
        }

        return ResponseEntity.status(401).body("FORBIDDEN");
    }

    @PostMapping("/edit/permissions/{id}/{employeeKey}")
    public ResponseEntity<Object> editEmployeePermissions(@PathVariable String id, @PathVariable String employeeKey, @RequestBody @Valid ParamEditEmployeePermissions paramEdit, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

        if(employeeOptional.isPresent() && employeeOptional.get().isMaster()) {

            Optional<Employee> employeeEditOptional = employeeService.getRegistered(user, id);

            if(employeeEditOptional.isPresent()) {
                Employee employee = employeeEditOptional.get();

                employeeService.editPermissionAndSave(employee, user, paramEdit);

                stockHistoryService.register(ActionEmployee.UPDATE_ACCOUNT.getAction(), ActionType.UPDATE.getAction(), null, 0, user, employee);

                return ResponseEntity.ok().body("OK");
            }

            return ResponseEntity.badRequest().body("NOT FOUND EMPLOYEE");
        }

        return ResponseEntity.status(401).body("FORBIDDEN");
    }

    @PostMapping("/edit/{employeeKey}")
    public ResponseEntity<Object> editEmployee(@PathVariable String employeeKey, @RequestBody @Valid ParamEditEmployee paramEdit, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

        if(employeeOptional.isPresent() && employeeService.testPassword(employeeOptional.get(), paramEdit.getPassword())) {

            Employee employee = employeeOptional.get();

            String response = employeeService.editAndSave(employee, user, paramEdit);

            if(!response.equals("OK")) {
                return ResponseEntity.badRequest().body(response);
            }

            stockHistoryService.register(ActionEmployee.UPDATE_ACCOUNT.getAction(), ActionType.UPDATE_EMPLOYEE.getAction(), null, 0, user, employee);

            return ResponseEntity.ok().body(response);
        }

        return ResponseEntity.status(401).body("FORBIDDEN");
    }

    @PostMapping("/edit/password/{employeeKey}")
    public ResponseEntity<Object> editEmployee(@PathVariable String employeeKey, @RequestBody @Valid ParamNewPassword paramEdit, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

        if(employeeOptional.isPresent()) {

            Employee employee = employeeOptional.get();

            if(!employeeService.testPassword(employee, paramEdit.getPassword())) {
                return ResponseEntity.badRequest().body("INCORRECT PASSWORD");
            }

            String response = employeeService.editPasswordAndSave(employee, user, paramEdit);

            if(!response.equals("OK")) {
                return ResponseEntity.badRequest().body(response);
            }

            stockHistoryService.register(ActionEmployee.UPDATE_ACCOUNT.getAction(), ActionType.UPDATE_EMPLOYEE.getAction(), null, 0, user, employee);

            return ResponseEntity.ok().body(response);
        }

        return ResponseEntity.status(401).body("FORBIDDEN");
    }

    @PostMapping("/edit/master/{id}/{employeeKey}")
    public ResponseEntity<Object> editMasterEmployee(@PathVariable String id, @PathVariable String employeeKey, @RequestBody @Valid ParamEditEmployeeMaster paramEdit, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);
        if(employeeOptional.isPresent() && employeeOptional.get().isMaster()) {

            String response = employeeService.editMasterAndSave(id, user, paramEdit);

            if(!response.equals("OK")) {
                return ResponseEntity.badRequest().body(response);
            }

            stockHistoryService.register(ActionEmployee.UPDATE_ACCOUNT_MASTER.getAction(), ActionType.UPDATE_EMPLOYEE.getAction(), null, 0, user, employeeOptional.get());

            return ResponseEntity.ok().body(response);
        }

        return ResponseEntity.status(401).body("FORBIDDEN");
    }

    @PostMapping("/delete/{id}/{employeeKey}")
    public ResponseEntity<String> removeEmployee(@PathVariable String id, @PathVariable String employeeKey, Authentication authentication) {
        User user = userService.get(authentication);
        Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

        if(employeeOptional.isPresent() && employeeOptional.get().isMaster()) {
            Optional<Employee> employeeDeleteOptional = employeeService.getRegistered(user, id);

            if(employeeDeleteOptional.isPresent()) {
                String response = employeeService.deleteEmployeeAndSave(employeeDeleteOptional.get(), user);

                if(!response.equals("OK")) {
                    return ResponseEntity.badRequest().body(response);
                }

                return ResponseEntity.ok().body(response);
            }

            return ResponseEntity.badRequest().body("NOT FOUND EMPLOYEE");
        }

        return ResponseEntity.status(401).body("FORBIDDEN");
    }
}

