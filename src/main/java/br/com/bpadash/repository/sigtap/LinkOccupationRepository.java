package br.com.bpadash.repository.sigtap;

import br.com.bpadash.model.LinkOccupation;
import br.com.bpadash.model.User;
import br.com.bpadash.projections.DateProjection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LinkOccupationRepository extends JpaRepository<LinkOccupation, Long> {

    Optional<LinkOccupation> findByDate(LocalDate date);

    Optional<LinkOccupation> findFirstByOrderByDateDesc();
}
