package br.com.bpadash.services.sigtap;

import br.com.bpadash.model.sigtap.DatesSigtap;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.sigtap.ParamUpdateDateSigtap;
import br.com.bpadash.repository.sigtap.DatesSigtapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatesSigtapService {

    @Autowired
    private DatesSigtapRepository datesSigtapRepository;

    public DatesSigtap create(User user) {
        DatesSigtap datesSigtap = new DatesSigtap(user);

        return this.save(datesSigtap);
    }

    public DatesSigtap save(DatesSigtap datesSigtap) {
        return datesSigtapRepository.save(datesSigtap);
    }

    public List<DatesSigtap> save(List<DatesSigtap> datesSigtapList) {
        return datesSigtapRepository.saveAll(datesSigtapList);
    }

    public DatesSigtap get(User user) {
        return datesSigtapRepository.findByUser(user);
    }

    public DatesSigtap get(Long id, User user) {
        return datesSigtapRepository.findByIdAndUser(id, user);
    }

    public void update(DatesSigtap datesSigtap , ParamUpdateDateSigtap paramUpdateDateSigtap) {
        switch (paramUpdateDateSigtap.getArqName()) {
            case "FPO" -> {
                datesSigtap.setDateFpoAuto(paramUpdateDateSigtap.isAuto());
                datesSigtap.setDateFpo(paramUpdateDateSigtap.getLocalDateCurrent());
            }
            case "PROF" -> {
                datesSigtap.setDateProfessionalsAuto(paramUpdateDateSigtap.isAuto());
                datesSigtap.setDateProfessionals(paramUpdateDateSigtap.getLocalDateCurrent());
            }
        }

        this.save(datesSigtap);
    }
}
