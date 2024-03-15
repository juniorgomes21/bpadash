package br.com.bpadash.services.user;

import br.com.bpadash.model.enumModel.ZoneTime;
import br.com.bpadash.model.user.*;
import br.com.bpadash.repository.user.SessionUserRepository;
import br.com.bpadash.services.adm.PackageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SessionUserService {

    @Autowired
    private SessionUserRepository sessionUserRepository;
    @Autowired
    private PackageUserService packageUserService;
    @Autowired
    private AccountLoggedService accountLoggedService;

    public Optional<SessionUser> get(User user) {

        return sessionUserRepository.findByUser(user);
    }

    /**
     * Obtem uma sessão do usuário e verifica se o funcionário está cadastrado, se sim adiciona o usuário na lista de
     * usuários logados.
     * @param user
     * @param employee
     * @return
     */
    public String verify(User user, Employee employee, String key) {
        Long idUser = user.getId();

        Optional<SessionUser> sessionUserOptional = this.get(user);

        String response;
        if(sessionUserOptional.isPresent()) {
            SessionUser sessionUser = sessionUserOptional.get();

            this.updateSession(sessionUser);

            return this.addLogged(sessionUser, employee, idUser, key);
        } else {
            response = "NOT FOUND SESSION";
        }

        return response;
    }


    /**
     * Cria uma sessõa de login
     * @param user
     * @param packageUser
     */
    public SessionUser create(User user, PackageUser packageUser) {
        return new SessionUser(user, packageUser.getMaxSession());
    }


    /**
     * Adiciona um funcionário na lista de funcionário logados caso ele não esteja.
     * @param sessionUser
     * @param employee
     * @param idUser
     * @return
     */
    public String addLogged(SessionUser sessionUser, Employee employee, Long idUser, String key) {

        if(isAddSession(sessionUser)) {

            if(sessionUser.getAccountLoggeds().stream().anyMatch(account -> account.getEmployee().equals(employee))) {
                return "USER IS LOGGED";
            }

            LocalDateTime date = LocalDateTime.now(ZoneId.of(ZoneTime.BR.getBr()));

            employee.setLastLogin(date);

            sessionUser.getAccountLoggeds().add(new AccountLogged(employee, idUser, date, key));

            this.save(sessionUser);

            return "OK";
        }

        return "MAX SESSION";
    }


    /**
     * Atualiza a sessão removendo os funcionários que expirou o tempo de login.
     * @param sessionUser
     */
    public void updateSession(SessionUser sessionUser) {

        if(sessionUser.getAccountLoggeds().size() == 0) {
            return;
        }

        LocalDateTime time = LocalDateTime.now(ZoneId.of(ZoneTime.BR.getBr())).minusHours(2L);

        List<AccountLogged> accountLoggedListForRemove = new ArrayList<>();

        sessionUser.getAccountLoggeds().forEach( accountLogged -> {
            if (accountLogged.getDate().isBefore(time)) {
                accountLoggedListForRemove.add(accountLogged);
            }
        });

        if(!accountLoggedListForRemove.isEmpty()) {
            sessionUser.getAccountLoggeds().removeAll(accountLoggedListForRemove);
        }
    }


    /**
     * Faz o logout do funcionário.
     * @param user
     * @param employee
     * @return
     */
    public String logoutEmployee(User user, Employee employee) {
        Optional<SessionUser> sessionUserOptional = sessionUserRepository.findByUser(user);

        if(sessionUserOptional.isPresent()) {
            SessionUser sessionUser = sessionUserOptional.get();

            Optional<AccountLogged> accountLoggedOptional = sessionUser.getAccountLoggeds()
                    .stream()
                    .filter(account -> account.getEmployee().equals(employee))
                    .findFirst();

            if(accountLoggedOptional.isPresent()) {
                sessionUser.getAccountLoggeds().remove(accountLoggedOptional.get());

                this.save(sessionUser);

                return "OK";
            } else {
                return "NOT FOUND EMPLOYEE";
            }
        }

        return "NOT FOUND SESSION";
    }

    public SessionUser save(SessionUser sessionUser) {

        return sessionUserRepository.save(sessionUser);
    }


    /**
     * Verifica se o número máximo de Funcionários já está logado.
     * @param sessionUser
     * @return
     */
    private boolean isAddSession(SessionUser sessionUser) {

        int countSession = sessionUser.getAccountLoggeds().size() + 1;
        int maxSession = sessionUser.getCountMaxLogged();

        return countSession <= maxSession;
    }


}
