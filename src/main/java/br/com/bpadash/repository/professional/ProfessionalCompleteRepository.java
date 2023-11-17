package br.com.bpadash.repository.professional;

import br.com.bpadash.model.ProfessionalComplete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalCompleteRepository extends JpaRepository<ProfessionalComplete, Long> {
}
