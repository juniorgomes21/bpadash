package br.com.bpadash.services.user;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class ParamLogin {

    @NotBlank
    @Size(min = 10, max = 50)
    private String email;
    @NotBlank
    @Size(min = 8, max = 50)
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsernamePasswordAuthenticationToken converter() {
        return new UsernamePasswordAuthenticationToken(EnCryptionAESService.hashString(email), password);
    }
}
