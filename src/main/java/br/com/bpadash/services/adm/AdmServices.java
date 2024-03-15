package br.com.bpadash.services.adm;

import br.com.bpadash.model.bpa.Address;
import br.com.bpadash.model.Administrator;
import br.com.bpadash.model.enumModel.Role;
import br.com.bpadash.model.user.AddressUser;
import br.com.bpadash.params.adm.ParamNewAdm;
import br.com.bpadash.repository.AdministratorRepository;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdmServices {

    @Autowired
    private AdministratorRepository administratorRepository;

    public Administrator admLogado(Authentication authentication) {
        Administrator adm = null;
        if(authentication.getPrincipal() instanceof Administrator) {
            adm = (Administrator) authentication.getPrincipal();
        }

        assert adm != null;
        adm = administratorRepository.findByCpf(adm.getCpf()).get();

        return adm;
    }

    public boolean testPassword(String password, Authentication authentication) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Administrator adm = this.admLogado(authentication);

        return passwordEncoder.matches(password, adm.getPassword());
    }

    public Administrator createAdministrador(ParamNewAdm paramNewAdm, AddressUser address) {

        Administrator administrator = new Administrator();

        administrator.setAddressUser(address);
        administrator.setProfile(Role.ADMINISTRATOR.name());
        administrator.setCpf(EnCryptionAESService.encrypt(paramNewAdm.getCpf()));
        administrator.setName(EnCryptionAESService.encrypt(paramNewAdm.getName()));
        administrator.setCell(EnCryptionAESService.encrypt(paramNewAdm.getCell()));
        administrator.setEmail(EnCryptionAESService.encrypt(paramNewAdm.getEmail()));
        administrator.setKeyCpf(EnCryptionAESService.hashString(paramNewAdm.getCpf()));
        administrator.setKeyEmail(EnCryptionAESService.hashString(paramNewAdm.getEmail()));
        administrator.setPassword(new BCryptPasswordEncoder().encode(paramNewAdm.getPassword()));

        return administrator;
    }


    public Administrator save(Administrator administrator) {
        return administratorRepository.save(administrator);
    }

    public boolean existe(Administrator adm) {
        return administratorRepository.findByKeyEmail(adm.getKeyEmail()).isPresent();
    }
}
