package br.com.bpadash.services.user;

import br.com.bpadash.model.Bpa;
import br.com.bpadash.model.User;
import br.com.bpadash.model.enumModel.Role;
import br.com.bpadash.params.ParamNewUser;
import br.com.bpadash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User logged(Authentication authentication) {
        User user = null;
        if (authentication.getPrincipal() instanceof User){
            user = (User) authentication.getPrincipal();
        }

        return user;
    }

    public User userInDb(Long id) {
        Optional<User> user = userRepository.findById(id);

        return user.orElse(null);

    }

    public User userInDb(String cpf) {
        Optional<User> player = userRepository.findByCpf(cpf);

        return player.orElse(null);

    }

    public void createUser(ParamNewUser paramNewUser) {
        User user = new User();

        user.setName(paramNewUser.getName());
        user.setCpf(paramNewUser.getCpf());
        user.setEmail(paramNewUser.getEmail());
        user.setCell(paramNewUser.getCell());
        user.setProfile(Role.USER.name());
        user.setPassword(new BCryptPasswordEncoder().encode(paramNewUser.getPassword()));

        this.save(user);
    }

    public void addBpa(User user, Bpa bpa) {
        user.getBpas().add(bpa);

        this.save(user);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> save(List<User> users) {
        return userRepository.saveAll(users);
    }
}
