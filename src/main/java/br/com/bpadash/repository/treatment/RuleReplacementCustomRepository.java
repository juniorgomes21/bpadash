package br.com.bpadash.repository.treatment;

import br.com.bpadash.model.treatment.RuleReplacementCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleReplacementCustomRepository extends JpaRepository<RuleReplacementCustom, Long> {
}
