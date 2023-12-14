package br.com.bpadash.repository.treatment;

import br.com.bpadash.model.treatment.RuleTreatmentPa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleTreatmentPaRepository extends JpaRepository<RuleTreatmentPa, Long> {
}
