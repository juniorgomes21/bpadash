package br.com.bpadash.params.user;

import br.com.bpadash.validations.user.PackageUserValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ParamNewUser extends ParamBase {
    @NotBlank
    @Size(min = 5, max = 50, message = "O nome deve ter entre 5 a 50 caracteres")
    private String nameRoot;
    @NotBlank
    @Size(min = 14, max = 14, message = "O CNPJ deve ter 14 caracteres")
    private String cnpj;
    @PackageUserValid
    private String packageUser;
    @NotBlank
    @Size(min = 8, max = 50, message = "Senha de administrador deve ter entre 8 a 50 caracteres")
    private String passwordAdm;

    public String getNameRoot() {
        return nameRoot;
    }

    public void setNameRoot(String nameRoot) {
        this.nameRoot = nameRoot;
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

    public String getPasswordAdm() {
        return passwordAdm;
    }

    public void setPasswordAdm(String passwordAdm) {
        this.passwordAdm = passwordAdm;
    }
}
