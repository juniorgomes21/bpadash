package br.com.bpadash.repository.fpo;

import br.com.bpadash.model.sigtap.Fpo;
import br.com.bpadash.model.sigtap.LinkFpo;
import br.com.bpadash.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FpoRepository extends JpaRepository<Fpo, Long> {
    List<Fpo> findByLinkFpo(LinkFpo linkFpo);
    Page<Fpo> findByLinkFpo(LinkFpo linkFpo, Pageable pageable);
    Page<Fpo> findByLinkFpoAndPaStartingWith(LinkFpo linkFpo, String pa, Pageable pageable);

    Optional<Fpo> findByLinkFpoAndPa(LinkFpo linkFpo , String pa);
    void deleteByLinkFpo(LinkFpo link);
    List<Fpo> findByLinkFpoIn(List<LinkFpo> linkFpoList);
}
