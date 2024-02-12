package br.com.bpadash.services.professional;

import br.com.bpadash.dto.DatesDTO;
import br.com.bpadash.dto.sigtap.DatesSigtapDTO;
import br.com.bpadash.model.sigtap.LinkFpo;
import br.com.bpadash.model.user.User;
import br.com.bpadash.model.sigtap.DatesSigtap;
import br.com.bpadash.model.sigtap.LinkProfessionals;
import br.com.bpadash.model.sigtap.ProfessionalComplete;
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


    public LinkProfessionals get(Long id) {
        return linkProfessionalsRepository.getById(id);
    }

    public Optional<LinkProfessionals> get(User user) {
        return linkProfessionalsRepository.findFirstByUser(user , Sort.by(Sort.Direction.DESC , "date"));
    }

    public List<LinkProfessionals> getAll(User user) {
        return linkProfessionalsRepository.findByUser(user);
    }

    public Optional<LinkProfessionals> get(LocalDate date, User user) {

        return linkProfessionalsRepository.findByDateAndUser(date, user);
    }

    public DatesDTO getDates(User user) {
        List<DateProjection> projectionList = linkProfessionalsRepository.findAllByUser(user, DateProjection.class, Sort.by(Sort.Direction.DESC, "date"));

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

    public DatesSigtapDTO getDates(DatesSigtap datesSigtap, User user) {
        List<DateProjection> bpaiList = linkProfessionalsRepository.findAllByUser(user, DateProjection.class, Sort.by(Sort.Direction.DESC, "date"));

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
                "Profissionais",
                datesSigtap.isDateProfessionalsAuto(),
                dateCurrent,
                years,
                datesFull
        );
    }

    public boolean haveFile(User user) {
        return linkProfessionalsRepository.findFirstByUser(user).isPresent();
    }

    public void addAndSave(List<ProfessionalComplete> completeList , LinkProfessionals linkProfessionals) {
        linkProfessionals.getProfessionalCompleteList().addAll(completeList);

        this.save(linkProfessionals);
    }

    public boolean exist(LocalDate date) {
        return linkProfessionalsRepository.findByDate(date).isPresent();
    }

    public boolean exist(User user) {
        return linkProfessionalsRepository.existsByUser(user);
    }

    public LinkProfessionals save(LinkProfessionals linkProfessionals) {
        return linkProfessionalsRepository.save(linkProfessionals);
    }

    public List<LinkProfessionals> save(List<LinkProfessionals> linkProfessionals) {
        return linkProfessionalsRepository.saveAll(linkProfessionals);
    }

    public void delete(LinkProfessionals linkProfessionals) {
        linkProfessionalsRepository.delete(linkProfessionals);
    }

    public Long calculateByte(User user) {
        List<LinkProfessionals> linkProfessionalsList = this.getAll(user);

        Long totalByte = 0L;

        for (LinkProfessionals linkProfessionals: linkProfessionalsList) {
            totalByte = totalByte + linkProfessionals.getFileSizeInBytes();
        }

        return totalByte;
    }
}
