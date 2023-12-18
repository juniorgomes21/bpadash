package br.com.bpadash.dto.treatment;

import br.com.bpadash.model.treatment.TreatmentFile;
import br.com.bpadash.services.treatment.RuleTreatmentPaDeleteService;
import br.com.bpadash.services.treatment.RuleTreatmentPaService;

import java.util.List;

public class TreatmentPaDeleteDTO {
    private int count;
    private List<RuleTreatmentPaDeleteDTO> ruleTreatmentPaDeleteList;


    public TreatmentPaDeleteDTO(TreatmentFile treatmentFile) {
        this.count = treatmentFile.getCount();
        this.ruleTreatmentPaDeleteList = RuleTreatmentPaDeleteService.dto(treatmentFile);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<RuleTreatmentPaDeleteDTO> getRuleTreatmentPaDeleteList() {
        return ruleTreatmentPaDeleteList;
    }

    public void setRuleTreatmentPaDeleteList(List<RuleTreatmentPaDeleteDTO> ruleTreatmentPaDeleteList) {
        this.ruleTreatmentPaDeleteList = ruleTreatmentPaDeleteList;
    }
}
