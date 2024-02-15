package br.com.bpadash.api.user.metrics;

import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpac;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.sigtap.Fpo;
import br.com.bpadash.model.sigtap.LinkFpo;
import br.com.bpadash.model.user.User;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpacService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.fpo.FpoService;
import br.com.bpadash.services.fpo.LinkFpoService;
import br.com.bpadash.services.user.UserService;
import br.com.bpadash.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/metrics/cbo")
public class CboMetricsApi {

        @Autowired
        private UserService userService;
        @Autowired
        private BpaiService bpaiService;
        @Autowired
        private BpacService bpacService;
        @Autowired
        private BpaService bpaService;
        @Autowired
        private LinkFpoService linkFpoService;
        @Autowired
        private FpoService fpoService;

        @GetMapping("/{date}")
        public ResponseEntity<Object> used(@PathVariable String date, Authentication authentication) {
            User user = userService.get(authentication);

            Map<String, Integer> contagemPa = new HashMap<>();

            Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(date), user);

            if(bpaOptional.isPresent()) {
                Bpa bpa = bpaOptional.get();

                List<Bpac> bpacList = bpacService.get(bpa);
                List<Bpai> bpaiList = bpaiService.get(bpa);

                bpacList.forEach(bpac -> {
                    String cbo = bpac.getCbo();
                    contagemPa.put(cbo, contagemPa.getOrDefault(cbo, 0) + 1);
                });

                // Contar as ocorrências de cada "pa" dentro de bpaiList
                bpaiList.forEach(bpai -> {
                    String cbo = bpai.getCbo();
                    contagemPa.put(cbo, contagemPa.getOrDefault(cbo, 0) + 1);
                });


                // Total de ocorrências
                int totalOcorrencias = contagemPa.values().stream().mapToInt(Integer::intValue).sum();

                // Agora você tem um Map com as contagens de cada "pa"
                // Vamos calcular a porcentagem para cada "pa"
                List<Map<String, Object>> resultado = contagemPa.entrySet().stream()
                        .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                        .map(entry -> {
                            String cbo = entry.getKey();
                            int ocorrencias = entry.getValue();
                            int porcentagem = (int) Math.round(((double) ocorrencias / totalOcorrencias) * 100);

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
