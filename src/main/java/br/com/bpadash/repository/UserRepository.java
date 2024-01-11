package br.com.bpadash.repository;

import br.com.bpadash.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByCnpj(String cpf);
}
