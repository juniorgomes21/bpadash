package br.com.bpadash.repository.fpo;

import br.com.bpadash.model.Fpo;
import br.com.bpadash.model.LinkFpo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FpoRepository extends JpaRepository<Fpo, Long> {
    List<Fpo> findByLinkFpo(LinkFpo linkFpo);
}
