package br.com.bpadash.api.user.graphics;

import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.ManagerBpa;
import br.com.bpadash.model.user.User;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.user.UserService;
import br.com.bpadash.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/graphics/age")
public class AgeGraphicsApi {

    @Autowired
    private UserService userService;
    @Autowired
    private BpaiService bpaiService;
    @Autowired
    private BpaService bpaService;

    @GetMapping("/{date}")
    public ResponseEntity<Object> used(@PathVariable String date, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(date), user);

        if(bpaOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();

            ManagerBpa managerBpa = bpa.getManagerBpa();

            Map<String, Integer> contagemIdades = new HashMap<>();

            contagemIdades.put("yong", managerBpa.getYong());
            contagemIdades.put("middleAge", managerBpa.getMiddleAge());
            contagemIdades.put("old", managerBpa.getOld());
            contagemIdades.put("total", managerBpa.getCountLineBpai());

            return ResponseEntity.ok(contagemIdades);
        }

        return ResponseEntity.badRequest().body("NOT FOUND");
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<Object> usedYear(@PathVariable Integer year, Authentication authentication) {
        User user = userService.userLogged(authentication);

        List<Bpa> bpaList = bpaService.getForYear(user, year);

        List<Integer> yongs = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        List<Integer> middleAges = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        List<Integer> olds = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));

        if(!bpaList.isEmpty()) {
            bpaList.forEach( bpa -> {
                int month = bpa.getDate().getMonthValue() - 1;
                int yong = bpa.getManagerBpa().getYong();
                int middleAge = bpa.getManagerBpa().getMiddleAge();
                int old = bpa.getManagerBpa().getOld();

                yongs.set(month, yong);
                middleAges.set(month, middleAge);
                olds.set(month, old);
            });
        }

        Map<String, List<Integer>> graphics = new HashMap<>();

        graphics.put("yongs", yongs);
        graphics.put("middleAges", middleAges);
        graphics.put("olds", olds);

        return ResponseEntity.ok(graphics);
    }
}
