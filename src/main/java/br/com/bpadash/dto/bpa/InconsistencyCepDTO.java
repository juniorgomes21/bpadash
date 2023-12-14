package br.com.bpadash.dto.bpa;

import br.com.bpadash.dto.sigtap.ErrorCEPsDTO;

import java.util.ArrayList;
import java.util.List;

public class InconsistencyCepDTO {
    private List<ErrorCEPsDTO> errorsCEPs = new ArrayList<>();

    public InconsistencyCepDTO() {
    }

    public InconsistencyCepDTO(List<ErrorCEPsDTO> errorsCEPs) {
        this.errorsCEPs = errorsCEPs;
    }

    public List<ErrorCEPsDTO> getErrorsCEPs() {
        return errorsCEPs;
    }

    public void setErrorsCEPs(List<ErrorCEPsDTO> errorsCEPs) {
        this.errorsCEPs = errorsCEPs;
    }
}
