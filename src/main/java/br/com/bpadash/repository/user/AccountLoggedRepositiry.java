package br.com.bpadash.repository.user;

import br.com.bpadash.model.user.AccountLogged;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountLoggedRepositiry extends JpaRepository<AccountLogged, Long> {
}
