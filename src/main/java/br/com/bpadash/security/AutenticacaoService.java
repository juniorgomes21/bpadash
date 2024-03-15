package br.com.bpadash.security;

import br.com.bpadash.model.Administrator;
import br.com.bpadash.model.user.User;
import br.com.bpadash.repository.AdministratorRepository;
import br.com.bpadash.repository.user.UserRepository;
import br.com.bpadash.services.user.SessionUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service("userDetailsService")
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private AdministratorRepository admRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String credencials) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByKeyEmail(credencials);

        if (user.isPresent()) {
            //TODO fazer verificação de validade do usuário
            User userLogged = user.get();

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userLogged.getProfile());
            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(authority);

            return userLogged;
        }

        Optional<Administrator> adm = admRepository.findByKeyEmail(credencials);

        if (adm.isPresent()) {
            Administrator administrator = adm.get();
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(administrator.getProfile());
            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(authority);

            return administrator;
        } else {
            throw new UsernameNotFoundException("Dados inválidos!");
        }
    }
}
