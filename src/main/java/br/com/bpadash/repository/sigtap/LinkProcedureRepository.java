package br.com.bpadash.repository.sigtap;

import br.com.bpadash.model.LinkProcedure;
import br.com.bpadash.model.User;
import br.com.bpadash.projections.DateProjection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LinkProcedureRepository extends JpaRepository<LinkProcedure, Long> {
    Optional<LinkProcedure> findByDateAndUser(LocalDate date , User user);

    boolean existsByDateAndUser(LocalDate date , User user);

    List<DateProjection> findAllByUser(User user , Class<DateProjection> dateProjectionClass , Sort sort);

    Optional<LinkProcedure> findFirstByUser(User user, Sort sort);
}
