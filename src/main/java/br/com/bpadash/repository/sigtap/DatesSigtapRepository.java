package br.com.bpadash.repository.sigtap;

import br.com.bpadash.model.DatesSigtap;
import br.com.bpadash.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatesSigtapRepository extends JpaRepository<DatesSigtap, Long> {
    DatesSigtap findByUser(User user);

    DatesSigtap findByIdAndUser(Long id , User user);
}
