package br.com.bpadash.params.professional;

import br.com.bpadash.dto.professional.ProfessionalDTO;

import java.util.List;

public class ParamNewProfessionals {
    private List<ParamNewProfessional> professionalsDTOList;

    public ParamNewProfessionals() {
    }

    public List<ParamNewProfessional> getProfessionalsDTOList() {
        return professionalsDTOList;
    }

    public void setProfessionalsDTOList(List<ParamNewProfessional> professionalsDTOList) {
        this.professionalsDTOList = professionalsDTOList;
    }
}
