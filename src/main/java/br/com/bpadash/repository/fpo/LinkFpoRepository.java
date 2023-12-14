package br.com.bpadash.repository.fpo;

import br.com.bpadash.model.LinkFpo;
import br.com.bpadash.model.User;
import br.com.bpadash.projections.DateProjection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LinkFpoRepository extends JpaRepository<LinkFpo, Long> {
    Optional<LinkFpo> findByUserAndDate(User user , LocalDate date);

    List<DateProjection> findAllByUser(User user, Class<DateProjection> fpoDateProjectionClass, Sort sort);

    boolean existsByDateAndUser(LocalDate date , User user);

    Optional<LinkFpo> findFirstByUser(User user , Sort sort);
}
