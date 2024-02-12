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
@RequestMapping("/api/graphics/cnsmed")
public class CnsmedGraphicsApi {

    @Autowired
    private UserService userService;
    @Autowired
    private BpaiService bpaiService;
    @Autowired
    private BpaService bpaService;

    @GetMapping("/used/{date}")
    public ResponseEntity<Object> used(@PathVariable String date, Authentication authentication) {
        User user = userService.userLogged(authentication);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(date), user);

        if (bpaOptional.isPresent()) {

            Map<String, Integer> contagemCnsmed = new HashMap<>();

            Bpa bpa = bpaOptional.get();

            List<Bpai> bpaiList = bpaiService.get(bpa);

            EncryptionService.decryptCnsmed(bpaiList);

            bpaiList.forEach(bpai -> {
                String cnsmed = bpai.getCnsmed();
                contagemCnsmed.put(cnsmed, contagemCnsmed.getOrDefault(cnsmed, 0) + 1);
            });

//            int totalOcorrencias = contagemCnsmed.values().stream().mapToInt(Integer::intValue).sum();
            int totalOcorrencias = bpa.getManagerBpa().getCountLineBpai();

            List<Map<String, Object>> resultado = contagemCnsmed.entrySet().stream()
                    .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                    .map(entry -> {
                        String cnsmed = entry.getKey();
                        int ocorrencias = entry.getValue();
                        int porcentagem = (int) Math.round(((double) ocorrencias / totalOcorrencias) * 100);
                        Map<String, Object> item = new HashMap<>();
                        item.put("cnsmed", cnsmed);
                        item.put("occurrences", ocorrencias);
                        item.put("percent", porcentagem);
                        return item;
                    }).collect(Collectors.toList());

            return ResponseEntity.ok(resultado);
        }

        return ResponseEntity.badRequest().body("NOT FOUND");
    }
}
