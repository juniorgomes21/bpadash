package br.com.bpadash.repository.sigtap;

import br.com.bpadash.model.sigtap.LinkOccupation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface LinkOccupationRepository extends JpaRepository<LinkOccupation, Long> {

    Optional<LinkOccupation> findByDate(LocalDate date);

    Optional<LinkOccupation> findFirstByOrderByDateDesc();
}
