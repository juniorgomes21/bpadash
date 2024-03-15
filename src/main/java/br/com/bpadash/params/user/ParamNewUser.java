package br.com.bpadash.params.user;

import br.com.bpadash.validations.user.PackageUserValid;

import javax.validation.constraints.NotBlank;

public class ParamNewUser extends ParamBase {
    @NotBlank
    private String userName;
    @NotBlank
    private String cnpj;
    @PackageUserValid
    private String packageUser;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getPackageUser() {
        return packageUser;
    }

    public void setPackageUser(String packageUser) {
        this.packageUser = packageUser;
    }
}
