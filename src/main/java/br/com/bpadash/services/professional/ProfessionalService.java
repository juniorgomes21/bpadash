package br.com.bpadash.services.professional;

import br.com.bpadash.dto.sigtap.ErrorCnsmedDTO;
import br.com.bpadash.dto.sigtap.ErrorSigTapDTO;
import br.com.bpadash.model.*;
import br.com.bpadash.params.professional.ParamNewProfessional;
import br.com.bpadash.params.professional.ParamUpdateProfessional;
import br.com.bpadash.repository.professional.ProfessionalCompleteRepository;
import br.com.bpadash.repository.professional.ProfessionalRepository;
import br.com.bpadash.services.EncryptionService;
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

    public List<ErrorSigTapDTO> verifyErrors(List<Bpai> bpaiListDB , List<ProfessionalComplete> professionalCompleteList) {
        List<ErrorSigTapDTO> errors = new ArrayList<>();

        List<String> cnsmedList = new ArrayList<>();
        for(Bpai bpai: bpaiListDB) {

            String cnsmed = bpai.getCnsmed();

            if(!cnsmedList.contains(cnsmed)) {
                long count = bpaiListDB.stream().filter( bpaiC -> bpaiC.getCnsmed().equals(cnsmed)).count();

                Optional<ProfessionalComplete> professionalCompleteOptional = professionalCompleteList.stream().filter(professional -> professional.getCodCns().equals(cnsmed)).findFirst();

                if(professionalCompleteOptional.isEmpty()) {
                    errors.add(new ErrorCnsmedDTO(bpai.getId(), bpai.getFlh(), bpai.getSeq(), "CNSMED NOT FOUND", bpai.getPa(), cnsmed, count));
                }
            }

            cnsmedList.add(cnsmed);
        }

        return errors;
    }

    public ProfessionalComplete get(Long id) {
        Optional<ProfessionalComplete> professionalCompleteOptional = professionalCompleteRepository.findById(id);

        return professionalCompleteOptional.orElse(null);
    }

    public ProfessionalComplete get(LinkProfessionals linkProfessionals , String idProfessional) {
        Optional<ProfessionalComplete> professionalCompleteOptional = professionalCompleteRepository.findByKeyProfIdAndLinkProfessionals(EncryptionService.hashString(idProfessional), linkProfessionals);

        return professionalCompleteOptional.orElse(null);
    }

    public void updateAndSave(ProfessionalComplete professionalComplete, ParamUpdateProfessional paramUpdateProfessional) {
        professionalComplete.setProfId(EncryptionService.encrypt(paramUpdateProfessional.getProfId()));
        professionalComplete.setCpf(EncryptionService.encrypt(paramUpdateProfessional.getCpf()));
        professionalComplete.setName(EncryptionService.encrypt(paramUpdateProfessional.getName()));
        professionalComplete.setLogradouro(EncryptionService.encrypt(paramUpdateProfessional.getLogradouro()));
        professionalComplete.setNumber(EncryptionService.encrypt(paramUpdateProfessional.getNumber()));
        professionalComplete.setComplement(EncryptionService.encrypt(paramUpdateProfessional.getComplement()));
        professionalComplete.setBairrodist(EncryptionService.encrypt(paramUpdateProfessional.getBairrodist()));
        professionalComplete.setCodCep(EncryptionService.encrypt(paramUpdateProfessional.getCodCep()));
        professionalComplete.setCodCns(EncryptionService.encrypt(paramUpdateProfessional.getCodCns()));
        professionalComplete.setTelephone(EncryptionService.encrypt(paramUpdateProfessional.getTelephone()));
        professionalComplete.getDadosVinc().setCodCbo(EncryptionService.encrypt(paramUpdateProfessional.getCodCbo()));

        this.save(professionalComplete);
    }
}
