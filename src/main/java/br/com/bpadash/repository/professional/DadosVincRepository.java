package br.com.bpadash.repository.professional;

import br.com.bpadash.model.sigtap.DadosVinc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DadosVincRepository extends JpaRepository<DadosVinc, Long> {
}
