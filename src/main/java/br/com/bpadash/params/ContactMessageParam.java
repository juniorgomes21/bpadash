package br.com.bpadash.params;

import br.com.bpadash.validations.user.PackageUserValid;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ContactMessageParam {
    @NotBlank
    @Size(min = 5, max = 50, message = "O nome deve ter entre 5 a 50 caracteres")
    private String name;
    @NotBlank
    @Email
    @Size(min = 10, max = 50, message = "O email deve ter entre 10 a 50 caracteres")
    private String email;
    @NotBlank
    @PackageUserValid
    private String packageName;
    @NotBlank
    @Size(min = 10, max = 255, message = "A mensagem deve ter entre 10 a 255 caracteres")
    private String message;

    public ContactMessageParam() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
