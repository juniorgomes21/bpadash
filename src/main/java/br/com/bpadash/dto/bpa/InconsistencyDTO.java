package br.com.bpadash.dto.bpa;

import br.com.bpadash.dto.sigtap.ErrorCEPsDTO;
import br.com.bpadash.dto.sigtap.ErrorPaDTO;

import java.util.ArrayList;
import java.util.List;

public class InconsistencyDTO {
    private List<ErrorCEPsDTO> errorsCEPs = new ArrayList<>();
    private List<ErrorPaDTO> errorsPaBpacDTOS  = new ArrayList<>();
    private List<ErrorPaDTO> errorsPaBpaiDTOS = new ArrayList<>();
    private List<BpacDTO> bpacList = new ArrayList<>();
    private List<BpaiDTO> bpaiList = new ArrayList<>();

    public InconsistencyDTO() {
    }

    public InconsistencyDTO(List<ErrorCEPsDTO> errorsCEPs , List<ErrorPaDTO> errorsPaBpacDTOS , List<ErrorPaDTO> errorsPaBpaiDTOS , List<BpacDTO> bpacList , List<BpaiDTO> bpaiList) {
        this.errorsCEPs = errorsCEPs;
        this.errorsPaBpacDTOS = errorsPaBpacDTOS;
        this.errorsPaBpaiDTOS = errorsPaBpaiDTOS;
        this.bpacList = bpacList;
        this.bpaiList = bpaiList;
    }

    public List<ErrorCEPsDTO> getErrorsCEPs() {
        return errorsCEPs;
    }

    public void setErrorsCEPs(List<ErrorCEPsDTO> errorsCEPs) {
        this.errorsCEPs = errorsCEPs;
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

    public List<BpacDTO> getBpacList() {
        return bpacList;
    }

    public void setBpacList(List<BpacDTO> bpacList) {
        this.bpacList = bpacList;
    }

    public List<BpaiDTO> getBpaiList() {
        return bpaiList;
    }

    public void setBpaiList(List<BpaiDTO> bpaiList) {
        this.bpaiList = bpaiList;
    }
}
