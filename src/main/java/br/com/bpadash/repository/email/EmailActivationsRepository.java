package br.com.bpadash.repository.email;


import br.com.bpadash.model.adm.EmailCodeAdministrator;
import br.com.bpadash.model.email.EmailCodeConfirm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailActivationsRepository extends JpaRepository<EmailCodeAdministrator, Long> {
    Optional<EmailCodeAdministrator> findByCode(String token);
}
