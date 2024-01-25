package br.com.bpadash.repository;


import br.com.bpadash.model.user.CodLograd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodLogradRepository extends JpaRepository<CodLograd, Long> {
    Optional<CodLograd> findFirstByKeyCodContaining(String lougrad);
}
