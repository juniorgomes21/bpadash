package br.com.bpadash.services.sigtap;

import br.com.bpadash.dto.sigtap.DatesSigtapDTO;
import br.com.bpadash.model.DatesSigtap;
import br.com.bpadash.model.LinkOccupation;
import br.com.bpadash.model.User;
import br.com.bpadash.projections.DateProjection;
import br.com.bpadash.repository.sigtap.LinkOccupationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LinkOccupationService {

    @Autowired
    private LinkOccupationRepository linkOccupationRepository;

    public Optional<LinkOccupation> get() {
        return linkOccupationRepository.findFirstByOrderByDateDesc();
    }

    public Optional<LinkOccupation> get(LocalDate date) {
        return linkOccupationRepository.findByDate(date);
    }

    public LinkOccupation save(LinkOccupation linkOccupation) {
        return linkOccupationRepository.save(linkOccupation);
    }

    public List<LinkOccupation> save(List<LinkOccupation> linkOccupationList) {
        return linkOccupationRepository.saveAll(linkOccupationList);
    }

    public DatesSigtapDTO getDates(DatesSigtap datesSigtap) {
        List<LinkOccupation> bpaiList = linkOccupationRepository.findAll();

        List<Integer> years = new ArrayList<>();
        List<List<List<Integer>>> dateCurrent = new ArrayList<>();
        List<List<List<Integer>>> datesFull = new ArrayList<>();

        if (!bpaiList.isEmpty()) {
            bpaiList.forEach(linkFpoListf -> {
                LocalDate date = linkFpoListf.getDate();

                int year = date.getYear();
                int month = date.getMonthValue();

                // Verifica se o ano já foi adicionado
                if (!years.contains(year)) {
                    List<Integer> months = new ArrayList<>();
                    List<Integer> yearList = new ArrayList<>();
                    List<List<Integer>> monthsAndYears = new ArrayList<>();

                    years.add(year);

                    // Adiciona a lista de meses e anos ao ano correspondente
                    monthsAndYears.add(months);
                    monthsAndYears.add(yearList);
                    datesFull.add(monthsAndYears);
                }

                // Adiciona o mês à lista de meses
                List<List<Integer>> yearData = datesFull.get(years.indexOf(year));
                List<Integer> months = yearData.get(0);

                if (!months.contains(month)) {
                    months.add(month);
                }

                // Adiciona o ano à lista de anos correspondente em datesFull
                if(!yearData.get(1).contains(year)) {
                    yearData.get(1).add(year);
                }
            });
        }

        LocalDate dateCurrentDB = datesSigtap.getDateOccupation();
        if(dateCurrentDB != null) {
            List<Integer> month = new ArrayList<>(List.of(dateCurrentDB.getMonthValue()));
            List<Integer> year = new ArrayList<>(List.of(dateCurrentDB.getYear()));
            List<List<Integer>> monthAndYear = new ArrayList<>();

            monthAndYear.add(month);
            monthAndYear.add(year);

            dateCurrent.add(monthAndYear);
        }

        return new DatesSigtapDTO(
                datesSigtap.getId(),
                "Ocupação",
                datesSigtap.isDateOccupationAuto(),
                dateCurrent,
                years,
                datesFull
        );
    }

}
