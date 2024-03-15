package br.com.bpadash.api;

import br.com.bpadash.dto.user.EmployeeDTO;
import br.com.bpadash.errorValidation.ErrorResponseDTO;
import br.com.bpadash.model.user.Employee;
import br.com.bpadash.repository.user.EmployeeRepository;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.user.EmployeeService;
import br.com.bpadash.services.user.ParamLogin;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpac;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.bpa.Address;
import br.com.bpadash.model.user.CodLograd;
import br.com.bpadash.model.user.User;
import br.com.bpadash.repository.AdministratorRepository;
import br.com.bpadash.repository.CodLogradRepository;
import br.com.bpadash.repository.user.SessionUserRepository;
import br.com.bpadash.repository.user.UserRepository;
import br.com.bpadash.repository.bpa.*;
import br.com.bpadash.repository.sigtap.AddressRepository;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
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
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;

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
    @Autowired
    private SessionUserRepository sessionUserRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;


    @PostMapping( value = "/create/bpai", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> bpaCreate(@RequestPart("file") MultipartFile file)  throws IllegalArgumentException {

        List<Bpai> bpaiList = new ArrayList<>();

        try {
            InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = br.readLine()) != null) {
                 if (line.startsWith("03")) {

                    Bpai bpai = this.create(line);

                    bpaiList.add(bpai);
                }
            }

            EnCryptionAESService.encryptBpaiInitial(bpaiList);
//            EnCryptionAESService.encryptBpaiInitial(bpaiList);

            return ResponseEntity.ok(bpaiList.size());

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IOException e) {
            throw new NullPointerException();
        }
    }

    @PostMapping("/auth/auth")
    public ResponseEntity<Object> auxNext(@RequestBody ParamLogin paramLogin) {
        String email = paramLogin.getEmail();
        String password = paramLogin.getPassword();

        if(!email.equals("alam.155@gmail.com") || !password.equals("12345678")) {
            return ResponseEntity.badRequest().build();
        }

        Map<String, String> map = new HashMap<>();

        map.put("type", "Bearer");
        map.put("token", "d1oihdo12nodknqwioud90120ej1op2jeo1h8902e1092809e1982ehajklsmnbduasgdiagsdiouasndkjagbs78d1y92ndlsh97dq9dsiahodiajhiposdahjsdasd");

        return ResponseEntity.ok(map);
    }

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

    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32]; // 256 bits
        secureRandom.nextBytes(key);

        // Converta a chave para uma string base64
        String base64Key = Base64.getEncoder().encodeToString(key);

        // Decodificar a chave base64 para obter os bytes originais
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);

        System.out.println("Chave gerada: " + base64Key);
        System.out.println("Chave decodificada: " + new String(decodedKey));
    }

    @PostMapping("/ping/{employeeKey}")
    public ResponseEntity<Object> ping(@PathVariable String employeeKey) {
        User user = userRepository.getById(1L);

        List<Employee> employeeList = employeeRepository.findAll();


        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

//        employeeList.forEach( employee -> {
//            employeeDTOList.add(new EmployeeDTO(employee));
//        });

        return ResponseEntity.ok(employeeDTOList);
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

            bpaiList.get(index).setIdade(EnCryptionAESService.encrypt(pa.get(indexList)));
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
                bpai.setDtnasc(EnCryptionAESService.encrypt(bpai.getDtnasc()));
            }
        });

