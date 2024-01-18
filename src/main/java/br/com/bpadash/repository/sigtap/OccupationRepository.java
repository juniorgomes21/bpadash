package br.com.bpadash.repository.sigtap;

import br.com.bpadash.model.LinkOccupation;
import br.com.bpadash.model.Occupation;
import br.com.bpadash.model.User;
import br.com.bpadash.projections.CodProcedureProjection;
import br.com.bpadash.projections.DateProjection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OccupationRepository extends JpaRepository<Occupation, Long> {
    List<CodProcedureProjection> findByLinkOccupation(LinkOccupation linkOccupation, Class<CodProcedureProjection> codProcedureProjectionClass);
}
