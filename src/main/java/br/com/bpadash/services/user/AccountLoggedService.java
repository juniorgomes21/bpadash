package br.com.bpadash.services.user;

import br.com.bpadash.model.user.AccountLogged;
import br.com.bpadash.repository.user.AccountLoggedRepositiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountLoggedService {

    @Autowired
    private AccountLoggedRepositiry accountLoggedRepositiry;

    public AccountLogged save(AccountLogged accountLogged) {
        return accountLoggedRepositiry.save(accountLogged);
    }
}
