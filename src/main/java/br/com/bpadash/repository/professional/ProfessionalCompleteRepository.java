package br.com.bpadash.repository.professional;

import br.com.bpadash.model.LinkProfessionals;
import br.com.bpadash.model.ProfessionalComplete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessionalCompleteRepository extends JpaRepository<ProfessionalComplete, Long> {
    Optional<ProfessionalComplete> findByKeyProfIdAndLinkProfessionals(String paramProfessional , LinkProfessionals linkProfessionals);

    Optional<ProfessionalComplete> findByNameAndLinkProfessionals(String paramProfessional , LinkProfessionals linkProfessionals);
}
