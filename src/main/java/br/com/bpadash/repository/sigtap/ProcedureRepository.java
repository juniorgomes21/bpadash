package br.com.bpadash.repository.sigtap;

import br.com.bpadash.model.sigtap.LinkProcedure;
import br.com.bpadash.model.sigtap.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Long> {

    List<Procedure> findByLinkProcedure(LinkProcedure linkProcedure);
}