//        for (int i = 0; i <= 175; i++) {
//            int index = random.nextInt(bpaiList.size() - 1);
//            int indexList = random.nextInt(0, 3);
//
//            bpaiList.get(index).setDtnasc(EnCryptionAESService.encrypt(pa.get(indexList)));
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

            bpaiList.get(index).setCepPcnte(EnCryptionAESService.encrypt(pa.get(indexList)));
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

            bpaiList.get(index).setRaca(EnCryptionAESService.encrypt(pa.get(indexList)));
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

        EnCryptionAESService.decryptSex(bpaiList);

        for (int i = 0; i <= 1500; i++) {
            int index = random.nextInt(bpaiList.size() - 1);
            int indexList = random.nextInt(0, 3);

            String sex = bpaiList.get(index).getSexo();
            bpaiList.get(index).setSexo(sex.equals("M") ? "F" : "M");
            count ++;
        }

        EnCryptionAESService.encryptSex(bpaiList);

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
            bpai.setIdade(EnCryptionAESService.encrypt(bpai.getIdade()));
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
            bpai.setRaca(EnCryptionAESService.encrypt(bpai.getRaca()));
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
            bpai.setDtnasc(EnCryptionAESService.decrypt(bpai.getDtnasc()));
        }

        bpaiRepository.saveAll(bpaiList);

        return ResponseEntity.ok().build();
    }


    private Bpai create(String line) {

        String ident = line.substring(0, 2);
        String cnes = line.substring(2, 9);
        String cmp = line.substring(9, 15);
        String cnsmed = line.substring(15, 30);
        String cbo = line.substring(30, 36);
        String dtaten = line.substring(36, 44);  // 6
        String flh = line.substring(44, 47);
        String seq = line.substring(47, 49);
        String pa = line.substring(49, 59);
        String cnspac = line.substring(59, 74);
        String sexo = line.substring(74, 75);
        String ibge = line.substring(75, 81);
        String cid = line.substring(81, 85);
        String idade = line.substring(85, 88);
        String qt = line.substring(88, 94);
        String caten = line.substring(94, 96);
        String naut = line.substring(96, 109);
        String org = line.substring(109, 112);
        String nmpac = line.substring(112, 142);  // 19
        String dtnasc = line.substring(142, 150);  // 20
        String raca = line.substring(150, 152);  // 21
        String etnia = line.substring(152, 156);  // 22
        String nac = line.substring(156, 159);  // 23
        String srv = line.substring(159, 162);  // 24
        String clf = line.substring(162, 165);  // 25
        String equipe_seq = line.substring(165, 173);  // 26
        String equipe_area = line.substring(173, 177);  // 27
        String cnpj = line.substring(177, 191);  // 28
        String cep_pcnte = line.substring(191, 199);  // 29
        String lograd_pcnte = line.substring(199, 202);  // 30
        String end_pcnte = line.substring(202, 232);  // 31
        String compl_pcnte = line.substring(232, 242);  // 32

        String num_pcnte;
        try {
            num_pcnte = line.substring(242, 247);
        } catch (StringIndexOutOfBoundsException e) {
            num_pcnte = "     ";
        }

        String bairro_pcnte;
        try {
            bairro_pcnte = line.substring(247, 277);
        } catch (StringIndexOutOfBoundsException e) {
            bairro_pcnte = "                              ";
        }

        String ddtel_pcnte;
        try {
            ddtel_pcnte = line.substring(277, 288);
        } catch (StringIndexOutOfBoundsException e) {
            ddtel_pcnte = "           ";
        }

        String email_pcnte;
        try {
            email_pcnte = line.substring(288, 328); // 38
        } catch (StringIndexOutOfBoundsException e) {
            email_pcnte = "                                        ";
        }

        String ine;
        try {
            ine = line.substring(328, 338); // 38
        } catch (StringIndexOutOfBoundsException e) {
            ine = "          ";
        }

        String fim;
        try {
            fim = line.substring(338, 340); // 38
        } catch (StringIndexOutOfBoundsException e) {
            fim = "  ";
        }


        Bpai bpai = new Bpai(
                null,
                String.valueOf(0),
                ident,
                cnes,
                cmp,
                cnsmed,
                cbo,
                dtaten,
                flh,
                seq,
                pa,
                cnspac,
                sexo,
                ibge,
                cid,
                idade,
                qt,
                caten,
                naut,
                org,
                nmpac,
                dtnasc,
                raca,
                etnia,
                nac,
                srv,
                clf,
                equipe_seq,
                equipe_area,
                cnpj,
                cep_pcnte,
                lograd_pcnte,
                end_pcnte,
                compl_pcnte,
                num_pcnte,
                bairro_pcnte,
                ddtel_pcnte,
                email_pcnte,
                ine,
                fim
        );

        return bpai;
    }
}
