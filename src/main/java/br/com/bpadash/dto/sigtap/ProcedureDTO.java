package br.com.bpadash.dto.sigtap;

import br.com.bpadash.model.Procedure;

public class ProcedureDTO {

    private String naProcedimento;

    public ProcedureDTO() {
    }

    public ProcedureDTO(Procedure procedure) {
        this.naProcedimento = procedure.getNaProcedimento();
    }

    public String getNaProcedimento() {
        return naProcedimento;
    }

    public void setNaProcedimento(String naProcedimento) {
        this.naProcedimento = naProcedimento;
    }
}
