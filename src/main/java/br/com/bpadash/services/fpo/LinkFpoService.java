package br.com.bpadash.services.fpo;

import br.com.bpadash.dto.fpo.FpoDatesDTO;
import br.com.bpadash.model.Fpo;
import br.com.bpadash.model.LinkFpo;
import br.com.bpadash.model.User;
import br.com.bpadash.projections.FpoDateProjection;
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

    public Optional<LinkFpo> get(User user, LocalDate date) {
        return linkFpoRepository.findByUserAndDate(user, date);
    }

    public void addFpos(LinkFpo linkFpo, List<Fpo> fpoList) {
        linkFpo.getFpoList().addAll(fpoList);
        this.save(linkFpo);
    }

    public FpoDatesDTO getDates(User user) {
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        List<FpoDateProjection> linkFpoList = linkFpoRepository.findAllByUser(user, FpoDateProjection.class, sort);

        FpoDatesDTO fpoDatesDTO = new FpoDatesDTO();
        linkFpoList.forEach( linkFpoListf -> {
            LocalDate date = linkFpoListf.getDate();

            List<Integer> dateList = new ArrayList<>();
            dateList.add(date.getMonthValue());
            dateList.add(date.getYear());

            fpoDatesDTO.getDates().add(dateList);
        });

        return fpoDatesDTO;
    }
}
