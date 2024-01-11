package br.com.bpadash.services.sigtap;

import br.com.bpadash.dto.sigtap.DatesSigtapDTO;
import br.com.bpadash.model.DatesSigtap;
import br.com.bpadash.model.LinkProcedure;
import br.com.bpadash.model.User;
import br.com.bpadash.repository.sigtap.LinkProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LinkProcedureService {

    @Autowired
    private LinkProcedureRepository linkProcedureRepository;

    public Optional<LinkProcedure> get() {
        return linkProcedureRepository.findFirstByOrderByDateDesc();
    }

    public Optional<LinkProcedure> get(LocalDate date) {
        return linkProcedureRepository.findByDate(date);
    }

    public LinkProcedure save(LinkProcedure linkProcedure) {
        return linkProcedureRepository.save(linkProcedure);
    }

    public List<LinkProcedure> saveAll(List<LinkProcedure> procedureList) {
        return linkProcedureRepository.saveAll(procedureList);
    }

    public boolean exists(LocalDate date) {
        return linkProcedureRepository.existsByDate(date);
    }

    public DatesSigtapDTO getDates(DatesSigtap datesSigtap) {
        List<LinkProcedure> bpaiList = linkProcedureRepository.findAll();

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

        LocalDate dateCurrentDB = datesSigtap.getDateFpo();
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
                "Procedimento",
                datesSigtap.isDateProcedureAuto(),
                dateCurrent,
                years,
                datesFull
        );
    }
}
