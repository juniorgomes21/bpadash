package br.com.bpadash.services.professional;

import br.com.bpadash.dto.DatesDTO;
import br.com.bpadash.dto.sigtap.DatesSigtapDTO;
import br.com.bpadash.model.DatesSigtap;
import br.com.bpadash.model.LinkProfessionals;
import br.com.bpadash.model.ProfessionalComplete;
import br.com.bpadash.model.User;
import br.com.bpadash.projections.DateProjection;
import br.com.bpadash.repository.professional.LinkProfessionalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LinkProfessionalsService {

    @Autowired
    private LinkProfessionalsRepository linkProfessionalsRepository;


    public LinkProfessionals save(LinkProfessionals linkProfessionals) {
        return linkProfessionalsRepository.save(linkProfessionals);
    }

    public List<LinkProfessionals> save(List<LinkProfessionals> linkProfessionals) {
        return linkProfessionalsRepository.saveAll(linkProfessionals);
    }

    public void addAndSave(List<ProfessionalComplete> completeList , LinkProfessionals linkProfessionals) {
        linkProfessionals.getProfessionalCompleteList().addAll(completeList);

        this.save(linkProfessionals);
    }

    public boolean exist(LocalDate date) {
        return linkProfessionalsRepository.findByDate(date).isPresent();
    }

    public Optional<LinkProfessionals> get(User user) {
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        return linkProfessionalsRepository.findFirstByUser(user, sort);
    }

    public Optional<LinkProfessionals> get(LocalDate date , User user) {

        return linkProfessionalsRepository.findByDateAndUser(date, user);
    }

    public DatesSigtapDTO getDates(User user, DatesSigtap datesSigtap) {
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        List<DateProjection> bpaiList = linkProfessionalsRepository.findAllByUser(user, DateProjection.class, sort);

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
                "OCU",
                datesSigtap.isDateProfessionalsAuto(),
                dateCurrent,
                years,
                datesFull
        );
    }

    public DatesDTO getDates(User user) {
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        List<DateProjection> projectionList = linkProfessionalsRepository.findAllByUser(user, DateProjection.class, sort);

        DatesDTO datesDTO = new DatesDTO();
        projectionList.forEach( linkFpoListf -> {
            LocalDate date = linkFpoListf.getDate();

            List<Integer> dateList = new ArrayList<>();
            dateList.add(date.getMonthValue());
            dateList.add(date.getYear());

            datesDTO.getDates().add(dateList);
        });

        return datesDTO;
    }
}
