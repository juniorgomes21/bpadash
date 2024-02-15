package br.com.bpadash.api.user.graphics;

import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.bpa.ManagerBpa;
import br.com.bpadash.model.user.User;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.user.UserService;
import br.com.bpadash.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/graphics/race")
public class RaceGraphicsApi {

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
//            List<Bpai> bpaiList = bpaiService.get(bpa);
//            EncryptionService.decryptRace(bpaiList);

            ManagerBpa managerBpa = bpa.getManagerBpa();

            Map<String, Integer> contagemIdades = new HashMap<>(); //bpaService.calculateRace(bpa, bpaiList);

            contagemIdades.put("blank", managerBpa.getBlank());
            contagemIdades.put("black", managerBpa.getBlack());
            contagemIdades.put("brown", managerBpa.getBrown());
            contagemIdades.put("yellow", managerBpa.getYellow());
            contagemIdades.put("Indigenous", managerBpa.getIndigenous());
            contagemIdades.put("noInformation", managerBpa.getNoInformation());
            contagemIdades.put("total", bpa.getManagerBpa().getCountLineBpai());

            return ResponseEntity.ok(contagemIdades);
        }

        return ResponseEntity.badRequest().body("NOT FOUND");
    }
}
