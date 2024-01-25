package br.com.bpadash.services.adm;

import br.com.bpadash.model.bpa.Address;
import br.com.bpadash.model.Administrator;
import br.com.bpadash.model.enumModel.Role;
import br.com.bpadash.model.user.AddressUser;
import br.com.bpadash.params.adm.ParamNewAdm;
import br.com.bpadash.repository.AdministratorRepository;
import br.com.bpadash.services.EncryptionService;
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
        administrator.setCpf(EncryptionService.encrypt(paramNewAdm.getCpf()));
        administrator.setName(EncryptionService.encrypt(paramNewAdm.getName()));
        administrator.setCell(EncryptionService.encrypt(paramNewAdm.getCell()));
        administrator.setEmail(EncryptionService.encrypt(paramNewAdm.getEmail()));
        administrator.setKeyCpf(EncryptionService.hashString(paramNewAdm.getCpf()));
        administrator.setKeyEmail(EncryptionService.hashString(paramNewAdm.getEmail()));
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
