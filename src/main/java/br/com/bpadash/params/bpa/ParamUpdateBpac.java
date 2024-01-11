package br.com.bpadash.params.bpa;

import br.com.bpadash.validations.bpa.BpaValid;
import br.com.bpadash.validations.bpa.org.OrgValid;

import javax.validation.constraints.NotBlank;

public class ParamUpdateBpac {

    @NotBlank(message = "O Campo não pode ser branco")
    @BpaValid(size = 2, message = "O tamanho do campo deve ser 2. O campo deverá ser preenchido apenas com números.")
    private String iden;
    @NotBlank(message = "O Campo não pode ser branco")
    @BpaValid(size = 7, message = "O tamanho do campo deve ser 7. O campo deverá ser preenchido apenas com números. Adicionar zeros à esquerda.")
    private String cnes;
    @NotBlank(message = "O Campo não pode ser branco")
    @BpaValid(size = 6, message = "O tamanho do campo deve ser 6. O campo deverá ser preenchido apenas com números. Formato AAAAMM")
    private String cmp;
    @NotBlank(message = "O Campo não pode ser branco")
    @BpaValid(size = 6, message = "O tamanho do campo deve ser 6. Código conforme a Classificação Brasileira de Ocupações (CBO)")
    private String cbo;
    @NotBlank(message = "O Campo não pode ser branco")
    @BpaValid(size = 3, message = "O tamanho do campo deve ser 3. Adicionar zeros à esquerda de um inteiro.")
    private String flh;
    @NotBlank(message = "O Campo não pode ser branco")
    @BpaValid(size = 2, message = "O tamanho do campo deve ser 2. Adicionar zeros à esquerda de um inteiro.")
    private String seq;
    @NotBlank(message = "O Campo não pode ser branco")
    @BpaValid(size = 10, message = "O tamanho do campo deve ser 10. O campo deverá ser preenchido apenas com números. Adicionar zeros à esquerda.")
    private String pa;
    @NotBlank(message = "O Campo não pode ser branco")
    @BpaValid(size = 3, message = "O tamanho do campo deve ser 3. O campo deverá ser preenchido apenas com números. Adicionar zeros à esquerda.")
    private String idade;
    @NotBlank(message = "O Campo não pode ser branco")
    @BpaValid(size = 6, message = "O tamanho do campo deve ser 6. O campo deverá ser preenchido apenas com números. Adicionar zeros à esquerda de um inteiro.")
    private String qt;
    @NotBlank(message = "O Campo não pode ser branco")
    @OrgValid
    private String org;
    @BpaValid(size = 2, message = "O tamanho do campo deve ser 2.")
    private String fim;

    public ParamUpdateBpac() {
    }

    public String getIden() {
        return iden;
    }

    public void setIden(String iden) {
        this.iden = iden;
    }

    public String getCnes() {
        return cnes;
    }

    public void setCnes(String cnes) {
        this.cnes = cnes;
    }

    public String getCmp() {
        return cmp;
    }

    public void setCmp(String cmp) {
        this.cmp = cmp;
    }

    public String getCbo() {
        return cbo;
    }

    public void setCbo(String cbo) {
        this.cbo = cbo;
    }

    public String getFlh() {
        return flh;
    }

    public void setFlh(String flh) {
        this.flh = flh;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getQt() {
        return qt;
    }

    public void setQt(String qt) {
        this.qt = qt;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }
}
