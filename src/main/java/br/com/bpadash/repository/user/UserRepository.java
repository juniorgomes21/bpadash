package br.com.bpadash.repository.user;

import br.com.bpadash.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByCnpj(String cpf);
    Optional<User> findByKeyEmail(String keyEmail);
    Optional<User> findByKeyCnpj(String keyCnpj);
}
