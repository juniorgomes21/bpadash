package br.com.bpadash.api.user.graphics;

import br.com.bpadash.dto.graphics.CountLineBpaForYearGraphicsDTO;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.user.User;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.fpo.LinkFpoService;
import br.com.bpadash.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/graphics/bpa")
public class BpaGraphicsApi {

    @Autowired
    private BpaService bpaService;
    @Autowired
    private UserService userService;
    @Autowired
    private LinkFpoService linkFpoService;

    @GetMapping("/procedures/per/month/{year}")
    public ResponseEntity<Object> calculateProcedure(@PathVariable int year, Authentication authentication) {
        User user = userService.get(authentication);

        List<Bpa> bpaList = bpaService.getForYear(user, year).stream().sorted(Comparator.comparing(Bpa::getDate).reversed()).toList();

        if(!bpaList.isEmpty()) {

            List<Map<Integer, Integer>> countProcessed = new ArrayList<>();
            bpaList.forEach( bpa -> {

                int valeuTotalProcedures = 0;
                if(bpa.getManagerBpa().isCalculateCountLine()) {
                    valeuTotalProcedures = bpaService.calculateProcedure(bpa);
                } else {
                    valeuTotalProcedures = bpa.getManagerBpa().getCountLineBpac() +  bpa.getManagerBpa().getCountLineBpai();
                }

                Map<Integer, Integer> articleMapOne = new HashMap<>();
                articleMapOne.put(bpa.getDate().getMonthValue(), valeuTotalProcedures);

                countProcessed.add(articleMapOne);
            });

            return ResponseEntity.ok(countProcessed);
        }

        return ResponseEntity.badRequest().body(new CountLineBpaForYearGraphicsDTO());
    }


    /**
     * Calcula o faturamento dos BPA por mês caso ainda não tenha sido calculado. Caso contrário devolve o faturamento.
     * @param authentication
     * @return
     */
    @GetMapping("/invoicing/per/month/{year}")
    public ResponseEntity<Object> calculateInvoicingPerMonth(@PathVariable int year, Authentication authentication) {
        User user = userService.get(authentication);

        List<Bpa> bpaList = bpaService.getForYear(user, year);

        if(!bpaList.isEmpty()) {
            List<Map<Integer, BigDecimal>> countProcessed = new ArrayList<>();

            bpaList.forEach( bpa -> {
                Map<Integer, BigDecimal> articleMapOne = new HashMap<>();
                articleMapOne.put(bpa.getDate().getMonthValue(), bpa.getManagerBpa().getInvoicing());

                countProcessed.add(articleMapOne);
            });

            return ResponseEntity.ok(countProcessed);
        }

        return ResponseEntity.badRequest().body(new CountLineBpaForYearGraphicsDTO());
    }

}
