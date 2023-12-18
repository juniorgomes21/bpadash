package br.com.bpadash.repository.treatment;

import br.com.bpadash.model.treatment.RuleTreatmentPaDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleTreatmentPaDeleteRepository extends JpaRepository<RuleTreatmentPaDelete, Long> {
}
