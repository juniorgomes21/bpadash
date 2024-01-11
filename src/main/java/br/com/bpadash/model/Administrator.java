package br.com.bpadash.model;

import br.com.bpadash.model.enumModel.Role;
import br.com.bpadash.model.enumModel.ZoneTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Entity
public class Administrator implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String cpf;
    @Column(unique = true)
    private String email;
    private String password;
    private String cell;
    private String profile;
    private LocalDateTime dateCreateAccount = LocalDateTime.now(ZoneId.of(ZoneTime.BR.getBr()));
    private LocalDateTime lastLogin = LocalDateTime.now(ZoneId.of(ZoneTime.BR.getBr()));
    @OneToOne
    private Address address;

    public Administrator() {
    }


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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(Role.ADMINISTRATOR.getName()));

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
