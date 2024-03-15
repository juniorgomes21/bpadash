package br.com.bpadash.api.user.history;

import br.com.bpadash.dto.UserDTO;
import br.com.bpadash.dto.bpa.BpaiDTO;
import br.com.bpadash.dto.user.StockHistoryDTO;
import br.com.bpadash.model.user.Employee;
import br.com.bpadash.model.user.User;
import br.com.bpadash.services.user.EmployeeService;
import br.com.bpadash.services.user.StockHistoryService;
import br.com.bpadash.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/history")
public class HistoryAction {
    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private StockHistoryService stockHistoryService;

    @GetMapping("/get")
    public ResponseEntity<Object> getUser(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Authentication authentication) {
        User user = userService.userLogged(authentication);

        Page<StockHistoryDTO> page = stockHistoryService.get(user, pageable);
//        Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);
//
//        if(employeeOptional.isPresent() && employeeOptional.get().isMaster()) {
//
//        }

        return ResponseEntity.ok(page);
    }
}
