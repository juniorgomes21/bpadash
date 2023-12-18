package br.com.bpadash.services.treatment;

import br.com.bpadash.dto.treatment.RuleTreatmentPaDeleteDTO;
import br.com.bpadash.model.treatment.RuleTreatmentPaDelete;
import br.com.bpadash.model.treatment.TreatmentFile;
import br.com.bpadash.params.treatment.ParamTreatmentPaDelete;
import br.com.bpadash.params.treatment.ParamUpdateExecuteFile;
import br.com.bpadash.repository.treatment.RuleTreatmentPaDeleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RuleTreatmentPaDeleteService {

    @Autowired
    private RuleTreatmentPaDeleteRepository ruleTreatmentPaDeleteRepository;


    public static List<RuleTreatmentPaDeleteDTO> dto(TreatmentFile treatmentFile) {
        List<RuleTreatmentPaDeleteDTO> ruleTreatmentPaDTOS = new ArrayList<>();

        treatmentFile.getRuleTreatmentPaDeleteList().forEach(rule -> {
            ruleTreatmentPaDTOS.add(new RuleTreatmentPaDeleteDTO(rule));
        });

        return ruleTreatmentPaDTOS;
    }

    public RuleTreatmentPaDelete get(Long id) {
        return ruleTreatmentPaDeleteRepository.findById(id).get();
    }

    public RuleTreatmentPaDelete create(ParamTreatmentPaDelete paramTreatmentPaDelete) {
        return new RuleTreatmentPaDelete(paramTreatmentPaDelete.getPa());
    }

    public void updateAndSave(RuleTreatmentPaDelete ruleTreatmentPaDelete , ParamUpdateExecuteFile paramUpdateExecuteFile) {
        ruleTreatmentPaDelete.setExecuteBpac(paramUpdateExecuteFile.getExecuteBpac());
        ruleTreatmentPaDelete.setExecuteBpai(paramUpdateExecuteFile.getExecuteBpai());

        this.save(ruleTreatmentPaDelete);
    }

    public RuleTreatmentPaDelete save(RuleTreatmentPaDelete ruleTreatmentPaDelete) {
        return ruleTreatmentPaDeleteRepository.save(ruleTreatmentPaDelete);
    }

    @Transactional
    public void delete(Long id) {
        ruleTreatmentPaDeleteRepository.deleteById(id);
    }
}
