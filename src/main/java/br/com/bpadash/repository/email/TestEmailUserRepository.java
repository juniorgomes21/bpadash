package br.com.bpadash.repository.email;

import br.com.bpadash.model.email.TestEmailUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestEmailUserRepository extends JpaRepository<TestEmailUser, Long> {
}
