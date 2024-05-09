package br.com.bpadash.services.goal;

import br.com.bpadash.dto.user.BugetDTO;
import br.com.bpadash.dto.user.GoalDTO;
import br.com.bpadash.model.GroupsPa.GroupsPa;
import br.com.bpadash.model.sigtap.Fpo;
import br.com.bpadash.model.sigtap.LinkFpo;
import br.com.bpadash.model.user.User;
import br.com.bpadash.services.fpo.FpoService;
import br.com.bpadash.services.fpo.LinkFpoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoalService {

    @Autowired
    private FpoService fpoService;
    @Autowired
    private GroupsService groupsService;

    public Page<GoalDTO> calculateGoal(LinkFpo linkFpo, String pa, Pageable pageable) {

        Page<Fpo> fpoListPage;
        if(pa.equals("null")) {
            fpoListPage = fpoService.getPageable(linkFpo, pageable);
        } else {
            fpoListPage = fpoService.getPaPageable(linkFpo, pa, pageable);
        }

        List<GoalDTO> goalDTOList = this.calculate(fpoListPage.getContent());

        return new PageImpl<>(goalDTOList, pageable, fpoListPage.getTotalElements());
    }

    public List<GoalDTO> calculateGoalPerGroup(LinkFpo linkFpo , List<String> paList) {

        List<Fpo> fpoList = linkFpo.getFpoList();

        return paList.stream()
                .map(pa -> calculateGroup(pa, fpoList))
                .collect(Collectors.toList());
    }

    public BugetDTO calculateBuget(LinkFpo linkFpo) {

        int totalOrcada = 0;
        int totalProd = 0;
        BigDecimal totalValueOrcad = BigDecimal.ZERO;
        BigDecimal totalValueProd = BigDecimal.ZERO;

        for (Fpo fpo : linkFpo.getFpoList()) {
            if (!fpo.getPa().endsWith("0000000")) {
                totalOrcada += fpo.getQuantOrcada();
                totalProd += fpo.getQuantProd();
                totalValueOrcad = totalValueOrcad.add(fpo.getValueOrcado());
                totalValueProd = totalValueProd.add(fpo.getValueProd());
            }
        }

        return new BugetDTO(totalOrcada, totalProd, totalValueOrcad, totalValueProd);
    }

    /**
     * Calcula a meta anual dos arquivos FPOs.
     * @return
     */
    public List<List<Map<Integer, BigDecimal>>> calculateBugetYearly(List<LinkFpo> linkFpoList) {

        List<Map<Integer, BigDecimal>> qtProdList = new ArrayList<>();
        List<Map<Integer, BigDecimal>> qtOrcadList = new ArrayList<>();

        for(LinkFpo linkFpo: linkFpoList) {

            BigDecimal totalValueProd = BigDecimal.ZERO;
            BigDecimal totalValueOrcad = BigDecimal.ZERO;

            for (Fpo fpo : linkFpo.getFpoList()) {
                if (!fpo.getPa().endsWith("0000000")) {
                    totalValueOrcad = totalValueOrcad.add(fpo.getValueOrcado());
                    totalValueProd = totalValueProd.add(fpo.getValueProd());
                }
            }

            int date = linkFpo.getDate().getMonthValue();

            BigDecimal finalTotalValueProd = totalValueProd;
            BigDecimal finalTotalValueOrcad = totalValueOrcad;

            qtProdList.add(new HashMap<>() {{
                put(date , finalTotalValueProd);
            }});

            qtOrcadList.add(new HashMap<>() {{
                put(date , finalTotalValueOrcad);
            }});
        }

        return new ArrayList<>(List.of(qtProdList, qtOrcadList));
    }

    /**
     * Calcula a meta do arquivo FPO agrupando por PA determinado pelo usuário.
     * @param fpoList
     * @return List<GoalDTO>
     */
    private GoalDTO calculateGroup(String paParam, List<Fpo> fpoList) {
        List<Fpo> fpoProcess = fpoList.stream()
                .filter(fpo -> fpo.getPa().startsWith(paParam))
                .toList();

        int qtProd = fpoProcess.stream().mapToInt(Fpo::getQuantProd).sum();
        int qtOrcada = fpoProcess.stream().mapToInt(Fpo::getQuantOrcada).sum();
        BigDecimal total = fpoProcess.stream()
                .map(fpo -> BigDecimal.valueOf(fpo.getQuantProd()).multiply(fpo.getValueUnit()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        double percent = ((double) qtProd / qtOrcada) * 100;
        percent = Math.round(percent * 100.0) / 100.0; // Arredondar para 2 casas decimais

        return new GoalDTO(paParam, qtOrcada, qtProd, percent, total, false);
    }


    /**
     * Calcula a meta do arquivo FPO.
     * @param fpoList
     * @return List<GoalDTO>
     */
    private List<GoalDTO> calculate(List<Fpo> fpoList) {

        List<GoalDTO> goalDTOList = new ArrayList<>();

        fpoList = fpoList.stream()
                .filter(fpo -> !fpo.getPa().endsWith("0000000"))
                .toList();

        fpoList.forEach( fpo -> {

            int qtProd = fpo.getQuantProd();
            int qtOcada = fpo.getQuantOrcada();

            double percent = ((double) qtProd / qtOcada) * 100;

            BigDecimal valueUnitary = fpo.getValueUnit();
            BigDecimal valueTotal = fpo.getValueApro();

            BigDecimal total = BigDecimal.valueOf(qtProd).multiply(valueUnitary);


            goalDTOList.add(new GoalDTO(fpo, total, Math.round(percent * 100.0) / 100.0, (valueTotal.compareTo(total)) == 0));
        });

        return goalDTOList;
    }

    public String addPa(User user , String pa) {
        Optional<GroupsPa> groupsPaOptional = groupsService.get(user);

        GroupsPa groupsPa;
        if(groupsPaOptional.isPresent()) {

            groupsPa = groupsService.addPa(groupsPaOptional.get(), pa);

            if(groupsPa == null) return "MAX LENGTH";

        } else {
            groupsPa = groupsService.create(user, pa);
        }

        groupsService.save(groupsPa);

        return "OK";
    }


    public String deletePa(User user , String pa) {
        Optional<GroupsPa> groupsPaOptional = groupsService.get(user);

        GroupsPa groupsPa;
        if(groupsPaOptional.isPresent()) {
            groupsPa = groupsService.deletePa(groupsPaOptional.get(), pa);

        } else {
            return "NOT FOUND GROUP";
        }

        groupsService.save(groupsPa);

        return "OK";
    }
}
