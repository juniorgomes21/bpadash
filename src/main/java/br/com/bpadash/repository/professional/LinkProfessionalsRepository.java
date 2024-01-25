package br.com.bpadash.repository.professional;

import br.com.bpadash.model.user.User;
import br.com.bpadash.model.sigtap.LinkProfessionals;
import br.com.bpadash.projections.DateProjection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LinkProfessionalsRepository extends JpaRepository<LinkProfessionals, Long> {
    Optional<LinkProfessionals> findByDate(LocalDate date);

    Optional<LinkProfessionals> findFirstByOrderByDateDesc();
    List<LinkProfessionals> findByUser(User user);

    List<DateProjection> findAllByUser(User user , Class<DateProjection> dateProjectionClass , Sort sort);

    Optional<LinkProfessionals> findByDateAndUser(LocalDate date , User user);

    Optional<LinkProfessionals> findFirstByUser(User user);
    Optional<LinkProfessionals> findFirstByUser(User user, Sort sort);
}
