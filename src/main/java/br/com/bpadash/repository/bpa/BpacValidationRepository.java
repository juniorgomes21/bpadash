package br.com.bpadash.repository.bpa;

import br.com.bpadash.model.bpa.BpacValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BpacValidationRepository extends JpaRepository<BpacValidation, Long> {
}
