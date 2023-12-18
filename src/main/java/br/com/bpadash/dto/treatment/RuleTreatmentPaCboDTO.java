package br.com.bpadash.dto.treatment;

import br.com.bpadash.model.treatment.RuleTreatmentPa;
import br.com.bpadash.model.treatment.RuleTreatmentPaCbo;

public class RuleTreatmentPaCboDTO {
    private Long id;
    private String paCurrent;
    private String cboCurrent;
    private String cboNew;
    private boolean executeBpac;
    private boolean executeBpai;


    public RuleTreatmentPaCboDTO() {
    }

    public RuleTreatmentPaCboDTO(RuleTreatmentPaCbo ruleTreatmentPaCbo) {
        this.id = ruleTreatmentPaCbo.getId();
        this.paCurrent = ruleTreatmentPaCbo.getPa();
        this.cboCurrent = ruleTreatmentPaCbo.getPa();
        this.cboNew = ruleTreatmentPaCbo.getCboNew();
        this.executeBpac = ruleTreatmentPaCbo.isExecuteBpac();
        this.executeBpai = ruleTreatmentPaCbo.isExecuteBpai();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaCurrent() {
        return paCurrent;
    }

    public void setPaCurrent(String paCurrent) {
        this.paCurrent = paCurrent;
    }

    public String getCboCurrent() {
        return cboCurrent;
    }

    public void setCboCurrent(String cboCurrent) {
        this.cboCurrent = cboCurrent;
    }

    public String getCboNew() {
        return cboNew;
    }

    public void setCboNew(String cboNew) {
        this.cboNew = cboNew;
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
