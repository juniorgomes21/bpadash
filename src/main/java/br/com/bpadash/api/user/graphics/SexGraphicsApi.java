package br.com.bpadash.api.user.graphics;

import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.user.User;
import br.com.bpadash.services.EncryptionService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    @GetMapping("/per/{date}")
    public ResponseEntity<Object> used(@PathVariable String date, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(date), user);

        if(bpaOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();

            if(bpa.getManagerBpa().isCalculateSex()) {
                List<Bpai> bpaiList = bpaiService.get(bpa);

                EncryptionService.decryptSex(bpaiList);

                Map<String, Integer> contagemIdades = new HashMap<>();

                bpaiList.forEach(bpai -> {
                    String age = bpai.getSexo();
                    contagemIdades.put(age, contagemIdades.getOrDefault(age, 0) + 1);
                });


                int totalOcorrencias = contagemIdades.values().stream().mapToInt(Integer::intValue).sum();

                // Calcular a porcentagem para cada grupo
                Map<String, Integer> porcentagensGrupos = contagemIdades.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> (int) Math.round(((double) entry.getValue() / totalOcorrencias) * 100)
                    ));

                bpaService.saveManagerSex(bpa, porcentagensGrupos);

                porcentagensGrupos.put("total", bpa.getManagerBpa().getCountLineBpai());

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
}
