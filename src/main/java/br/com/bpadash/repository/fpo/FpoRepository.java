package br.com.bpadash.repository.fpo;

import br.com.bpadash.model.Fpo;
import br.com.bpadash.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FpoRepository extends JpaRepository<Fpo, Long> {
}
