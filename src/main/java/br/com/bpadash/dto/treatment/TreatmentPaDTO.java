package br.com.bpadash.dto.treatment;

import br.com.bpadash.model.treatment.RuleTreatmentPa;
import br.com.bpadash.model.treatment.TreatmentFile;
import br.com.bpadash.services.treatment.RuleTreatmentPaService;

import java.util.List;

public class TreatmentPaDTO {
    private int count;
    private List<RuleTreatmentPaDTO> ruleTreatmentPaList;

    public TreatmentPaDTO(TreatmentFile treatmentFile) {
        this.count = treatmentFile.getCount();
        this.ruleTreatmentPaList = RuleTreatmentPaService.dto(treatmentFile);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<RuleTreatmentPaDTO> getRuleTreatmentPaList() {
        return ruleTreatmentPaList;
    }

    public void setRuleTreatmentPaList(List<RuleTreatmentPaDTO> ruleTreatmentPaList) {
        this.ruleTreatmentPaList = ruleTreatmentPaList;
    }
}
