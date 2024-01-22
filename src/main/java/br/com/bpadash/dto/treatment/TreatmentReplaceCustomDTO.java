package br.com.bpadash.dto.treatment;

import br.com.bpadash.model.treatment.TreatmentFile;
import br.com.bpadash.services.treatment.RuleTreatmentPaService;
import br.com.bpadash.services.treatment.RuleTreatmentReplaceCustomService;

import java.util.List;

public class TreatmentReplaceCustomDTO {
    private int count;
    private List<RuleReplaceCustomDTO> ruleTreatmentReplaceCustoList;

    public TreatmentReplaceCustomDTO() {
    }

    public TreatmentReplaceCustomDTO(TreatmentFile treatmentFile) {
        this.count = treatmentFile.getCount();
        this.ruleTreatmentReplaceCustoList = RuleTreatmentReplaceCustomService.dto(treatmentFile);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<RuleReplaceCustomDTO> getRuleTreatmentReplaceCustoList() {
        return ruleTreatmentReplaceCustoList;
    }

    public void setRuleTreatmentReplaceCustoList(List<RuleReplaceCustomDTO> ruleTreatmentReplaceCustoList) {
        this.ruleTreatmentReplaceCustoList = ruleTreatmentReplaceCustoList;
    }
}
