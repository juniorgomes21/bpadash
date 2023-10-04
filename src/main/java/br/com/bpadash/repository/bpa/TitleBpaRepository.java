package br.com.bpadash.repository.bpa;

import br.com.bpadash.model.Bpa;
import br.com.bpadash.model.TitleBpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TitleBpaRepository extends JpaRepository<TitleBpa, Long> {
    TitleBpa findByBpa(Bpa bpa);

    @Query(value = "SELECT SUM(" +
            "LENGTH(b.iden) + LENGTH(b.hdr) + LENGTH(b.mvm) + LENGTH(b.lin) + " +
            "LENGTH(b.flh) + LENGTH(b.smtVrf) + LENGTH(b.rsp) + LENGTH(b.sgl) + " +
            "LENGTH(b.cgccpf) + LENGTH(b.dst) + LENGTH(b.dstIn) + " +
            "LENGTH(b.versao) + LENGTH(b.fim)) FROM TitleBpa b WHERE b.id IN :id")
    int calculateSizeById(@Param("id") Long id);
}
