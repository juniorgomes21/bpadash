package br.com.bpadash.repository.sigtap;

import br.com.bpadash.model.sigtap.LinkOccupation;
import br.com.bpadash.model.sigtap.Occupation;
import br.com.bpadash.projections.CodProcedureProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OccupationRepository extends JpaRepository<Occupation, Long> {
    List<CodProcedureProjection> findByLinkOccupation(LinkOccupation linkOccupation, Class<CodProcedureProjection> codProcedureProjectionClass);
}
