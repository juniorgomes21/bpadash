package br.com.bpadash.services.scanner;

import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.*;
import br.com.bpadash.params.bpa.ParamNewBpa;
import br.com.bpadash.params.fpo.ParamNewFpo;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpacService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.bpa.TitleBpaService;
import br.com.bpadash.services.fpo.FpoService;
import br.com.bpadash.services.fpo.LinkFpoService;
import br.com.bpadash.services.professional.DadosVincService;
import br.com.bpadash.services.professional.ProfessionalService;
import br.com.bpadash.services.user.StorageService;
import br.com.bpadash.services.user.UserService;
import br.com.bpadash.utilities.Utilities;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScannerFile {

    @Autowired
    private BpaService bpaService;

    @Autowired
    private TitleBpaService titleBpaService;

    @Autowired
    private BpacService bpacService;

    @Autowired
    private BpaiService bpaiService;

    @Autowired
    private UserService userService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private FpoService fpoService;

    @Autowired
    private LinkFpoService linkFpoService;

    @Autowired
    private ProfessionalService professionalService;

    @Autowired
    private DadosVincService dadosVincService;

    @Transactional
    public String createBpa(MultipartFile file, User user, ParamNewBpa paramNewBpa, List<ErrorsFile> errorsFileList, StopWatch startTime) throws IllegalArgumentException {
        TitleBpa titleBpa = new TitleBpa();
        List<Bpac> bpacList = new ArrayList<>();
        List<Bpai> bpaiList = new ArrayList<>();

        Bpa bpa = new Bpa(user, bpaService.generateIdentifier(user), paramNewBpa);

        if(bpaService.get(bpa.getDate(), user).isPresent()) {
            return "EXIST DATE";
        }

        System.out.println("Viu se a data existe " + startTime.getTime() + " milissegundos: " + startTime.getTime()/1000);

        try {
            // Obtém o fluxo de entrada do arquivo
            Long xxx = file.getSize();
            InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                if(line.startsWith("01")) {
                    titleBpa = titleBpaService.createTitleBpa(line, lineNumber, bpa, errorsFileList);

                } else if (line.startsWith("02")) {
                    Bpac bpac = bpacService.createBpac(user, line, lineNumber, bpa, errorsFileList);
                    if(bpac != null) {
                        bpacList.add(bpac);
                    }

                } else if (line.startsWith("03")) {
                    Bpai bpai = bpaiService.create(line, lineNumber, bpa);
                    if(bpai != null) {
                        bpaiList.add(bpai);
                    }
                }

                lineNumber++;
            }
            System.out.println("criou todas as linhas do BPA: " + startTime.getTime() + " milissegundos: " + startTime.getTime()/1000);

            if(!errorsFileList.isEmpty()) {
                return "ERROR FILE";
            }
            System.out.println("criptografando: " + startTime.getTime() + " milissegundos: " + startTime.getTime()/1000);

            EncryptionService.encryptBpai(bpaiList); // de 5 a 6 segundos para executar

            System.out.println("Criptografado: " + startTime.getTime() + " milissegundos: " + startTime.getTime()/1000);

            Long totalBytes = storageService.hasStorage(bpa, titleBpa, bpacList, bpaiList);

            System.out.println("Calculou o tamanho do arquivo: " + startTime.getTime() + " milissegundos: " + startTime.getTime()/1000);

            if(user.getStorageFree() < totalBytes) {
                return "NOT STORAGE";
            }

            bpaService.updatebyte(bpa, totalBytes, true);

            titleBpaService.save(titleBpa);

            if(!bpacList.isEmpty()) {
                bpacService.save(bpacList);
            }

            if(!bpaiList.isEmpty()) {
                bpaiService.save(bpaiList, startTime);
            }

            userService.addBpa(user, bpa, totalBytes);

            System.out.println("Save all: " + startTime.getTime() + " milissegundos: " + startTime.getTime()/1000);


            return "CREATE";


        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IOException e) {
            throw new NullPointerException();
        }
    }

    public Bpa createBpac(User user, MultipartFile file , Bpa bpa, List<ErrorsFile> errorsFileList) throws IllegalArgumentException {
        List<Bpac> bpacList = new ArrayList<>();


        try {
            // Obtém o fluxo de entrada do arquivo
            InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("02")) {
                    Bpac bpac = bpacService.createBpac(user, line, lineNumber, bpa, errorsFileList);
                    bpacList.add(bpac);
                }

                lineNumber++;
            }

            if(!errorsFileList.isEmpty()) {
                return null;
            }

            Long byteBpac = 0L;
            if(!bpacList.isEmpty()) {
                List<Bpac> listBpac = bpacService.save(bpacList);
                List<Long> ids = new ArrayList<>();
                listBpac.forEach( bpac -> {
                    ids.add(bpac.getId());
                });

                byteBpac = bpacService.sizeByte(ids);
            }


            bpaService.updatebyte(bpa, byteBpac, true);

            return bpa;

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IOException e) {
            throw new NullPointerException();
        }
    }

    public Bpa createBpai(MultipartFile file , Bpa bpa) throws IllegalArgumentException {
        List<Bpai> bpaiList = new ArrayList<>();

        try {
            // Obtém o fluxo de entrada do arquivo
            InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                 if (line.startsWith("03")) {
                    Bpai bpai = bpaiService.create(line, lineNumber, bpa);
                    bpaiList.add(bpai);
                 }

                lineNumber++;
            }

            Long byteBpai = 0L;
            if(!bpaiList.isEmpty()) {
                //TODO descomentar isso
//                List<Bpai> listBpai = bpaiService.save(bpaiList);
                List<Long> ids = new ArrayList<>();
//                listBpai.forEach( bpac -> {
//                    ids.add(bpac.getId());
//                });

                byteBpai = bpaiService.sizeByte(ids);
            }

            bpaService.updatebyte(bpa, byteBpai, true);

            return bpa;

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IOException e) {
            throw new NullPointerException();

        }
    }

    public String createFpo(MultipartFile file, ParamNewFpo paramNewFpo, User user, List<ErrorsFile> errorsFileList) throws IllegalArgumentException {
        try {

            if(user.getStorageFree() < file.getSize()) {
                return "NOT STORAGE";
            }

            LocalDate date = Utilities.formatDate(paramNewFpo.getDate());
            if(linkFpoService.get(user, date).isPresent()) {
                return "EXIST DATE";
            }

            LinkFpo linkFpo = new LinkFpo(paramNewFpo, user, date);

            InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            int lineNumber = 2;
            List<Fpo> fpoList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                try {
                    String pa = line.substring(2, 11);
                    if (pa.matches("\\d+")) {
                        Fpo fpo = fpoService.create(linkFpo, pa, line, lineNumber, errorsFileList);
                        if(fpo != null) {
                            fpoList.add(fpo);
                        }
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    // Pula a linha sem informações
                }
                lineNumber++;
            }

            if(!errorsFileList.isEmpty()) {
                return "ERROR FILE";
            }

            linkFpoService.save(linkFpo);

            fpoService.save(fpoList);

            linkFpoService.addFpos(linkFpo, fpoList);

            userService.addFpo(user, linkFpo, file.getSize());

            return "CREATE";

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IOException e) {
            throw new NullPointerException();

        }
    }

    public String createProfessionals(MultipartFile file, User user, List<ErrorsFile> errorsFileList) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            InputStream inputStream = file.getInputStream();
            Document document = builder.parse(inputStream);
            Element root = document.getDocumentElement();
            NodeList profissionalElements = root.getElementsByTagName("DADOS_PROFISSIONAIS");

            List<ProfessionalComplete> professionalCompleteList = new ArrayList<>();
            List<DadosVinc> dadosVincList = new ArrayList<>();
            for (int i = 0; i < profissionalElements.getLength(); i++) {
                Element profissionalElem = (Element) profissionalElements.item(i);

                String profId = profissionalElem.getAttribute("PROF_ID");
                String cpf = profissionalElem.getAttribute("CPF_PROF");
                String pispasep = profissionalElem.getAttribute("PISPASEP");
                String name = profissionalElem.getAttribute("NOME_PROF");
                String nameMother = profissionalElem.getAttribute("NOME_MAE");
                String birthDate = profissionalElem.getAttribute("DATA_NASC");
                String codMun = profissionalElem.getAttribute("COD_MUN");
                String sexo = profissionalElem.getAttribute("SEXO");
                String numBook = profissionalElem.getAttribute("NUM_LIVRO");
                String numSheet = profissionalElem.getAttribute("NUM_FOLHA");
                String numTerm = profissionalElem.getAttribute("NUM_TERMO");
                String codorgemis = profissionalElem.getAttribute("CODORGEMIS");
                String dateEmiss = profissionalElem.getAttribute("DATA_EMISS");
                String numIdent = profissionalElem.getAttribute("NUM_IDENT");
                String siglaEst = profissionalElem.getAttribute("SIGLA_EST");
                String dtemiident = profissionalElem.getAttribute("DTEMIIDENT");
                String dateEntra = profissionalElem.getAttribute("DATA_ENTRA");
                String ctpsNumer = profissionalElem.getAttribute("CTPS_NUMER");
                String serie = profissionalElem.getAttribute("SERIE");
                String sigestctps = profissionalElem.getAttribute("SIGESTCTPS");
                String dtemisctps = profissionalElem.getAttribute("DTEMISCTPS");
                String logradouro = profissionalElem.getAttribute("LOGRADOURO");
                String number = profissionalElem.getAttribute("NUMERO");
                String complement = profissionalElem.getAttribute("COMPLEMENT");
                String bairrodist = profissionalElem.getAttribute("BAIRRODIST");
                String codCep = profissionalElem.getAttribute("COD_CEP");
                String siglaUf = profissionalElem.getAttribute("SIGLA_UF");
                String codEscolar = profissionalElem.getAttribute("CODESCOLAR");
                String codCertid = profissionalElem.getAttribute("COD_CERTID");
                String indNacio = profissionalElem.getAttribute("IND_NACIO");
                String nameCarto = profissionalElem.getAttribute("NOME_CARTO");
                String codBanc = profissionalElem.getAttribute("COD_BANCO");
                String nameCountry = profissionalElem.getAttribute("NOME_PAIS");
                String numAgenc = profissionalElem.getAttribute("NUM_AGENC");
                String contaCc = profissionalElem.getAttribute("CONTA_CC");
                String codCns = profissionalElem.getAttribute("COD_CNS");
                String dTercsih = profissionalElem.getAttribute("D_TERCSIH");
                String status = profissionalElem.getAttribute("STATUS");
                String statusmov = profissionalElem.getAttribute("STATUSMOV");
                String date = profissionalElem.getAttribute("DATA_ATU");
                String userProf = profissionalElem.getAttribute("USUARIO");
                String cdRaca = profissionalElem.getAttribute("CD_RACA");
                String telephone = profissionalElem.getAttribute("TELEFONE");
                String nameFather = profissionalElem.getAttribute("NOME_PAI");
                String cdTpLogr = profissionalElem.getAttribute("CD_TP_LOGR");
                String portaria = profissionalElem.getAttribute("PORTARIA");
                String dtNatur = profissionalElem.getAttribute("DT_NATUR");
                String codCountry = profissionalElem.getAttribute("CD_PAIS");

                ProfessionalComplete professionalComplete = new ProfessionalComplete(
                    profId,
                    cpf,
                    pispasep,
                    name,
                    nameMother,
                    birthDate,
                    codMun,
                    sexo,
                    numBook,
                    numSheet,
                    numTerm,
                    codorgemis,
                    dateEmiss,
                    numIdent,
                    siglaEst,
                    dtemiident,
                    dateEntra,
                    ctpsNumer,
                    serie,
                    sigestctps,
                    dtemisctps,
                    logradouro,
                    number,
                    complement,
                    bairrodist,
                    codCep,
                    siglaUf,
                    codEscolar,
                    codCertid,
                    indNacio,
                    nameCarto,
                    codBanc,
                    nameCountry,
                    numAgenc,
                    contaCc,
                    codCns,
                    dTercsih,
                    status,
                    statusmov,
                    date,
                    userProf,
                    cdRaca,
                    telephone,
                    nameFather,
                    cdTpLogr,
                    portaria,
                    dtNatur,
                    codCountry
                );

                NodeList vinculosElements = profissionalElem.getElementsByTagName("DADOS_VINC_PROF");
                DadosVinc dadosVinc = new DadosVinc();
                for (int j = 0; j < vinculosElements.getLength(); j++) {
                    Element vinculoElem = (Element) vinculosElements.item(j);

                    String codCbo = vinculoElem.getAttribute("COD_CBO");
                    String indVinc = vinculoElem.getAttribute("IND_VINC");
                    String cghoraoutr = vinculoElem.getAttribute("CGHORAOUTR");
                    String cghoraamb = vinculoElem.getAttribute("CG_HORAAMB");
                    String conselhoid = vinculoElem.getAttribute("CONSELHOID");
                    String n_registro = vinculoElem.getAttribute("N_REGISTRO");
                    String vinculo_sus = vinculoElem.getAttribute("VINCULO_SUS");
                    String usuario_vinculo = vinculoElem.getAttribute("USUARIO");
                    String cghorahosp = vinculoElem.getAttribute("CGHORAHOSP");

                    dadosVinc = new DadosVinc(
                        codCbo,
                        indVinc,
                        cghoraoutr,
                        cghoraamb,
                        conselhoid,
                        n_registro,
                        vinculo_sus,
                        usuario_vinculo,
                        cghorahosp
                    );

                }
                professionalComplete.setDadosVinc(dadosVinc);
                professionalCompleteList.add(professionalComplete);
                dadosVincList.add(dadosVinc);
            }

            EncryptionService.encrypt(professionalCompleteList);

            long totalBytes = storageService.hasStorage(professionalCompleteList);

            if(user.getStorageFree() < totalBytes) {
                return "NOT STORAGE";
            }

            dadosVincService.save(dadosVincList);
            professionalService.save(professionalCompleteList);

            userService.addProfessionals(professionalCompleteList, user);
            userService.updateStorageAndSave(user, totalBytes, "sub");

            return "CREATE";
        } catch (IllegalArgumentException | ParserConfigurationException | IOException | SAXException e) {
            return "ERROR";
        }
    }
}
