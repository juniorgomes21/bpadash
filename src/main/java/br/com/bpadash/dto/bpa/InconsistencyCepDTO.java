package br.com.bpadash.dto.bpa;

import br.com.bpadash.dto.sigtap.ErrorCEPsInvalidsDTO;

import java.util.ArrayList;
import java.util.List;

public class InconsistencyCepDTO {
    private List<ErrorCEPsInvalidsDTO> errorsCEPs = new ArrayList<>();

    public InconsistencyCepDTO() {
    }

    public InconsistencyCepDTO(List<ErrorCEPsInvalidsDTO> errorsCEPs) {
        this.errorsCEPs = errorsCEPs;
    }

    public List<ErrorCEPsInvalidsDTO> getErrorsCEPs() {
        return errorsCEPs;
    }

    public void setErrorsCEPs(List<ErrorCEPsInvalidsDTO> errorsCEPs) {
        this.errorsCEPs = errorsCEPs;
    }
}
