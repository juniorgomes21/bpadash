package br.com.bpadash.services.treatment;

import br.com.bpadash.dto.treatment.RuleTreatmentPaDTO;
import br.com.bpadash.model.treatment.RuleTreatmentPa;
import br.com.bpadash.model.treatment.TreatmentFile;
import br.com.bpadash.params.treatment.ParamTreatmentPa;
import br.com.bpadash.params.treatment.ParamUpdateExecuteFile;
import br.com.bpadash.repository.treatment.RuleTreatmentPaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RuleTreatmentPaService {

    @Autowired
    private RuleTreatmentPaRepository ruleTreatmentPaRepository;

    public static List<RuleTreatmentPaDTO> dto(TreatmentFile treatmentFile) {
        List<RuleTreatmentPaDTO> ruleTreatmentPaDTOS = new ArrayList<>();

        treatmentFile.getRuleTreatmentPaList().forEach( rule -> {
            ruleTreatmentPaDTOS.add(new RuleTreatmentPaDTO(rule));
        });

        return ruleTreatmentPaDTOS;
    }

    public RuleTreatmentPa create(ParamTreatmentPa paramTreatmentPa) {
        return new RuleTreatmentPa(paramTreatmentPa.getPaCurrent(), paramTreatmentPa.getNewPa());
    }

    public void updateAndSave(RuleTreatmentPa ruleTreatmentPa, ParamUpdateExecuteFile paramUpdateExecuteFile) {
        ruleTreatmentPa.setExecuteBpac(paramUpdateExecuteFile.getExecuteBpac());
        ruleTreatmentPa.setExecuteBpai(paramUpdateExecuteFile.getExecuteBpai());

        this.save(ruleTreatmentPa);
    }

    public RuleTreatmentPa get(Long id) {
        return ruleTreatmentPaRepository.findById(id).get();
    }

    @Transactional
    public void delete(Long id) {
        ruleTreatmentPaRepository.deleteById(id);
    }

    public RuleTreatmentPa save(RuleTreatmentPa ruleTreatmentPa) {
        return ruleTreatmentPaRepository.save(ruleTreatmentPa);
    }

}
