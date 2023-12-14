package br.com.bpadash.repository.treatment;

import br.com.bpadash.model.treatment.RuleTreatmentPaCbo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleTreatmentPaCboRepository extends JpaRepository<RuleTreatmentPaCbo, Long> {
}
