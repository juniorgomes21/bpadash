package br.com.bpadash.services.sigtap;

import br.com.bpadash.dto.sigtap.DatesSigtapDTO;
import br.com.bpadash.model.DatesSigtap;
import br.com.bpadash.model.LinkCep;
import br.com.bpadash.model.User;
import br.com.bpadash.projections.DateProjection;
import br.com.bpadash.repository.sigtap.LinkCepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LinkCepService {

    @Autowired
    private LinkCepRepository linkCepRepository;

    public boolean exists(LocalDate date) {
        return linkCepRepository.existsByDate(date);
    }


    public LinkCep save(LinkCep linkCep) {
        return linkCepRepository.save(linkCep);
    }

    public List<LinkCep> save(List<LinkCep> cepList) {
        return linkCepRepository.saveAll(cepList);
    }

    public Optional<LinkCep> get() {
        return linkCepRepository.findFirstByOrderByDateDesc();
    }

    public Optional<LinkCep> get(LocalDate date) {
        return linkCepRepository.findByDate(date);
    }

    public DatesSigtapDTO getDates(DatesSigtap datesSigtap) {
        List<LinkCep> bpaiList = linkCepRepository.findAll();

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

        LocalDate dateCurrentDB = datesSigtap.getDateCep();
        if(dateCurrentDB != null ) {
            List<Integer> month = new ArrayList<>(List.of(dateCurrentDB.getMonthValue()));
            List<Integer> year = new ArrayList<>(List.of(dateCurrentDB.getYear()));
            List<List<Integer>> monthAndYear = new ArrayList<>();

            monthAndYear.add(month);
            monthAndYear.add(year);

            dateCurrent.add(monthAndYear);
        }

        return new DatesSigtapDTO(
                datesSigtap.getId(),
                "CEP",
                datesSigtap.isDateCepAuto(),
                dateCurrent,
                years,
                datesFull
        );
    }
}
