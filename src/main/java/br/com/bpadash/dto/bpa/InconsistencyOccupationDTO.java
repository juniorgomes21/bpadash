package br.com.bpadash.dto.bpa;

import br.com.bpadash.dto.sigtap.ErrorOccupationDTO;

import java.util.ArrayList;
import java.util.List;

public class InconsistencyOccupationDTO {
    private List<ErrorOccupationDTO> errorsOccupationBpacDTOS = new ArrayList<>();
    private List<ErrorOccupationDTO> errorsOccupationBpaiDTOS = new ArrayList<>();

    public InconsistencyOccupationDTO(List<ErrorOccupationDTO> errorsOccupationBpacDTOS , List<ErrorOccupationDTO> errorsOccupationBpaiDTOS) {
        this.errorsOccupationBpacDTOS = errorsOccupationBpacDTOS;
        this.errorsOccupationBpaiDTOS = errorsOccupationBpaiDTOS;
    }

    public List<ErrorOccupationDTO> getErrorsOccupationBpacDTOS() {
        return errorsOccupationBpacDTOS;
    }

    public void setErrorsOccupationBpacDTOS(List<ErrorOccupationDTO> errorsOccupationBpacDTOS) {
        this.errorsOccupationBpacDTOS = errorsOccupationBpacDTOS;
    }

    public List<ErrorOccupationDTO> getErrorsOccupationBpaiDTOS() {
        return errorsOccupationBpaiDTOS;
    }

    public void setErrorsOccupationBpaiDTOS(List<ErrorOccupationDTO> errorsOccupationBpaiDTOS) {
        this.errorsOccupationBpaiDTOS = errorsOccupationBpaiDTOS;
    }
}
