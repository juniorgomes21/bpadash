package br.com.bpadash.dto.bpa;

import br.com.bpadash.dto.sigtap.ErrorPaDTO;
import br.com.bpadash.dto.sigtap.ErrorProcedureDTO;

import java.util.ArrayList;
import java.util.List;

public class InconsistencyProcedureDTO {
    private List<ErrorPaDTO> errorsPaBpac  = new ArrayList<>();
    private List<ErrorProcedureDTO> errorsSexBpai = new ArrayList<>();

    public InconsistencyProcedureDTO() {
    }

    public InconsistencyProcedureDTO(List<ErrorPaDTO> errorsPaBpac , List<ErrorProcedureDTO> errorsSexBpai) {
        this.errorsPaBpac = errorsPaBpac;
        this.errorsSexBpai = errorsSexBpai;
    }

    public List<ErrorPaDTO> getErrorsPaBpac() {
        return errorsPaBpac;
    }

    public void setErrorsPaBpac(List<ErrorPaDTO> errorsPaBpac) {
        this.errorsPaBpac = errorsPaBpac;
    }

    public List<ErrorProcedureDTO> getErrorsSexBpai() {
        return errorsSexBpai;
    }

    public void setErrorsSexBpai(List<ErrorProcedureDTO> errorsSexBpai) {
        this.errorsSexBpai = errorsSexBpai;
    }
}
