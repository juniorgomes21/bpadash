package br.com.bpadash.params.bpa;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class ParamDeleteBpac {
    @NotBlank
    private String identifier;
    private List<Long> list;

    public ParamDeleteBpac() {
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<Long> getList() {
        return list;
    }

    public void setList(List<Long> list) {
        this.list = list;
    }
}
