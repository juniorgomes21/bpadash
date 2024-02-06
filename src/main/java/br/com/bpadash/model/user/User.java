package br.com.bpadash.model.user;

import br.com.bpadash.model.bpa.*;
import br.com.bpadash.model.enumModel.Role;
import br.com.bpadash.model.enumModel.ZoneTime;
import br.com.bpadash.model.sigtap.DatesSigtap;
import br.com.bpadash.model.treatment.TreatmentFile;
import br.com.bpadash.services.EncryptionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cnpj;
    @Column(unique = true)
    private String keyCnpj;
    private String email;
    @Column(unique = true)
    private String keyEmail;
    private String password;
    private String cell;
    private String profile = Role.USER.name();
    private Long storageUsed = 0L;
    private Long storageFree = 0L;
    private Long storageTotal = 0L;
    @NotBlank
    private String packageNameUser;
    private String packageNumberRules;
    private boolean valid = true;
    private boolean changePass = false;
    private boolean termsAndUse = true;
    private String dateCreateAccount = EncryptionService.encrypt(String.valueOf(LocalDateTime.now(ZoneId.of(ZoneTime.BR.getBr()))));
    private String lastLogin = EncryptionService.encrypt(String.valueOf(LocalDateTime.now(ZoneId.of(ZoneTime.BR.getBr()))));
    @OneToMany(fetch = FetchType.EAGER)
    private List<Bpa> bpas = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    private TitleValidation titleValidation = new TitleValidation();
    @OneToOne(cascade = CascadeType.ALL)
    private BpacValidation bpacValidation = new BpacValidation();
    @OneToOne(cascade = CascadeType.ALL)
    private BpaiValidation bpaiValidation = new BpaiValidation();
    @OneToOne(cascade = CascadeType.ALL)
    private TreatmentFile treatmentFile;
    @OneToOne(cascade = CascadeType.ALL)
    private AddressUser addressUser;
    @OneToOne(cascade = CascadeType.ALL)
    private DatesSigtap datesSigtap = new DatesSigtap();


    public User() {}


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getPackageNameUser() {
        return packageNameUser;
    }

    public void setPackageNameUser(String packageNameUser) {
        this.packageNameUser = packageNameUser;
    }

    public String getPackageNumberRules() {
        return packageNumberRules;
    }

    public void setPackageNumberRules(String packageNumberRules) {
        this.packageNumberRules = packageNumberRules;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getProfile() {
        return profile;
    }

    public String getKeyCnpj() {
        return keyCnpj;
    }

    public void setKeyCnpj(String keyCnpj) {
        this.keyCnpj = keyCnpj;
    }

    public String getKeyEmail() {
        return keyEmail;
    }

    public void setKeyEmail(String keyEmail) {
        this.keyEmail = keyEmail;
    }

    public boolean isTermsAndUse() {
        return termsAndUse;
    }

    public void setTermsAndUse(boolean termsAndUse) {
        this.termsAndUse = termsAndUse;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public boolean isChangePass() {
        return changePass;
    }

    public void setChangePass(boolean changePass) {
        this.changePass = changePass;
    }

    public Long getStorageUsed() {
        return storageUsed;
    }

    public void setStorageUsed(Long storageUsed) {
        this.storageUsed = storageUsed;
    }

    public Long getStorageFree() {
        return storageFree;
    }

    public void setStorageFree(Long storageFree) {
        this.storageFree = storageFree;
    }

    public Long getStorageTotal() {
        return storageTotal;
    }

    public void setStorageTotal(Long storageTotal) {
        this.storageTotal = storageTotal;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getDateCreateAccount() {
        return dateCreateAccount;
    }

    public void setDateCreateAccount(String dateCreateAccount) {
        this.dateCreateAccount = dateCreateAccount;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public List<Bpa> getBpas() {
        return bpas;
    }

    public void setBpas(List<Bpa> bpas) {
        this.bpas = bpas;
    }

    public TitleValidation getTitleValidation() {
        return titleValidation;
    }

    public void setTitleValidation(TitleValidation titleValidation) {
        this.titleValidation = titleValidation;
    }

    public BpacValidation getBpacValidation() {
        return bpacValidation;
    }

    public void setBpacValidation(BpacValidation bpacValidation) {
        this.bpacValidation = bpacValidation;
    }

    public BpaiValidation getBpaiValidation() {
        return bpaiValidation;
    }

    public void setBpaiValidation(BpaiValidation bpaiValidation) {
        this.bpaiValidation = bpaiValidation;
    }

    public TreatmentFile getTreatmentFile() {
        return treatmentFile;
    }

    public void setTreatmentFile(TreatmentFile treatmentFile) {
        this.treatmentFile = treatmentFile;
    }

    public AddressUser getAddressUser() {
        return addressUser;
    }

    public void setAddressUser(AddressUser addressUser) {
        this.addressUser = addressUser;
    }

    public DatesSigtap getDatesSigtap() {
        return datesSigtap;
    }

    public void setDatesSigtap(DatesSigtap datesSigtap) {
        this.datesSigtap = datesSigtap;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(Role.USER.getName()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.valid;
    }
}
