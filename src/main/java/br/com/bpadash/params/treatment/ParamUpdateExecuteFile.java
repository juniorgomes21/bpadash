package br.com.bpadash.params.treatment;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;

public class ParamUpdateExecuteFile {

    @Pattern(regexp = "true|false", message = "O valor deve ser true ou false")
    private String executeBpac;
    @Pattern(regexp = "true|false", message = "O valor deve ser true ou false")
    private String executeBpai;

    public ParamUpdateExecuteFile() {
    }

    public boolean getExecuteBpac() {
        return executeBpac.equals("true");
    }

    public void setExecuteBpac(String executeBpac) {
        this.executeBpac = executeBpac;
    }

    public boolean getExecuteBpai() {
        return executeBpai.equals("true");
    }

    public void setExecuteBpai(String executeBpai) {
        this.executeBpai = executeBpai;
    }
}
