package br.com.bpadash.api.user.graphics;

import br.com.bpadash.dto.graphics.SexGraphicsDTO;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.user.User;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.graphics.GraphicsService;
import br.com.bpadash.services.user.UserService;
import br.com.bpadash.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/graphics/sex")
public class SexGraphicsApi {
    @Autowired
    private UserService userService;
    @Autowired
    private BpaiService bpaiService;
    @Autowired
    private BpaService bpaService;
    @Autowired
    private GraphicsService graphicsService;



    @GetMapping("/per/{date}")
    public ResponseEntity<Object> used(@PathVariable String date, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(date), user);

        if(bpaOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();

            if(bpa.getManagerBpa().isCalculateSex()) {
                List<Bpai> bpaiList = bpaiService.get(bpa);

                EncryptionService.decryptSex(bpaiList);

                Map<String, Integer> porcentagensGrupos = bpaService.calculateSex(bpa, bpaiList);

                return ResponseEntity.ok(porcentagensGrupos);
            } else  {
                Map<String, Integer> contagemIdades = new HashMap<>();
                contagemIdades.put("M", bpa.getManagerBpa().getSexM());
                contagemIdades.put("F", bpa.getManagerBpa().getSexF());
                contagemIdades.put("total", bpa.getManagerBpa().getCountLineBpai());

                return ResponseEntity.ok(contagemIdades);
            }

        }

        return ResponseEntity.badRequest().body("NOT FOUND");
    }

    @GetMapping("/{year}")
    public ResponseEntity<Object> getForSex(@PathVariable Integer year, Authentication authentication) {
        User user = userService.userLogged(authentication);

        List<Bpa> bpaList = bpaService.getForYear(user, year);

        List<Integer> mans = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        List<Integer> womans = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));

        if(!bpaList.isEmpty()) {
            bpaList.forEach( bpa -> {
                int month = bpa.getDate().getMonthValue() - 1;
                int countM = bpa.getManagerBpa().getSexM();
                int countF = bpa.getManagerBpa().getSexF();

                mans.set(month, countM);
                womans.set(month, countF);
            });
        }

        Map<String, List<Integer>> graphics = new HashMap<>();

        graphics.put("mans", mans);
        graphics.put("womans", womans);

        return ResponseEntity.ok(graphics);
    }

}
