package br.com.bpadash.repository.bpa;

import br.com.bpadash.model.BpacValidation;
import br.com.bpadash.model.TitleValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleValidationRepository extends JpaRepository<TitleValidation, Long> {
}
