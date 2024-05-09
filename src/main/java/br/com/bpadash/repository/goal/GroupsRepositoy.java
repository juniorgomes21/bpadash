package br.com.bpadash.repository.goal;

import br.com.bpadash.model.GroupsPa.GroupsPa;
import br.com.bpadash.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupsRepositoy extends JpaRepository<GroupsPa, Long> {
    Optional<GroupsPa> getByUser(User user);
}
