package br.com.bpadash.repository.user;

import br.com.bpadash.model.user.SessionUser;
import br.com.bpadash.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionUserRepository extends JpaRepository<SessionUser,Long> {
    Optional<SessionUser> findByUser(User user);

}
