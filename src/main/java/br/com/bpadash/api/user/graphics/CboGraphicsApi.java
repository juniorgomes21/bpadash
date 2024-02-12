package br.com.bpadash.api.user.graphics;

import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpac;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.user.User;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpacService;
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
@RequestMapping("/api/graphics/cbo")
public class CboGraphicsApi {

    @Autowired
    private UserService userService;
    @Autowired
    private BpaiService bpaiService;
    @Autowired
    private BpacService bpacService;
    @Autowired
    private BpaService bpaService;

    @GetMapping("/used/{date}")
    public ResponseEntity<Object> used(@PathVariable String date, Authentication authentication) {
        User user = userService.userLogged(authentication);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(date), user);

        if (bpaOptional.isPresent()) {

            Map<String, Integer> contagemCbo = new HashMap<>();

            Bpa bpa = bpaOptional.get();

            List<Bpac> bpacList = bpacService.get(bpa);
            List<Bpai> bpaiList = bpaiService.get(bpa);

            bpacList.forEach(bpac -> {
                String cbo = bpac.getCbo();
                contagemCbo.put(cbo, contagemCbo.getOrDefault(cbo, 0) + 1);
            });

            bpaiList.forEach(bpai -> {
                String cbo = bpai.getCbo();
                contagemCbo.put(cbo, contagemCbo.getOrDefault(cbo, 0) + 1);
            });

            int totalProcedures = bpa.getManagerBpa().getCountLineBpai() + bpa.getManagerBpa().getCountLineBpac();

            List<Map<String, Object>> resultado = contagemCbo.entrySet().stream()
                    .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                    .map( entry -> {
                        String cbo = entry.getKey();
                        int ocorrencias = entry.getValue();
                        int porcentagem = (int) Math.round(((double) ocorrencias / totalProcedures) * 100);

                        Map<String, Object> item = new HashMap<>();

                        item.put("cbo", cbo);
                        item.put("occurrences", ocorrencias);
                        item.put("percent", porcentagem);

                        return item;

                    }).collect(Collectors.toList());


            return ResponseEntity.ok(resultado);
        }

        return ResponseEntity.badRequest().body("NOT FOUND");
    }
}
