package br.com.bpadash.services.adm;

import br.com.bpadash.model.Address;
import br.com.bpadash.model.Administrator;
import br.com.bpadash.model.enumModel.Role;
import br.com.bpadash.params.adm.ParamNewAdm;
import br.com.bpadash.repository.AdministratorRepository;
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

    public void createAdministrador(ParamNewAdm paramNewAdm, Address address) {
        Administrator administrator = new Administrator();
        administrator.setName(paramNewAdm.getName());
        administrator.setCpf(paramNewAdm.getCpf());
        administrator.setAddress(address);
        administrator.setEmail(paramNewAdm.getEmail());
        administrator.setCell(paramNewAdm.getCell());
        administrator.setProfile(Role.ADMINISTRATOR.name());
        administrator.setPassword(new BCryptPasswordEncoder().encode(paramNewAdm.getPassword()));

        administratorRepository.save(administrator);
    }
}
