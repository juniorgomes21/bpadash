package br.com.bpadash.model;

import br.com.bpadash.model.enumModel.Role;
import br.com.bpadash.model.enumModel.ZoneTime;
import br.com.bpadash.model.treatment.TreatmentFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Entity
@Table(name = "usuarios")
public class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cpf;
    private String email;
    private String password;
    private String cell;
    private String profile;
    private int profissionalNumberFree = 500;
    private int totalProfissional = 500;
    private Long storageUsed = 0L;
    private Long storageFree = 1073741824L;
    private Long storageTotal = 1073741824L; // 1GB
    private boolean valid = true;
    private LocalDateTime dateCreateAccount = LocalDateTime.now(ZoneId.of(ZoneTime.BR.getBr()));
    private LocalDateTime lastLogin = LocalDateTime.now(ZoneId.of(ZoneTime.BR.getBr()));
    @OneToMany
    private List<Bpa> bpas = new ArrayList<>();
    @OneToOne
    private BpacValidation bpacValidation;
    @OneToOne
    private BpaiValidation bpaiValidation;
    @OneToOne
    private TreatmentFile treatmentFile;

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public int getProfissionalNumberFree() {
        return profissionalNumberFree;
    }

    public void setProfissionalNumberFree(int profissionalNumberFree) {
        this.profissionalNumberFree = profissionalNumberFree;
    }

    public int getTotalProfissional() {
        return totalProfissional;
    }

    public void setTotalProfissional(int totalProfissional) {
        this.totalProfissional = totalProfissional;
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

    public LocalDateTime getDateCreateAccount() {
        return dateCreateAccount;
    }

    public void setDateCreateAccount(LocalDateTime dateCreateAccount) {
        this.dateCreateAccount = dateCreateAccount;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public List<Bpa> getBpas() {
        return bpas;
    }

    public void setBpas(List<Bpa> bpas) {
        this.bpas = bpas;
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
        return true;
    }
}
