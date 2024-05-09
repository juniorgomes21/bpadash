package br.com.bpadash.api.user.goal;

import br.com.bpadash.model.GroupsPa.GroupsPa;
import br.com.bpadash.model.sigtap.LinkFpo;
import br.com.bpadash.model.user.User;
import br.com.bpadash.services.fpo.LinkFpoService;
import br.com.bpadash.services.goal.GoalService;
import br.com.bpadash.services.goal.GroupsService;
import br.com.bpadash.services.goal.ParamPa;
import br.com.bpadash.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/goal")
public class GoalApi {

    @Autowired
    private GoalService goalService;
    @Autowired
    private LinkFpoService linkFpoService;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupsService groupsService;

    @GetMapping("/all/{pa}")
    public ResponseEntity<Object> getAllGoal(
            @PathVariable
            @Valid
            @NotNull
            @Size(max = 9, message = "Pa deve ter no máximo 9 caracteres")
            String pa,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable, Authentication authentication
    ) {
        User user = userService.get(authentication);

        Optional<LinkFpo> linkFpoOptional = linkFpoService.verify(user);

        return linkFpoOptional.<ResponseEntity<Object>>map(linkFpo -> ResponseEntity.ok(goalService.calculateGoal(linkFpo, pa, pageable))).orElseGet(() -> ResponseEntity.badRequest().body("NOT FOUND FPO"));
    }

    @GetMapping("/get/budget")
    public ResponseEntity<Object> getBudget(Authentication authentication) {
        User user = userService.get(authentication);

        Optional<LinkFpo> linkFpoOptional = linkFpoService.verify(user);

        return linkFpoOptional.<ResponseEntity<Object>>map(linkFpo -> ResponseEntity.ok(goalService.calculateBuget(linkFpo))).orElseGet(() -> ResponseEntity.badRequest().body("NOT FOUND FPO"));
    }

    @GetMapping("/get/budget/yearly/{year}")
    public ResponseEntity<Object> getBudgetYearly(@PathVariable int year, Authentication authentication) {
        User user = userService.get(authentication);

        List<LinkFpo> linkFpoList = linkFpoService.get(user, year);

        if(linkFpoList.isEmpty()) return ResponseEntity.badRequest().body("NO EXIST FPO IN DATE");

        return ResponseEntity.ok(goalService.calculateBugetYearly(linkFpoList));
    }

    @GetMapping("/get/groups")
    public ResponseEntity<Object> getAllGroups(Authentication authentication) {
        User user = userService.get(authentication);

        Optional<GroupsPa> groupsPaOptional = groupsService.get(user);

        if(groupsPaOptional.isPresent()) {
            GroupsPa groupsPa = groupsPaOptional.get();

            Optional<LinkFpo> linkFpoOptional = linkFpoService.verify(user);

            return linkFpoOptional.<ResponseEntity<Object>>map(linkFpo -> ResponseEntity.ok(goalService.calculateGoalPerGroup(linkFpo, groupsPa.getPaList()))).orElseGet(() -> ResponseEntity.badRequest().body("NOT FOUND FPO"));
        }

        return ResponseEntity.badRequest().body("NOT FOUND GROUPS");
    }


    @PostMapping("/create/group")
    public ResponseEntity<Object> createGroup(@RequestBody @Valid ParamPa param, Authentication authentication ) {
        User user = userService.get(authentication);

        String response = goalService.addPa(user, param.getPa());

        if(!response.equals("OK")) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok().build();
    }


    @PostMapping("/delete/group")
    public ResponseEntity<Object> deleteGroup(@RequestBody @Valid ParamPa param, Authentication authentication ) {
        User user = userService.get(authentication);

        String response = goalService.deletePa(user, param.getPa());

        if(!response.equals("OK")) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok().build();
    }
}
