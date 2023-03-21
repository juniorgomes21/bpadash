package br.com.bpadash.services.user;

import br.com.bpadash.model.User;
import br.com.bpadash.model.enumModel.Role;
import br.com.bpadash.params.ParamNewUser;
import br.com.bpadash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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

        return this.playerInDb(user.getId());
    }

    public User playerInDb(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return user.get();
        }

        return null;
    }

    public User playerInDb(String cpf) {
        Optional<User> player = userRepository.findByCpf(cpf);

        if (player.isPresent()) {
            return player.get();
        }

        return null;
    }

    public void createUser(ParamNewUser paramNewUser) {
        User newUser = new User();

        newUser.setName(paramNewUser.getName());
        newUser.setCpf(paramNewUser.getCpf());
        newUser.setEmail(paramNewUser.getEmail());
        newUser.setCell(paramNewUser.getCell());
        newUser.setProfile(Role.USER.name());
        newUser.setPassword(new BCryptPasswordEncoder().encode(paramNewUser.getPassword()));

        userRepository.save(newUser);
    }
}
