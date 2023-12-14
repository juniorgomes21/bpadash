package br.com.bpadash.repository;

import br.com.bpadash.model.ExceptionErroLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceptionErroLogRepository extends JpaRepository<ExceptionErroLog, Long> {
}
