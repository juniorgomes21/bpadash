package br.com.bpadash.services.user;

import br.com.bpadash.dto.bpa.TimeLineUserDTO;
import br.com.bpadash.model.*;
import br.com.bpadash.model.enumModel.Role;
import br.com.bpadash.model.treatment.TreatmentFile;
import br.com.bpadash.params.ParamNewUser;
import br.com.bpadash.params.bpa.ParamValidationBpac;
import br.com.bpadash.params.bpa.ParamValidationBpai;
import br.com.bpadash.repository.UserRepository;
import br.com.bpadash.services.bpa.BpacService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.treatment.TreatmentFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BpacService bpacService;

    @Autowired
    private BpaiService bpaiService;

    public User logged(Authentication authentication) {
        User user = null;
        if (authentication.getPrincipal() instanceof User){
            user = (User) authentication.getPrincipal();
        }

        return user;
    }

    public User userInDb(Long id) {
        Optional<User> user = userRepository.findById(id);

        return user.orElse(null);

    }

    public User userInDb(String cpf) {
        Optional<User> player = userRepository.findByCpf(cpf);

        return player.orElse(null);

    }

    @Transactional
    public User createUser(ParamNewUser paramNewUser) {
        User user = new User();

        user.setName(paramNewUser.getName());
        user.setCpf(paramNewUser.getCpf());
        user.setEmail(paramNewUser.getEmail());
        user.setCell(paramNewUser.getCell());
        user.setProfile(Role.USER.name());
        user.setBpacValidation(new BpacValidation());
        user.setBpaiValidation(new BpaiValidation());
        user.setTreatmentFile(new TreatmentFile());
        user.setPassword(new BCryptPasswordEncoder().encode(paramNewUser.getPassword()));

        return this.save(user);
    }


    public void addBpa(User user, Bpa bpa, Long totalBytes) {
        user.getBpas().add(bpa);
        this.updateStorageAndSave(user, totalBytes, "sub");
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User saveAndFlush(User user) {
        return userRepository.saveAndFlush(user);
    }

    public List<User> save(List<User> users) {
        return userRepository.saveAll(users);
    }

    public void setValidationBpac(User user , ParamValidationBpac paramValidationBpac) {
        user.getBpacValidation().setIdent(paramValidationBpac.isIdent());
        user.getBpacValidation().setCnes(paramValidationBpac.isCnes());
        user.getBpacValidation().setCmp(paramValidationBpac.isCmp());
        user.getBpacValidation().setCbo(paramValidationBpac.isCbo());
        user.getBpacValidation().setFlh(paramValidationBpac.isFlh());
        user.getBpacValidation().setSeq(paramValidationBpac.isSeq());
        user.getBpacValidation().setPa(paramValidationBpac.isPa());
        user.getBpacValidation().setIdade(paramValidationBpac.isIdade());
        user.getBpacValidation().setQt(paramValidationBpac.isQt());
        user.getBpacValidation().setOrg(paramValidationBpac.isOrg());

        this.save(user);
    }

    public void setValidationBpai(User user , ParamValidationBpai paramValidationBpai) {
        user.getBpaiValidation().setIdent(paramValidationBpai.isIdent());
        user.getBpaiValidation().setCnes(paramValidationBpai.isCnes());
        user.getBpaiValidation().setCmp(paramValidationBpai.isCmp());
        user.getBpaiValidation().setCnsmed(paramValidationBpai.isCnsmed());
        user.getBpaiValidation().setCbo(paramValidationBpai.isCbo());
        user.getBpaiValidation().setDtaten(paramValidationBpai.isDtaten());
        user.getBpaiValidation().setFlh(paramValidationBpai.isFlh());
        user.getBpaiValidation().setSeq(paramValidationBpai.isSeq());
        user.getBpaiValidation().setPa(paramValidationBpai.isPa());
        user.getBpaiValidation().setCnspac(paramValidationBpai.isCnspac());
        user.getBpaiValidation().setSexo(paramValidationBpai.isSexo());
        user.getBpaiValidation().setIbge(paramValidationBpai.isIbge());
        user.getBpaiValidation().setCid(paramValidationBpai.isCid());
        user.getBpaiValidation().setIdade(paramValidationBpai.isIdade());
        user.getBpaiValidation().setQt(paramValidationBpai.isQt());
        user.getBpaiValidation().setCaten(paramValidationBpai.isCaten());
        user.getBpaiValidation().setNaut(paramValidationBpai.isNaut());
        user.getBpaiValidation().setOrg(paramValidationBpai.isOrg());
        user.getBpaiValidation().setNmpac(paramValidationBpai.isNmpac());
        user.getBpaiValidation().setDtnasc(paramValidationBpai.isDtnasc());
        user.getBpaiValidation().setRaca(paramValidationBpai.isRaca());
        user.getBpaiValidation().setEtnia(paramValidationBpai.isEtnia());
        user.getBpaiValidation().setNac(paramValidationBpai.isNac());
        user.getBpaiValidation().setSrv(paramValidationBpai.isSrv());
        user.getBpaiValidation().setClf(paramValidationBpai.isClf());
        user.getBpaiValidation().setEquipeSeq(paramValidationBpai.isEquipeSeq());
        user.getBpaiValidation().setEquipeArea(paramValidationBpai.isEquipeArea());
        user.getBpaiValidation().setCnpj(paramValidationBpai.isCnpj());
        user.getBpaiValidation().setCepPcnte(paramValidationBpai.isCepPcnte());
        user.getBpaiValidation().setLogradPcnte(paramValidationBpai.isLogradPcnte());
        user.getBpaiValidation().setEndPcnte(paramValidationBpai.isEndPcnte());
        user.getBpaiValidation().setComplPcnte(paramValidationBpai.isComplPcnte());
        user.getBpaiValidation().setNumPcnte(paramValidationBpai.isNumPcnte());
        user.getBpaiValidation().setBairroPcnte(paramValidationBpai.isBairroPcnte());
        user.getBpaiValidation().setDdtelPcnte(paramValidationBpai.isDdtelPcnte());
        user.getBpaiValidation().setEmailPcnte(paramValidationBpai.isEmailPcnte());
        user.getBpaiValidation().setIne(paramValidationBpai.isIne());

        this.save(user);
    }

    public List<TimeLineUserDTO> timeLine(User user) {
        List<Bpa> bpaList = user.getBpas();

        List<TimeLineUserDTO> timeLineUserDTOS = new ArrayList<>();
        bpaList.forEach( bpa -> {
            timeLineUserDTOS.add(new TimeLineUserDTO(bpa));
        });

        Comparator<TimeLineUserDTO> dateComparator = (dto1, dto2) -> dto2.getDate().compareTo(dto1.getDate());

        timeLineUserDTOS.sort(dateComparator);

        return timeLineUserDTOS;
    }


    public void updateStorageAndSave(User user, Long totalBytes, String action) {
        if(action.equals("sub")) {
            user.setStorageUsed(user.getStorageUsed() + totalBytes);
            user.setStorageFree(user.getStorageFree() - totalBytes);
        } else {
            user.setStorageUsed(user.getStorageUsed() - totalBytes);
            user.setStorageFree(user.getStorageFree() + totalBytes);
        }

        this.saveAndFlush(user);
    }
}
