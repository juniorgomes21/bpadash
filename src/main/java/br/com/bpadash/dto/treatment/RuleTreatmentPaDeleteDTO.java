package br.com.bpadash.dto.treatment;

import br.com.bpadash.model.treatment.RuleTreatmentPaCbo;
import br.com.bpadash.model.treatment.RuleTreatmentPaDelete;

public class RuleTreatmentPaDeleteDTO {
    private Long id;
    private String pa;
    private boolean executeBpac;
    private boolean executeBpai;


    public RuleTreatmentPaDeleteDTO() {
    }

    public RuleTreatmentPaDeleteDTO(RuleTreatmentPaDelete ruleTreatmentPaDelete) {
        this.id = ruleTreatmentPaDelete.getId();
        this.pa = ruleTreatmentPaDelete.getPa();
        this.executeBpac = ruleTreatmentPaDelete.isExecuteBpac();
        this.executeBpai = ruleTreatmentPaDelete.isExecuteBpai();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public boolean isExecuteBpac() {
        return executeBpac;
    }

    public void setExecuteBpac(boolean executeBpac) {
        this.executeBpac = executeBpac;
    }

    public boolean isExecuteBpai() {
        return executeBpai;
    }

    public void setExecuteBpai(boolean executeBpai) {
        this.executeBpai = executeBpai;
    }
}
