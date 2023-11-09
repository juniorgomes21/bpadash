package br.com.bpadash.params.bpa;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class ParamDeleteBpac {
    private List<Long> list;

    public ParamDeleteBpac() {
    }

    public List<Long> getList() {
        return list;
    }

    public void setList(List<Long> list) {
        this.list = list;
    }
}
