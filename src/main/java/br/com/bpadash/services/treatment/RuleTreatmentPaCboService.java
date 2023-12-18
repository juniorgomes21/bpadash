package br.com.bpadash.services.treatment;

import br.com.bpadash.model.treatment.RuleTreatmentPaCbo;
import br.com.bpadash.params.treatment.ParamTreatmentPaCbo;
import br.com.bpadash.params.treatment.ParamUpdateExecuteFile;
import br.com.bpadash.repository.treatment.RuleTreatmentPaCboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RuleTreatmentPaCboService {

    @Autowired
    private RuleTreatmentPaCboRepository ruleTreatmentPaCboRepository;

    public RuleTreatmentPaCbo create(ParamTreatmentPaCbo paramTreatmentPaCbo) {
        return new RuleTreatmentPaCbo(paramTreatmentPaCbo);
    }

    public RuleTreatmentPaCbo get(Long id) {
        return ruleTreatmentPaCboRepository.findById(id).get();
    }

    public void updateAndSave(RuleTreatmentPaCbo ruleTreatmentPaCbo, ParamUpdateExecuteFile paramUpdateExecuteFile) {
        ruleTreatmentPaCbo.setExecuteBpac(paramUpdateExecuteFile.getExecuteBpac());
        ruleTreatmentPaCbo.setExecuteBpai(paramUpdateExecuteFile.getExecuteBpai());

        this.save(ruleTreatmentPaCbo);
    }

    public RuleTreatmentPaCbo save(RuleTreatmentPaCbo ruleTreatmentPaCbo) {
        return ruleTreatmentPaCboRepository.save(ruleTreatmentPaCbo);
    }

    @Transactional
    public void delete(Long id) {
        ruleTreatmentPaCboRepository.deleteById(id);
    }
}
