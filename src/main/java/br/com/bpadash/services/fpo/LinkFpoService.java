package br.com.bpadash.services.fpo;

import br.com.bpadash.dto.DatesDTO;
import br.com.bpadash.dto.sigtap.DatesSigtapDTO;
import br.com.bpadash.model.DatesSigtap;
import br.com.bpadash.model.Fpo;
import br.com.bpadash.model.LinkFpo;
import br.com.bpadash.model.User;
import br.com.bpadash.projections.DateProjection;
import br.com.bpadash.repository.fpo.LinkFpoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LinkFpoService {

    @Autowired
    private LinkFpoRepository linkFpoRepository;


    public LinkFpo save(LinkFpo linkFpo) {
       return linkFpoRepository.save(linkFpo);
    }

    public List<LinkFpo> save(List<LinkFpo> linkFpoList) {
        return linkFpoRepository.saveAll(linkFpoList);
    }

    public Optional<LinkFpo> get(User user) {
        Sort sort = Sort.by(Sort.Direction.DESC, "date");

        return linkFpoRepository.findFirstByUser(user, sort);
    }

    public Optional<LinkFpo> get(LocalDate date, User user) {
        return linkFpoRepository.findByUserAndDate(user, date);
    }

    public void addFpos(LinkFpo linkFpo, List<Fpo> fpoList) {
        linkFpo.getFpoList().addAll(fpoList);
        this.save(linkFpo);
    }

    public DatesDTO getDates(User user) {
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        List<DateProjection> linkFpoList = linkFpoRepository.findAllByUser(user, DateProjection.class, sort);

        DatesDTO datesDTO = new DatesDTO();
        linkFpoList.forEach( linkFpoListf -> {
            LocalDate date = linkFpoListf.getDate();

            List<Integer> dateList = new ArrayList<>();
            dateList.add(date.getMonthValue());
            dateList.add(date.getYear());

            datesDTO.getDates().add(dateList);
        });

        return datesDTO;
    }

    public DatesSigtapDTO getDates(User user, DatesSigtap datesSigtap) {
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        List<DateProjection> bpaiList = linkFpoRepository.findAllByUser(user, DateProjection.class, sort);

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
                "FPO",
                datesSigtap.isDateFpoAuto(),
                dateCurrent,
                years,
                datesFull
        );
    }

    public boolean exists(LocalDate date , User user) {
        return linkFpoRepository.existsByDateAndUser(date, user);
    }
}
