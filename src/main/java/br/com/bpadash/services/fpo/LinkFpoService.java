package br.com.bpadash.services.fpo;

import br.com.bpadash.dto.DatesDTO;
import br.com.bpadash.dto.sigtap.DatesSigtapDTO;
import br.com.bpadash.model.user.User;
import br.com.bpadash.model.sigtap.DatesSigtap;
import br.com.bpadash.model.sigtap.LinkFpo;
import br.com.bpadash.projections.DateProjection;
import br.com.bpadash.repository.fpo.LinkFpoRepository;
import br.com.bpadash.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LinkFpoService {

    @Autowired
    private LinkFpoRepository linkFpoRepository;

    public Optional<LinkFpo> get(User user) {
        return linkFpoRepository.findFirstByUser(user, Sort.by(Sort.Direction.DESC, "date"));
    }

    public LinkFpo get(Long id) {
        return linkFpoRepository.getById(id);
    }

    public List<LinkFpo> getAll(User user) {
        return linkFpoRepository.findByUser(user);
    }

    public Optional<LinkFpo> get(LocalDate date, User user) {
        return linkFpoRepository.findByUserAndDate(user, date);
    }

    public Optional<LinkFpo> verify(User user) {
        DatesSigtap datesSigtap = user.getDatesSigtap();

        Optional<LinkFpo> linkFpoOptional;
        if(datesSigtap.isDateFpoAuto()) {
            linkFpoOptional = this.get(user);
        } else {
            linkFpoOptional = this.get(datesSigtap.getDateFpo(), user);
        }

        return linkFpoOptional;
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

    public DatesSigtapDTO getDates(DatesSigtap datesSigtap, User user) {
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        List<DateProjection> bpaiList = linkFpoRepository.findAllByUser(user, DateProjection.class, sort);

        List<Integer> years = new ArrayList<>();
        List<List<List<Integer>>> dateCurrent = new ArrayList<>();
        List<List<List<Integer>>> datesFull = new ArrayList<>();

        if (!bpaiList.isEmpty()) {
            bpaiList.forEach( linkFpoListf -> {
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

    public boolean haveFile(User user) {
        return linkFpoRepository.findFirstByUser(user).isPresent();
    }

    public boolean exists(LocalDate date , User user) {
        return linkFpoRepository.existsByDateAndUser(date, user);
    }

    public LinkFpo save(LinkFpo linkFpo) {
        return linkFpoRepository.save(linkFpo);
    }

    public List<LinkFpo> save(List<LinkFpo> linkFpoList) {
        return linkFpoRepository.saveAll(linkFpoList);
    }

    public void delete(LinkFpo link) {
        linkFpoRepository.delete(link);
    }

    public Long calculateByte(User user) {
        List<LinkFpo> linkFpoList = this.getAll(user);

        Long totalByte = 0L;

        for (LinkFpo linkFpo: linkFpoList) {
            totalByte = totalByte + linkFpo.getFileSizeInBytes();
        }

        return totalByte;
    }

    public boolean exist(User user) {
        return linkFpoRepository.existsByUser(user);
    }
}
