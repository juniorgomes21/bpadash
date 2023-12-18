package br.com.bpadash.dto.treatment;

import br.com.bpadash.model.treatment.RuleTreatmentPa;

public class RuleTreatmentPaDTO {
    private Long id;
    private String paCurrent;
    private String paNew;
    private boolean executeBpac;
    private boolean executeBpai;


    public RuleTreatmentPaDTO() {
    }

    public RuleTreatmentPaDTO(RuleTreatmentPa ruleTreatmentPa) {
        this.id = ruleTreatmentPa.getId();
        this.paCurrent = ruleTreatmentPa.getPaCurrent();
        this.paNew = ruleTreatmentPa.getPaNew();
        this.executeBpac = ruleTreatmentPa.isExecuteBpac();
        this.executeBpai = ruleTreatmentPa.isExecuteBpai();
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

    public String getPaNew() {
        return paNew;
    }

    public void setPaNew(String paNew) {
        this.paNew = paNew;
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
