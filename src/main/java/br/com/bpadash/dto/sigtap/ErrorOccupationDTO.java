package br.com.bpadash.dto.sigtap;

import br.com.bpadash.model.Bpac;
import br.com.bpadash.model.Bpai;

public class ErrorOccupationDTO extends ErrorSigTapDTO{
    private String cbo;
    private String pa;

    public ErrorOccupationDTO() {
    }

    public ErrorOccupationDTO(Long id, String flh, String seq, String cbo, String pa, String msg) {
        super(id, msg, flh, seq);
        this.cbo = cbo;
        this.pa = pa;
    }


    public String getCbo() {
        return cbo;
    }

    public void setCbo(String cbo) {
        this.cbo = cbo;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }
}
