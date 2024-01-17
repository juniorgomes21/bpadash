package br.com.bpadash.dto.bpa;

import br.com.bpadash.model.User;

public class ValidationsDTO {
    private TitleValidationDTO titleValidation;
    private BpacValidationDTO bpacValidation;
    private BpaiValidationDTO bpaiValidation;

    public ValidationsDTO() {
    }

    public ValidationsDTO(User user) {
        this.titleValidation = new TitleValidationDTO(user.getTitleValidation());
        this.bpacValidation = new BpacValidationDTO(user.getBpacValidation());
        this.bpaiValidation = new BpaiValidationDTO(user.getBpaiValidation());
    }

    public TitleValidationDTO getTitleValidation() {
        return titleValidation;
    }

    public void setTitleValidation(TitleValidationDTO titleValidation) {
        this.titleValidation = titleValidation;
    }

    public BpacValidationDTO getBpacValidation() {
        return bpacValidation;
    }

    public void setBpacValidation(BpacValidationDTO bpacValidation) {
        this.bpacValidation = bpacValidation;
    }

    public BpaiValidationDTO getBpaiValidation() {
        return bpaiValidation;
    }

    public void setBpaiValidation(BpaiValidationDTO bpaiValidation) {
        this.bpaiValidation = bpaiValidation;
    }
}
