package br.com.bpadash.model.treatment;

import br.com.bpadash.dto.treatment.RuleTreatmentPaCboDTO;
import br.com.bpadash.dto.treatment.RuleTreatmentPaDTO;
import br.com.bpadash.params.treatment.ParamTreatmentPaCbo;
import br.com.bpadash.services.treatment.RuleTreatmentPaService;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RuleTreatmentPaCbo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pa;
    private String cboCurrent;
    private String cboNew;
    private boolean executeBpac = true;
    private boolean executeBpai = true;

    public RuleTreatmentPaCbo() {
    }

    public RuleTreatmentPaCbo(ParamTreatmentPaCbo paramTreatmentPaCbo) {
        this.pa = paramTreatmentPaCbo.getPa();
        this.cboCurrent = paramTreatmentPaCbo.getCboCurrent();
        this.cboNew = paramTreatmentPaCbo.getCboNew();
    }

    public static List<RuleTreatmentPaCboDTO> dto(TreatmentFile treatmentFile) {
        List<RuleTreatmentPaCboDTO> ruleTreatmentPaDTOS = new ArrayList<>();

        treatmentFile.getRuleTreatmentPaCboList().forEach( rule -> {
            ruleTreatmentPaDTOS.add(new RuleTreatmentPaCboDTO(rule));
        });

        return ruleTreatmentPaDTOS;
    }


    public Long getId() {
        return id;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
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
