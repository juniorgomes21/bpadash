package br.com.bpadash.dto.treatment;

import br.com.bpadash.model.treatment.RuleTreatmentPa;
import br.com.bpadash.model.treatment.RuleTreatmentPaCbo;
import br.com.bpadash.model.treatment.TreatmentFile;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class TreatmentPaCboDTO {
    private int count;
    private List<RuleTreatmentPaCboDTO> ruleTreatmentPaCboList;

    public TreatmentPaCboDTO(TreatmentFile treatmentFile) {
        this.count = treatmentFile.getCount();
        this.ruleTreatmentPaCboList = RuleTreatmentPaCbo.dto(treatmentFile);
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<RuleTreatmentPaCboDTO> getRuleTreatmentPaCboList() {
        return ruleTreatmentPaCboList;
    }

    public void setRuleTreatmentPaCboList(List<RuleTreatmentPaCboDTO> ruleTreatmentPaCboList) {
        this.ruleTreatmentPaCboList = ruleTreatmentPaCboList;
    }
}
