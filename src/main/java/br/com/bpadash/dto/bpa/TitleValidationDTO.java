package br.com.bpadash.dto.bpa;

import br.com.bpadash.model.bpa.TitleValidation;

public class TitleValidationDTO {
    private boolean lin;
    private boolean flh;
    private boolean smtVrf;
    private boolean cgccpf;

    public TitleValidationDTO() {
    }

    public TitleValidationDTO(TitleValidation titleValidation) {
        this.lin = titleValidation.isLin();
        this.flh = titleValidation.isFlh();
        this.smtVrf = titleValidation.isSmtVrf();
        this.cgccpf = titleValidation.isCgccpf();
    }

    public boolean isLin() {
        return lin;
    }

    public void setLin(boolean lin) {
        this.lin = lin;
    }

    public boolean isFlh() {
        return flh;
    }

    public void setFlh(boolean flh) {
        this.flh = flh;
    }

    public boolean isSmtVrf() {
        return smtVrf;
    }

    public void setSmtVrf(boolean smtVrf) {
        this.smtVrf = smtVrf;
    }

    public boolean isCgccpf() {
        return cgccpf;
    }

    public void setCgccpf(boolean cgccpf) {
        this.cgccpf = cgccpf;
    }
}
