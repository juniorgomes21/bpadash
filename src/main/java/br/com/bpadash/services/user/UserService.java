package br.com.bpadash.services.user;

import br.com.bpadash.dto.UserDTO;
import br.com.bpadash.dto.bpa.TimeLineDTO;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.sigtap.LinkFpo;
import br.com.bpadash.model.sigtap.LinkProfessionals;
import br.com.bpadash.model.treatment.TreatmentFile;
import br.com.bpadash.model.user.AddressUser;
import br.com.bpadash.model.user.PackageUser;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.bpa.ParamValidationTitle;
import br.com.bpadash.params.user.ParamNewUser;
import br.com.bpadash.params.bpa.ParamValidationBpac;
import br.com.bpadash.params.bpa.ParamValidationBpai;
import br.com.bpadash.repository.UserRepository;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.adm.PackageUserService;
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
    private PackageUserService packageUserService;

    public User get(Authentication authentication) {
        User user;
        if(authentication.getPrincipal() instanceof User) {
            user = (User) authentication.getPrincipal();
            Optional<User> userDb = userRepository.findById(user.getId());

            return userDb.orElse(null);
        }

        return null;
    }

    public User userLogged(Authentication authentication) {
        User user = null;
        if(authentication.getPrincipal() instanceof User) {
            user = (User) authentication.getPrincipal();
        }

        return user;
    }

    @Transactional
    public User createUser(ParamNewUser paramNewUser, AddressUser address) {
        User user = new User();

        PackageUser packageUser = packageUserService.get(paramNewUser.getPackageUser());

        user.setAddressUser(address);
        user.setName(EncryptionService.encrypt(paramNewUser.getName()));
        user.setCnpj(EncryptionService.encrypt(paramNewUser.getCnpj()));
        user.setKeyCnpj(EncryptionService.hashString(paramNewUser.getCnpj()));
        user.setCell(EncryptionService.encrypt(paramNewUser.getCell()));
        user.setEmail(EncryptionService.encrypt(paramNewUser.getEmail()));
        user.setKeyEmail(EncryptionService.hashString(paramNewUser.getEmail()));
        user.setPackageUser(EncryptionService.encrypt(packageUser.getPackageName()));
        user.setStorageFree(packageUser.getSizeStorage());
        user.setStorageTotal(packageUser.getSizeStorage());
        user.setTreatmentFile(new TreatmentFile(packageUser.getNumberRules()));
        user.setPassword(new BCryptPasswordEncoder().encode(paramNewUser.getPassword()));

        return user;
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

    public int countRules(User user) {
        int a = user.getTreatmentFile().getRuleTreatmentPaList().size();
        int b = user.getTreatmentFile().getRuleTreatmentPaCboList().size();
        int c = user.getTreatmentFile().getRuleTreatmentPaDeleteList().size();

        return a + b + c;
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

    public void setValidationTitle(User user , ParamValidationTitle paramValidationTitle) {
        user.getTitleValidation().setLin(paramValidationTitle.isLin());
        user.getTitleValidation().setFlh(paramValidationTitle.isFlh());
        user.getTitleValidation().setSmtVrf(paramValidationTitle.isSmtVrf());
        user.getTitleValidation().setCgccpf(paramValidationTitle.isCgccpf());

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

    public List<TimeLineDTO> timeLine(User user) {
        List<Bpa> bpaList = user.getBpas();

        List<TimeLineDTO> timeLineDTOS = new ArrayList<>();

        bpaList.forEach( bpa -> {
            timeLineDTOS.add(new TimeLineDTO(bpa));
        });

        Comparator<TimeLineDTO> dateComparator = (dto1, dto2) -> dto2.getDate().compareTo(dto1.getDate());

        timeLineDTOS.sort(dateComparator);

        return timeLineDTOS;
    }

    public List<TimeLineDTO> timeLineProfessionals(List<LinkProfessionals> linkProfessionalsList) {

        List<TimeLineDTO> timeLineDTOS = new ArrayList<>();

        linkProfessionalsList.forEach( link -> {
            timeLineDTOS.add(new TimeLineDTO(link));
        });

        Comparator<TimeLineDTO> dateComparator = (dto1, dto2) -> dto2.getDate().compareTo(dto1.getDate());

        timeLineDTOS.sort(dateComparator);

        return timeLineDTOS;
    }

    public List<TimeLineDTO> timeLineFpo(List<LinkFpo> linkFpos) {

        List<TimeLineDTO> timeLineDTOS = new ArrayList<>();

        linkFpos.forEach( link -> {
            timeLineDTOS.add(new TimeLineDTO(link));
        });

        Comparator<TimeLineDTO> dateComparator = (dto1, dto2) -> dto2.getDate().compareTo(dto1.getDate());

        timeLineDTOS.sort(dateComparator);

        return timeLineDTOS;
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

    public void updateStorageBpaiAndSave(User user, Long bytesOld, Long bytesNew) {

        //ADD
        user.setStorageUsed(user.getStorageUsed() - bytesOld);
        user.setStorageFree(user.getStorageFree() + bytesOld);

        //SUBTRACT
        user.setStorageUsed(user.getStorageUsed() + bytesNew);
        user.setStorageFree(user.getStorageFree() - bytesNew);

        this.save(user);
    }

    public boolean testPassword(User user, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.matches(password, user.getPassword());
    }

    public UserDTO updatePassword(User user, String currentPassword) {
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String newPassword = bc.encode(currentPassword);

        user.setPassword(newPassword);
        user.setChangePass(true);

        return new UserDTO(userRepository.save(user));
    }


    public boolean existe(User user) {
        Optional<User> userOptinal = userRepository.findByKeyCnpj(user.getKeyCnpj());

        return userOptinal.isPresent();
    }
}
