package br.com.bpadash.repository.bpa;

import br.com.bpadash.model.Bpa;
import br.com.bpadash.model.Bpac;
import br.com.bpadash.model.Bpai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BpacRepository extends JpaRepository<Bpac, Long> {
    List<Bpac> findByBpa(Bpa bpa);

    Page<Bpac> findByBpa(Bpa bpa, Pageable pageable);

    @Query(value = "SELECT SUM(" +
            "LENGTH(b.ident) + LENGTH(b.cnes) + LENGTH(b.cmp) + LENGTH(b.org) + " +
            "LENGTH(b.flh) + LENGTH(b.seq) + LENGTH(b.pa) + LENGTH(b.idade) + " +
            "LENGTH(b.qt) + LENGTH(b.org) + LENGTH(b.fim)) FROM Bpac b WHERE b.id IN :idList")
    Long calculateSizeById(@Param("idList") List<Long> idList);

    void deleteByBpa(Bpa bpa);

    List<Bpac> findByPaAndBpa(String oldPa , Bpa bpa);

    List<Bpac> findByCboAndBpa(String cbo, Bpa bpa);

    Bpac findTopByBpaOrderByFlhDescSeqDesc(Bpa bpa);
}
