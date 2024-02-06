package br.com.bpadash.dto;

import br.com.bpadash.model.user.User;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.user.StorageService;

public class UserDTO {

    private Long key;
    private String name;
    private String email;
    private String cnpj;
    private String cell;
    private String packageName;
    private String packageTotalRules;
    private int countBpa = 0;
    private int countRules = 0;
    private boolean valid;
    private boolean changePass;
    private String storageTotal;
    private AddressDTO address;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.key = EncryptionService.encryptKeyUser(user.getId());
        this.name = EncryptionService.decrypt(user.getName());
        this.email = EncryptionService.decrypt(user.getEmail());
        this.cell = EncryptionService.decrypt(user.getCell());
        this.cnpj = EncryptionService.decrypt(user.getCnpj());
        this.packageName = this.formatPackageName(EncryptionService.decrypt(user.getPackageNameUser()));
        this.packageTotalRules = EncryptionService.decrypt(user.getPackageNumberRules());
        this.valid = user.isValid();
        this.countBpa = user.getBpas().size();
        this.countRules = this.countTotalRules(user);
        this.changePass = user.isChangePass();
        this.storageTotal = StorageService.formatBytes(user.getStorageTotal());
        this.address = new AddressDTO(EncryptionService.decryptAddressUser(user.getAddressUser()));
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
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

    public int getCountRules() {
        return countRules;
    }

    public void setCountRules(int countRules) {
        this.countRules = countRules;
    }

    public int getCountBpa() {
        return countBpa;
    }

    public void setCountBpa(int countBpa) {
        this.countBpa = countBpa;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageTotalRules() {
        return packageTotalRules;
    }

    public void setPackageTotalRules(String packageTotalRules) {
        this.packageTotalRules = packageTotalRules;
    }

    public boolean isChangePass() {
        return changePass;
    }

    public void setChangePass(boolean changePass) {
        this.changePass = changePass;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getStorageTotal() {
        return storageTotal;
    }

    public void setStorageTotal(String storageTotal) {
        this.storageTotal = storageTotal;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    private int countTotalRules(User user) {
        int rules = user.getTreatmentFile().getRuleTreatmentPaList().size();
        int rules1 = user.getTreatmentFile().getRuleTreatmentPaCboList().size();
        int rules2 = user.getTreatmentFile().getRuleTreatmentPaDeleteList().size();
        int rules3 = user.getTreatmentFile().getRuleReplacementCustoms().size();

        return rules + rules1 + rules2 + rules3;
    }

    private String formatPackageName(String packageName) {

        return packageName.equals("bronze") ? packageName : packageName.equals("gold") ? "ouro" : "platinha";
    }

}
