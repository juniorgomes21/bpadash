package br.com.bpadash.services.adm;

import br.com.bpadash.model.Administrator;
import br.com.bpadash.model.enumModel.Role;
import br.com.bpadash.model.user.AddressUser;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.adm.ParamNewAdm;
import br.com.bpadash.repository.AdministratorRepository;
import br.com.bpadash.repository.user.UserRepository;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdmServices {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdministratorRepository administratorRepository;


    public Administrator logged(Authentication authentication) {
        Administrator adm = null;
        if(authentication.getPrincipal() instanceof Administrator) {
            adm = (Administrator) authentication.getPrincipal();
        }

        assert adm != null;
        adm = administratorRepository.findByCpf(adm.getCpf()).get();

        return adm;
    }

    public boolean testPassword(Administrator administrator, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.matches(password, administrator.getPassword());
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

    public List<User> getUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.DESC, "dateCreateAccount"));
    }

    public void changeActiveUser(User user) {

        user.setValid(!user.isValid());

        userRepository.save(user);
    }
}
