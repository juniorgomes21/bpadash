package br.com.bpadash.dto.bpa;

import br.com.bpadash.dto.sigtap.ErrorCEPsBlankDTO;
import br.com.bpadash.dto.sigtap.ErrorCEPsInvalidsDTO;
import br.com.bpadash.dto.sigtap.ErrorOccupationDTO;

import java.util.ArrayList;
import java.util.List;

public class IncosistencyErrorCEPsDTO {
    private List<ErrorCEPsInvalidsDTO> errorsCEPs = new ArrayList<>();
    private List<ErrorCEPsBlankDTO> errorsCEPsBlank = new ArrayList<>();

    public IncosistencyErrorCEPsDTO() {
    }

    public IncosistencyErrorCEPsDTO(List<ErrorCEPsInvalidsDTO> errorsCEPs, List<ErrorCEPsBlankDTO> errorsCEPsBlank) {
        this.errorsCEPs = errorsCEPs;
        this.errorsCEPsBlank = errorsCEPsBlank;
    }

    public List<ErrorCEPsInvalidsDTO> getErrorsCEPs() {
        return errorsCEPs;
    }

    public void setErrorsCEPs(List<ErrorCEPsInvalidsDTO> errorsCEPs) {
        this.errorsCEPs = errorsCEPs;
    }

    public List<ErrorCEPsBlankDTO> getErrorsCEPsBlank() {
        return errorsCEPsBlank;
    }

    public void setErrorsCEPsBlank(List<ErrorCEPsBlankDTO> errorsCEPsBlank) {
        this.errorsCEPsBlank = errorsCEPsBlank;
    }
}
