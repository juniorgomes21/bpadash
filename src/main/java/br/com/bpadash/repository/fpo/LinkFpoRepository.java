package br.com.bpadash.repository.fpo;

import br.com.bpadash.model.user.User;
import br.com.bpadash.model.sigtap.LinkFpo;
import br.com.bpadash.projections.DateProjection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LinkFpoRepository extends JpaRepository<LinkFpo, Long> {

    boolean existsByDate(LocalDate date);

    Optional<LinkFpo> findByDate(LocalDate date);

    Optional<LinkFpo> findFirstByOrderByDateDesc();

    List<DateProjection> findAllByUser(User user , Class<DateProjection> dateProjectionClass , Sort sort);

    Optional<LinkFpo> findByUserAndDate(User user , LocalDate date);

    Optional<LinkFpo> findFirstByUser(User user);
    Optional<LinkFpo> findFirstByUser(User user , Sort sort);

    boolean existsByDateAndUser(LocalDate date , User user);

    List<LinkFpo> findByUser(User user);

    boolean existsByUser(User user);

    List<LinkFpo> findByUserAndDateBetween(User user, LocalDate start , LocalDate end);
}
