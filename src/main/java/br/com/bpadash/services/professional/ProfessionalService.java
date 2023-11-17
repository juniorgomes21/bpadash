package br.com.bpadash.services.professional;

import br.com.bpadash.model.Professional;
import br.com.bpadash.model.ProfessionalComplete;
import br.com.bpadash.model.User;
import br.com.bpadash.params.professional.ParamNewProfessional;
import br.com.bpadash.repository.professional.ProfessionalCompleteRepository;
import br.com.bpadash.repository.professional.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessionalService {

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private ProfessionalCompleteRepository professionalCompleteRepository;

    public List<Professional> create(List<ParamNewProfessional> paramNewProfessionals, User user) {
        List<Professional> professionals = new ArrayList<>();
        paramNewProfessionals.forEach(paramNewProfessional -> {
            professionals.add(new Professional(paramNewProfessional, user));
        });

        return professionals;
    }

    public ProfessionalComplete save(ProfessionalComplete professional) {
        return professionalCompleteRepository.save(professional);
    }

    public List<ProfessionalComplete> save(List<ProfessionalComplete> professionalList) {
        return professionalCompleteRepository.saveAll(professionalList);
    }

    public List<Integer> exist(List<ParamNewProfessional> paramNewProfessionals) {

        List<Integer> integers = new ArrayList<>();
        paramNewProfessionals.forEach( professional -> {
            Optional<Professional> professionalOptional = professionalRepository.findByCns(professional.getCns());
            if(professionalOptional.isPresent()) {
                integers.add(professional.getId());
            }
        });

        return integers;
    }
}
