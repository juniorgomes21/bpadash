package br.com.bpadash.repository.sigtap;

import br.com.bpadash.model.sigtap.Cep;
import br.com.bpadash.model.sigtap.LinkCep;
import br.com.bpadash.projections.CepProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CepRepository extends JpaRepository<Cep, Long> {
    boolean existsByCepAndLinkCep(String cep , LinkCep linkCep);

    List<CepProjection> findByLinkCep(LinkCep linkCep);

}
