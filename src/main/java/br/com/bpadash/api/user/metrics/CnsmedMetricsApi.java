package br.com.bpadash.api.user.metrics;

import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpac;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.sigtap.Fpo;
import br.com.bpadash.model.sigtap.LinkFpo;
import br.com.bpadash.model.sigtap.LinkProfessionals;
import br.com.bpadash.model.sigtap.ProfessionalComplete;
import br.com.bpadash.model.user.User;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpacService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.fpo.FpoService;
import br.com.bpadash.services.fpo.LinkFpoService;
import br.com.bpadash.services.professional.LinkProfessionalsService;
import br.com.bpadash.services.professional.ProfessionalService;
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
@RequestMapping("/api/metrics/cnsmed")
public class CnsmedMetricsApi {

        @Autowired
        private UserService userService;
        @Autowired
        private BpaiService bpaiService;
        @Autowired
        private BpacService bpacService;
        @Autowired
        private BpaService bpaService;
        @Autowired
        private LinkProfessionalsService linkProfessionalsService;
        @Autowired
        private ProfessionalService professionalService;

        @GetMapping("/{date}")
        public ResponseEntity<Object> used(@PathVariable String date, Authentication authentication) {
            User user = userService.get(authentication);

            Map<String, Integer> contagemCnsmed = new HashMap<>();

            Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(date), user);
            Optional<LinkProfessionals> linkProfessionalsOptional = linkProfessionalsService.verify(user);

            if(bpaOptional.isPresent() && linkProfessionalsOptional.isPresent()) {
                Bpa bpa = bpaOptional.get();

                List<ProfessionalComplete> professionalList = linkProfessionalsOptional.get().getProfessionalCompleteList();

                List<Bpai> bpaiList = bpaiService.get(bpa);

                EncryptionService.decryptCnsmed(bpaiList);
                EncryptionService.decryptProfessionalCnsAndName(professionalList);

                bpaiList.forEach(bpai -> {
                    String cnsmed = bpai.getCnsmed();
                    contagemCnsmed.put(cnsmed, contagemCnsmed.getOrDefault(cnsmed, 0) + 1);
                });


                // Total de ocorrências
                int totalOcorrencias = contagemCnsmed.values().stream().mapToInt(Integer::intValue).sum();

                // Agora você tem um Map com as contagens de cada "pa"
                // Vamos calcular a porcentagem para cada "pa"
                List<Map<String, Object>> resultado = contagemCnsmed.entrySet().stream()
                        .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                        .map(entry -> {
                            String cnsmed = entry.getKey();
                            int ocorrencias = entry.getValue();
                            int porcentagem = (int) Math.round(((double) ocorrencias / totalOcorrencias) * 100);

                            Optional<ProfessionalComplete> professional = professionalList.stream().filter(prof -> prof.getCodCns().equals(cnsmed)).findFirst();
                            String nameProfe = "Profissional não encontrado";
                            if(professional.isPresent()) {
                                ProfessionalComplete prof = professional.get();
                                nameProfe = prof.getName();
                            }
                            Map<String, Object> item = new HashMap<>();
                            item.put("cnsmed", cnsmed);
                            item.put("name", nameProfe);
                            item.put("occurrences", ocorrencias);
                            item.put("percent", porcentagem);
                            return item;
                        }).collect(Collectors.toList());



                return ResponseEntity.ok(resultado);
            }

            return ResponseEntity.badRequest().body("NOT FOUND");
        }

}
