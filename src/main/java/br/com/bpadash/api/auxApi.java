package br.com.bpadash.api;

import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.Administrator;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpac;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.bpa.Address;
import br.com.bpadash.model.user.CodLograd;
import br.com.bpadash.model.user.User;
import br.com.bpadash.repository.AdministratorRepository;
import br.com.bpadash.repository.CodLogradRepository;
import br.com.bpadash.repository.UserRepository;
import br.com.bpadash.repository.bpa.*;
import br.com.bpadash.repository.sigtap.AddressRepository;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.utilities.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.*;
import java.security.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/aux")
public class auxApi {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TitleBpaRepository titleBpaRepository;
    @Autowired
    private BpaRepository bpaRepository;
    @Autowired
    private BpaiRepository bpaiRepository;
    @Autowired
    private BpacRepository bpacRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private TitleValidationRepository titleValidationRepository;
    @Autowired
    private CodLogradRepository codLogradRepository;
    @Autowired
    private AdministratorRepository administratorRepository;
    @Autowired
    private BpaiService bpaiService;
    @Autowired
    private BpaService bpaService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EntityManager entityManager;


    @PostMapping("/delete/bpai")
    public ResponseEntity<Object> deleteBpai() {

        bpaiRepository.deleteAll();

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete/all")
    public ResponseEntity<Object> deleteAll() {

        titleBpaRepository.deleteAll();
        bpaiRepository.deleteAll();
        bpacRepository.deleteAll();
        bpaRepository.deleteAll();

        return ResponseEntity.ok().build();
    }


    @PostMapping("/ping")
    public ResponseEntity<Object> ping() {
        User user = userRepository.getById(1L);
        Bpa bpa = bpaRepository.getById(50L);

        bpaService.calculateInvoicing(bpa, null, null, null, user, true);


        return ResponseEntity.ok().build();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Bpai> get(Bpa bpa) {
        return bpaiRepository.findByBpa(bpa);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLog() {
        CodLograd codLograd = new CodLograd("123", "43211");

        codLogradRepository.save(codLograd);
    }

    @PostMapping(value = "/file/cep", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> pingx(@RequestPart("file") MultipartFile file) throws IOException {
        User user = userRepository.getById(1L);
        List<ErrorsFile> errorsFileList = new ArrayList<>();
        List<Bpai> bpaiList = new ArrayList<>();

        InputStream inputStream = file.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        int lineNumber = 1;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("03")) {
                Bpai bpai = bpaiService.create(line, lineNumber, null, user, errorsFileList);
                if(bpai != null) {
                    bpaiList.add(bpai);
                }
            }

            lineNumber++;
        }

        List<Address> addresseDB = addressRepository.findAll();
        List<Address> addressList = new ArrayList<>();

        bpaiList.forEach( bpai -> {
            if(!bpai.getCepPcnte().isBlank()) {
                String cepPcnte =  bpai.getCepPcnte();

                boolean isOk = addresseDB.stream().anyMatch( address -> address.getCep().equals(cepPcnte));
                boolean isOk2 = addressList.stream().anyMatch( address -> address.getCep().equals(cepPcnte));

                if(!isOk && !isOk2) {
                    String logradPcnte= bpai.getLogradPcnte();
                    String complPcnte = bpai.getComplPcnte();
                    String endPcnte = bpai.getEndPcnte();
                    String bairroPcnte = bpai.getBairroPcnte();

                    if(!cepPcnte.isBlank() || !logradPcnte.isBlank() || !complPcnte.isBlank() || !endPcnte.isBlank() || !bairroPcnte.isBlank()) {
                        addressList.add(new Address(bpai));
                    }
                }
            }
        });

        addressRepository.saveAll(addressList);

        return ResponseEntity.ok(addressList);
    }

    @PostMapping("/add/erros/pa")
    public ResponseEntity<Object> errosA() {
        Random random = new Random();
        int count = 0;
        Bpa bpa = bpaRepository.getById(8L);

        List<String> pa = new ArrayList<>(Arrays.asList("1201012010", "0000001110", "2221113330", "0123210123"));

        List<Bpai> bpaiList = bpaiRepository.findByBpa(bpa);

        for (int i = 0; i <= 10; i++) {
            int index = random.nextInt(bpaiList.size() - 1);
            int indexList = random.nextInt(0, 3);

            bpaiList.get(index).setPa(pa.get(indexList));
            count ++;
        }

        bpaiRepository.saveAll(bpaiList);

        return ResponseEntity.ok("erros PA adicionados, count: " + count);
    }

    @PostMapping("/add/erros/ageMinMax")
    public ResponseEntity<Object> errosB() {
        Random random = new Random();
        int count = 0;
        Bpa bpa = bpaRepository.getById(8L);

        List<String> pa = new ArrayList<>(Arrays.asList("135", "256", "768", "999"));

        List<Bpai> bpaiList = bpaiRepository.findByBpa(bpa);

        for (int i = 0; i <= 10; i++) {
            int index = random.nextInt(bpaiList.size() - 1);
            int indexList = random.nextInt(0, 3);

            bpaiList.get(index).setIdade(EncryptionService.encrypt(pa.get(indexList)));
            count ++;
        }

        bpaiRepository.saveAll(bpaiList);

        return ResponseEntity.ok("erros Idades adicionados, count: " + count);
    }

    @PostMapping("/add/erros/ageDate")
    public ResponseEntity<Object> errosC() {
        Random random = new Random();
        int count = 0;
        Bpa bpa = bpaRepository.getById(8L);


//        List<String> pa = new ArrayList<>(Arrays.asList("17150501", "18950101", "18990301", "18771201"));

        List<Bpai> bpaiList = bpaiRepository.findByBpa(bpa);

        bpaiList.forEach( bpai -> {
            if(bpai.getDtnasc().length() == 8) {
                bpai.setDtnasc(EncryptionService.encrypt(bpai.getDtnasc()));
            }
        });

//        for (int i = 0; i <= 175; i++) {
//            int index = random.nextInt(bpaiList.size() - 1);
//            int indexList = random.nextInt(0, 3);
//
//            bpaiList.get(index).setDtnasc(EncryptionService.encrypt(pa.get(indexList)));
//            count ++;
//        }

        bpaiRepository.saveAll(bpaiList);

        return ResponseEntity.ok("erros Idades ageDate adicionados, count: " + count);
    }

    @PostMapping("/add/erros/cep")
    public ResponseEntity<Object> errosD() {
        Random random = new Random();
        int count = 0;
        Bpa bpa = bpaRepository.getById(1L);

        List<String> pa = new ArrayList<>(Arrays.asList("22200555", "66600000", "65611111", "62655555"));

        List<Bpai> bpaiList = bpaiRepository.findByBpa(bpa);

        for (int i = 0; i <= 55; i++) {
            int index = random.nextInt(bpaiList.size() - 1);
            int indexList = random.nextInt(0, 3);

            bpaiList.get(index).setCepPcnte(EncryptionService.encrypt(pa.get(indexList)));
            count ++;
        }

        bpaiRepository.saveAll(bpaiList);

        return ResponseEntity.ok("erros Idades adicionados, count: " + count);
    }

    @PostMapping("/add/erros/qt")
    public ResponseEntity<Object> errosE() {
        Random random = new Random();
        int count = 0;
        Bpa bpa = bpaRepository.getById(6L);

        List<String> pa = new ArrayList<>(Arrays.asList("535498", "899826", "341255", "785999"));

        List<Bpai> bpaiList = bpaiRepository.findByBpa(bpa);

        for (int i = 0; i <= 225; i++) {
            int index = random.nextInt(bpaiList.size() - 1);
            int indexList = random.nextInt(0, 3);

            bpaiList.get(index).setQt(pa.get(indexList));
            count ++;
        }

        bpaiRepository.saveAll(bpaiList);

        return ResponseEntity.ok("erros Idades adicionados, count: " + count);
    }

    @PostMapping("/add/erros/dateService")
    public ResponseEntity<Object> errosF() {
        Random random = new Random();
        int count = 0;
        Bpa bpa = bpaRepository.getById(6L);

        List<String> pa = new ArrayList<>(Arrays.asList("199905", "202105", "202501", "202002"));

        List<Bpai> bpaiList = bpaiRepository.findByBpa(bpa);

        for (int i = 0; i <= 225; i++) {
            int index = random.nextInt(bpaiList.size() - 1);
            int indexList = random.nextInt(0, 3);

            bpaiList.get(index).setDtaten(pa.get(indexList));
            count ++;
        }

        bpaiRepository.saveAll(bpaiList);

        return ResponseEntity.ok("erros Idades adicionados, count: " + count);
    }

    @PostMapping("/add/erros/race")
    public ResponseEntity<Object> errosG() {
        Random random = new Random();
        int count = 0;
        Bpa bpa = bpaRepository.getById(6L);

        List<String> pa = new ArrayList<>(Arrays.asList("08", "07", "09", "99"));

        List<Bpai> bpaiList = bpaiRepository.findByBpa(bpa);

        for (int i = 0; i <= 155; i++) {
            int index = random.nextInt(bpaiList.size() - 1);
            int indexList = random.nextInt(0, 3);

            bpaiList.get(index).setRaca(EncryptionService.encrypt(pa.get(indexList)));
            count ++;
        }

        bpaiRepository.saveAll(bpaiList);

        return ResponseEntity.ok("erros Raça adicionados, count: " + count);
    }

    @PostMapping("/add/erros/cnsmed")
    public ResponseEntity<Object> errosH() {
        Random random = new Random();
        int count = 0;
        Bpa bpa = bpaRepository.getById(6L);

        List<String> pa = new ArrayList<>(Arrays.asList("709 809 070 111 111", "709809070922399", "709803450999999", "709809066666699"));

        List<Bpai> bpaiList = bpaiRepository.findByBpa(bpa);

        for (int i = 0; i <= 255; i++) {
            int index = random.nextInt(bpaiList.size() - 1);
            int indexList = random.nextInt(0, 3);
            String cnsmed = random.nextInt(99999999) + String.valueOf(random.nextInt(9999999));

            bpaiList.get(index).setCnsmed(cnsmed);
            count ++;
        }

        bpaiRepository.saveAll(bpaiList);

        return ResponseEntity.ok("erros Raça adicionados, count: " + count);
    }

    @PostMapping("/add/erros/sex")
    public ResponseEntity<Object> errosI() {
        Random random = new Random();
        int count = 0;
        Bpa bpa = bpaRepository.getById(6L);

        List<Bpai> bpaiList = bpaiRepository.findByBpa(bpa);

        EncryptionService.decryptSex(bpaiList);

        for (int i = 0; i <= 1500; i++) {
            int index = random.nextInt(bpaiList.size() - 1);
            int indexList = random.nextInt(0, 3);

            String sex = bpaiList.get(index).getSexo();
            bpaiList.get(index).setSexo(sex.equals("M") ? "F" : "M");
            count ++;
        }

        EncryptionService.encryptSex(bpaiList);

        bpaiRepository.saveAll(bpaiList);

        return ResponseEntity.ok("erros Raça adicionados, count: " + count);
    }

    @PostMapping("/add/erros/occupation")
    public ResponseEntity<Object> errosJ() {
        Random random = new Random();
        int count = 0;
        Bpa bpa = bpaRepository.getById(6L);

        List<String> pa = new ArrayList<>(Arrays.asList("000222", "444000", "545454", "787878"));
        List<String> pax = new ArrayList<>(Arrays.asList("558844", "446633", "456456", "112233"));

        List<Bpai> bpaiList = bpaiRepository.findByBpa(bpa);
        List<Bpac> bpacList = bpacRepository.findByBpa(bpa);

        for (int i = 0; i <= 285; i++) {
            int index = random.nextInt(bpaiList.size() - 1);
            int indexList = random.nextInt(0, 3);

            bpaiList.get(index).setCbo(pa.get(indexList));
            count ++;
        }

        for (int i = 0; i <= 155; i++) {
            int index = random.nextInt(bpacList.size() - 1);
            int indexList = random.nextInt(0, 3);

            bpacList.get(index).setCbo(pax.get(indexList));
            count ++;
        }

        bpacRepository.saveAll(bpacList);
        bpaiRepository.saveAll(bpaiList);

        return ResponseEntity.ok("erros Raça adicionados, count: " + count);
    }

    @PostMapping("/cripto/idade")
    public ResponseEntity<Object> crp() {

        User user = userRepository.getById(1L);
        LocalDate date = Utilities.formatDate("2023-06-01");

        Bpa bpa = bpaRepository.findByDateAndUser(date, user).get();

        List<Bpai> bpaiList = bpaiRepository.findByBpa(bpa);

        for (Bpai bpai: bpaiList) {
            bpai.setIdade(EncryptionService.encrypt(bpai.getIdade()));
        }

        bpaiRepository.saveAll(bpaiList);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/cripto/race")
    public ResponseEntity<Object> race() {

        User user = userRepository.getById(1L);
        LocalDate date = Utilities.formatDate("2023-06-01");

        Bpa bpa = bpaRepository.findByDateAndUser(date, user).get();

        List<Bpai> bpaiList = bpaiRepository.findByBpa(bpa);

        for (Bpai bpai: bpaiList) {
            bpai.setRaca(EncryptionService.encrypt(bpai.getRaca()));
        }

        bpaiRepository.saveAll(bpaiList);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/dcripto/dtnasc")
    public ResponseEntity<Object> dcrp() {

        User user = userRepository.getById(1L);
        LocalDate date = Utilities.formatDate("2023-11-1");

        Bpa bpa = bpaRepository.findByDateAndUser(date, user).get();

        List<Bpai> bpaiList = bpaiRepository.findByBpa(bpa);

        for (Bpai bpai: bpaiList) {
            bpai.setDtnasc(EncryptionService.decrypt(bpai.getDtnasc()));
        }

        bpaiRepository.saveAll(bpaiList);

        return ResponseEntity.ok().build();
    }

}
