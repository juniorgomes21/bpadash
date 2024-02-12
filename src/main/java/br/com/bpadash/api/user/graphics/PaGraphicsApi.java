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

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/graphics/pa")
public class PaGraphicsApi {

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
        User user = userService.get(authentication);

        Map<String, Integer> contagemPa = new HashMap<>();

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(date), user);

        if(bpaOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();

            List<Bpac> bpacList = bpacService.get(bpa);
            List<Bpai> bpaiList = bpaiService.get(bpa);

            bpacList.forEach(bpac -> {
                String pa = bpac.getPa();
                contagemPa.put(pa, contagemPa.getOrDefault(pa, 0) + 1);
            });

            // Contar as ocorrências de cada "pa" dentro de bpaiList
            bpaiList.forEach(bpai -> {
                String pa = bpai.getPa();
                contagemPa.put(pa, contagemPa.getOrDefault(pa, 0) + 1);
            });


            // Total de ocorrências
            int totalOcorrencias = contagemPa.values().stream().mapToInt(Integer::intValue).sum();

            // Agora você tem um Map com as contagens de cada "pa"
            // Vamos calcular a porcentagem para cada "pa"
            List<Map<String, Object>> resultado = contagemPa.entrySet().stream()
                    .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                    .map(entry -> {
                        String pa = entry.getKey();
                        int ocorrencias = entry.getValue();
                        int porcentagem = (int) Math.round(((double) ocorrencias / totalOcorrencias) * 100);
                        Map<String, Object> item = new HashMap<>();
                        item.put("pa", pa);
                        item.put("occurrences", ocorrencias);
                        item.put("percent", porcentagem);
                        return item;
                    }).collect(Collectors.toList());

            return ResponseEntity.ok(resultado.subList(0, 5));
        }

        return ResponseEntity.badRequest().body("NOT FOUND");
    }

    @GetMapping("/{year}") // TODO revisar pos não está coerente com 1 ano
    public ResponseEntity<Object> usedYear(@PathVariable Integer year, Authentication authentication) {
        User user = userService.get(authentication);

        List<Bpa> bpaList = bpaService.getForYear(user, year);


//        List<Integer> pa1 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
//        List<Integer> pa2 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
//        List<Integer> pa3 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
//        List<Integer> pa4 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));

        Map<String, List<Map.Entry<String, Integer>>> contagemPaYear = new HashMap<>();

        bpaList.forEach( bpa -> {
            Map<String, Integer> contagemPa = new HashMap<>();

            int month = bpa.getDate().getMonthValue() - 1;

            List<Bpac> bpacList = bpacService.get(bpa);
            List<Bpai> bpaiList = bpaiService.get(bpa);

            bpacList.forEach(bpac -> {
                String pa = bpac.getPa();
                contagemPa.put(pa, contagemPa.getOrDefault(pa, 0) + 1);
            });

            // Contar as ocorrências de cada "pa" dentro de bpaiList
            bpaiList.forEach(bpai -> {
                String pa = bpai.getPa();
                contagemPa.put(pa, contagemPa.getOrDefault(pa, 0) + 1);
            });

            List<Map.Entry<String, Integer>> top4 = contagemPa.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(4)
                    .toList();

            contagemPaYear.put(String.valueOf(month), top4);

        });


        return ResponseEntity.ok(contagemPaYear);
    }
}
