package br.com.bpadash.repository.sigtap;

import br.com.bpadash.model.sigtap.LinkCep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface LinkCepRepository extends JpaRepository<LinkCep, Long> {

    boolean existsByDate(LocalDate date);

    Optional<LinkCep> findByDate(LocalDate date);

    Optional<LinkCep> findFirstByOrderByDateDesc();
}
