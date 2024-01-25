package br.com.bpadash.repository.fpo;

import br.com.bpadash.model.sigtap.Fpo;
import br.com.bpadash.model.sigtap.LinkFpo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FpoRepository extends JpaRepository<Fpo, Long> {
    List<Fpo> findByLinkFpo(LinkFpo linkFpo);

    Optional<Fpo> findByLinkFpoAndPa(LinkFpo linkFpo , String pa);
    void deleteByLinkFpo(LinkFpo link);
}
