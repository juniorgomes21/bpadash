package br.com.bpadash.repository.bpa;

import br.com.bpadash.model.Bpa;
import br.com.bpadash.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BpaRepository extends JpaRepository<Bpa, Long> {
    List<Bpa> findByUser(User user);

    Optional<Bpa> findByDateAndUser(LocalDate date, User user);

    Bpa findByIdentifierAndUser(String identifier , User user);
}
