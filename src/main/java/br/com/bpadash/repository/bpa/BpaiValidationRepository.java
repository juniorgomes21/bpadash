package br.com.bpadash.repository.bpa;

import br.com.bpadash.model.bpa.BpaiValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BpaiValidationRepository extends JpaRepository<BpaiValidation, Long> {
}
