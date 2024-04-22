package br.com.bpadash.services.user;

import br.com.bpadash.dto.bpa.BpaDTO;
import br.com.bpadash.dto.bpa.TimeLineDTO;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.sigtap.DatesSigtap;
import br.com.bpadash.model.sigtap.LinkFpo;
import br.com.bpadash.model.sigtap.LinkProfessionals;
import br.com.bpadash.model.treatment.TreatmentFile;
import br.com.bpadash.model.user.AddressUser;
import br.com.bpadash.model.user.Employee;
import br.com.bpadash.model.user.PackageUser;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.user.ParamNewUser;
import br.com.bpadash.repository.user.UserRepository;
import br.com.bpadash.services.adm.PackageUserService;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
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
    @Autowired
    private SessionUserService sessionUserService;


    /**
     * Busca um usuário no banco de dados.
     * @param authentication
     * @return
     */
    public User get(Authentication authentication) {
        if(authentication.getPrincipal() instanceof User user) {
            Optional<User> userDbOptional = userRepository.findById(user.getId());

            return userDbOptional.orElse(null);
        }

        return null;
    }


    /**
     * Busca um usuário no banco de dados.
     * @param id
     * @return
     */
    public User get(Long id) {
        return userRepository.getById(id);
    }


    /**
     * Busca o usuário autenticado.
     * @param authentication
     * @return
     */
    public User userLogged(Authentication authentication) {
        User user = null;
        if(authentication.getPrincipal() instanceof User) {
            user = (User) authentication.getPrincipal();
        }

        return user;
    }

    @Transactional
    public User createUser(ParamNewUser paramNewUser, AddressUser address, PackageUser packageUser) {
        User user = new User();

        String email = EnCryptionAESService.encrypt(paramNewUser.getEmail());

        user.setAddressUser(address);
        user.setSessionUser(sessionUserService.create(user, packageUser));
        user.getEmployeeRegistered().add(new Employee(paramNewUser.getNameRoot(), email, paramNewUser.getPassword(), true, user));
        user.setCountMaxEmployee(packageUser.getMaxSession() * 2);
        user.setName(EnCryptionAESService.encrypt(paramNewUser.getName()));
        user.setCnpj(EnCryptionAESService.encrypt(paramNewUser.getCnpj()));
        user.setKeyCnpj(EnCryptionAESService.hashString(paramNewUser.getCnpj()));
        user.setCell(EnCryptionAESService.encrypt(paramNewUser.getCell()));
        user.setEmail(email);
        user.setKeyEmail(EnCryptionAESService.hashString(paramNewUser.getEmail()));
        user.setPackageNameUser(EnCryptionAESService.encrypt(packageUser.getPackageName()));
        user.setPackageNumberRules(EnCryptionAESService.encrypt(String.valueOf(packageUser.getNumberRules())));
        user.setStorageFree(packageUser.getSizeStorage());
        user.setStorageTotal(packageUser.getSizeStorage());
        user.setDatesSigtap(new DatesSigtap());
        user.setTreatmentFile(new TreatmentFile(packageUser.getNumberRules()));
        user.setPassword(new BCryptPasswordEncoder().encode(paramNewUser.getPassword()));

        return user;
    }

    public void addBpa(User user, Bpa bpa, Long totalBytes) {
        user.getBpas().add(bpa);

        this.updateStorageAndSave(user, totalBytes, false);
    }


    /**
     * Conta o número total de regras do usuário.
     * @param user
     * @return
     */
    public int countRules(User user) {
        int a = user.getTreatmentFile().getRuleTreatmentPaList().size();
        int b = user.getTreatmentFile().getRuleTreatmentPaCboList().size();
        int c = user.getTreatmentFile().getRuleTreatmentPaDeleteList().size();
        int d = user.getTreatmentFile().getRuleReplacementCustoms().size();

        return a + b + c + d;
    }


    /**
     * Testa a senha o utilizador.
     * @param user
     * @param password
     * @return boolean
     */
    public boolean testPassword(User user, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.matches(password, user.getPassword());
    }


    /**
     * Atualiza a senha do utilizador.
     * @param user
     * @param currentPassword
     * @return User
     */
    public User updatePassword(User user, String currentPassword) {
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String newPassword = bc.encode(currentPassword);

        user.setPassword(newPassword);
        user.setChangePass(true);

        return userRepository.save(user);
    }


    /**
     * Verifica se um cnpj ou email (usuário) existe.
     * @param user
     * @return boolean
     */
    public boolean existe(User user) {
        Optional<User> userOptinal = userRepository.findByKeyCnpj(user.getKeyCnpj());

        if(userOptinal.isPresent()) {
            return true;
        }

        userOptinal = userRepository.findByKeyEmail(user.getKeyEmail());

        return userOptinal.isPresent();
    }


    /**
     * Retorna todas as datas dos arquivos BPA do utilizador.
     * @param bpaList
     * @return List<BpaDTO>
     */
    public List<BpaDTO> getBpaDates(List<Bpa> bpaList) {

        List<BpaDTO> bpaDTOList = new ArrayList<>();
        bpaList.forEach( bpa -> {
            BpaDTO bpaDto = new BpaDTO(bpa);
            bpaDTOList.add(bpaDto);
        });

        return bpaDTOList;
    }


    /**
     * Arredonda para duas casas decimais.
     * @param value
     * @return double
     */
    private double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    // TIMELINE

    /**
     * Retorna uma lista com as datas de arquivos BPA salvos.
     * @param user
     * @return
     */
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


    /**
     * Retorna uma lista com as datas de arquivos PROF salvos.
     * @param linkProfessionalsList
     * @return
     */
    public List<TimeLineDTO> timeLineProfessionals(List<LinkProfessionals> linkProfessionalsList) {

        List<TimeLineDTO> timeLineDTOS = new ArrayList<>();

        linkProfessionalsList.forEach( link -> {
            timeLineDTOS.add(new TimeLineDTO(link));
        });

        Comparator<TimeLineDTO> dateComparator = (dto1, dto2) -> dto2.getDate().compareTo(dto1.getDate());

        timeLineDTOS.sort(dateComparator);

        return timeLineDTOS;
    }


    /**
     * Retorna uma lista com as datas de arquivos FPO salvos.
     * @param linkFpos
     * @return
     */
    public List<TimeLineDTO> timeLineFpo(List<LinkFpo> linkFpos) {

        List<TimeLineDTO> timeLineDTOS = new ArrayList<>();

        linkFpos.forEach( link -> {
            timeLineDTOS.add(new TimeLineDTO(link));
        });

        Comparator<TimeLineDTO> dateComparator = (dto1, dto2) -> dto2.getDate().compareTo(dto1.getDate());

        timeLineDTOS.sort(dateComparator);

        return timeLineDTOS;
    }


    // STORAGE

    /**
     * Atualiza o armazenamento usado pelo usuário quando cria ou exclui um BPA ou linhas BPA.
     * @param user
     * @param totalBytes
     * @param add
     */
    public void updateStorageAndSave(User user, Long totalBytes, boolean add) {
        if(add) {
            //Adiciona armazenamento na conta do utilizador
            user.setStorageUsed(user.getStorageUsed() - totalBytes);
            user.setStorageFree(user.getStorageFree() + totalBytes);
        } else {
            //Subtrai armazenamento na conta do utilizador
            user.setStorageUsed(user.getStorageUsed() + totalBytes);
            user.setStorageFree(user.getStorageFree() - totalBytes);
        }

        this.saveAndFlush(user);
    }


    /**
     * Atualiza o armazenamento usado pelo utilizador em uma atualização de arquivo BPA.
     * @param user
     * @param bytesOld
     * @param bytesNew
     */
    public void updateStorageBpaiAndSave(User user, Long bytesOld, Long bytesNew) {

        //ADD
        user.setStorageUsed(user.getStorageUsed() - bytesOld);
        user.setStorageFree(user.getStorageFree() + bytesOld);

        //SUBTRACT
        user.setStorageUsed(user.getStorageUsed() + bytesNew);
        user.setStorageFree(user.getStorageFree() - bytesNew);

        this.save(user);
    }


    /**
     * Calcula a porcentagem de armazenamento usado pelo utilizador.
     * @param user
     * @param sizeByteBpa
     * @param sizeByteFpo
     * @param sizeByteProfe
     * @return List<Double>
     */
    public List<Double> caculatePercentStorage(User user , Long sizeByteBpa , Long sizeByteFpo , Long sizeByteProfe) {
        List<Double> percentages = new ArrayList<>();
        Long totalByteUser = user.getStorageTotal();

        double percentBpa = (sizeByteBpa * 100.0) / totalByteUser;
        double percentFpo = (sizeByteFpo * 100.0) / totalByteUser;
        double percentProfe = (sizeByteProfe * 100.0) / totalByteUser;

        percentages.add(roundToTwoDecimalPlaces(percentBpa));
        percentages.add(roundToTwoDecimalPlaces(percentFpo));
        percentages.add(roundToTwoDecimalPlaces(percentProfe));

        return percentages;

    }


    // SAVE DB

    public User save(User user) {
        return userRepository.save(user);
    }

    public User saveAndFlush(User user) {
        return userRepository.saveAndFlush(user);
    }

    public List<User> save(List<User> users) {
        return userRepository.saveAll(users);
    }

}
