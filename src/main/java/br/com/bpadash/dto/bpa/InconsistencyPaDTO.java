package br.com.bpadash.dto.bpa;

import br.com.bpadash.dto.sigtap.ErrorPaDTO;

import java.util.ArrayList;
import java.util.List;

public class InconsistencyPaDTO {
    private List<ErrorPaDTO> errorsPaBpacDTOS  = new ArrayList<>();
    private List<ErrorPaDTO> errorsPaBpaiDTOS = new ArrayList<>();

    public InconsistencyPaDTO() {
    }

    public InconsistencyPaDTO(List<ErrorPaDTO> errorsPaBpacDTOS , List<ErrorPaDTO> errorsPaBpaiDTOS) {
        this.errorsPaBpacDTOS = errorsPaBpacDTOS;
        this.errorsPaBpaiDTOS = errorsPaBpaiDTOS;
    }

    public List<ErrorPaDTO> getErrorsPaBpacDTOS() {
        return errorsPaBpacDTOS;
    }

    public void setErrorsPaBpacDTOS(List<ErrorPaDTO> errorsPaBpacDTOS) {
        this.errorsPaBpacDTOS = errorsPaBpacDTOS;
    }

    public List<ErrorPaDTO> getErrorsPaBpaiDTOS() {
        return errorsPaBpaiDTOS;
    }

    public void setErrorsPaBpaiDTOS(List<ErrorPaDTO> errorsPaBpaiDTOS) {
        this.errorsPaBpaiDTOS = errorsPaBpaiDTOS;
    }
}
