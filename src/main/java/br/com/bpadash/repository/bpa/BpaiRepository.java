package br.com.bpadash.repository.bpa;

import br.com.bpadash.model.Bpai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BpaiRepository extends JpaRepository<Bpai, Long> {
}
