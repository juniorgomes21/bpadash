package br.com.bpadash.repository.bpa;

import br.com.bpadash.model.Bpac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BpacRepository extends JpaRepository<Bpac, Long> {
}
