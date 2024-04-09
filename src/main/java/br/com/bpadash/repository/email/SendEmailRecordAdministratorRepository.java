package br.com.bpadash.repository.email;



import br.com.bpadash.model.email.SendEmailRecord;
import br.com.bpadash.model.email.SendEmailRecordAdministrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendEmailRecordAdministratorRepository extends JpaRepository<SendEmailRecordAdministrator, Long> {
}
