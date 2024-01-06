package br.com.bpadash.services.scanner;

import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.*;
import br.com.bpadash.params.bpa.ParamNewBpa;
import br.com.bpadash.params.fpo.ParamNewFpo;
import br.com.bpadash.params.professional.ParamNewProfessionals;
import br.com.bpadash.params.sigtap.ParamNewCep;
import br.com.bpadash.params.sigtap.ParamNewOccupation;
import br.com.bpadash.params.sigtap.ParamNewProcedure;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpacService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.bpa.TitleBpaService;
import br.com.bpadash.services.fpo.FpoService;
import br.com.bpadash.services.fpo.LinkFpoService;
import br.com.bpadash.services.professional.DadosVincService;
import br.com.bpadash.services.professional.LinkProfessionalsService;
import br.com.bpadash.services.professional.ProfessionalService;
import br.com.bpadash.services.sigtap.*;
import br.com.bpadash.services.user.StorageService;
import br.com.bpadash.services.user.UserService;
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
import java.nio.charset.StandardCharsets;
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

    @Autowired
    private LinkProfessionalsService linkProfessionalsService;

    @Autowired
    private LinkOccupationService linkOccupationService;

    @Autowired
    private OccupationService occupationService;

    @Autowired
    private LinkProcedureService linkProcedureService;

    @Autowired
    private ProcedureService procedureService;

    @Autowired
    private CepService cepService;

    @Autowired
    private LinkCepService linkCepService;

    @Transactional
    public String createBpa(MultipartFile file, User user, ParamNewBpa paramNewBpa, List<ErrorsFile> errorsFileList, StopWatch startTime) throws IllegalArgumentException {
        TitleBpa titleBpa = new TitleBpa();
        List<Bpac> bpacList = new ArrayList<>();
        List<Bpai> bpaiList = new ArrayList<>();

        Bpa bpa = new Bpa(user, bpaService.generateIdentifier(user), paramNewBpa);

        try {
            InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                if(line.startsWith("01")) {
                    titleBpa = titleBpaService.create(line, lineNumber, bpa, errorsFileList);
                    if(titleBpa != null) {
                        int year = Integer.parseInt(titleBpa.getMvm().substring(0, 4));
                        int month = Integer.parseInt(titleBpa.getMvm().substring(4, 6));

                        LocalDate date;
                        try {
                            date = LocalDate.of(year, month, 1);
                        } catch (Exception e) {
                            return "ERROR FORMAT DATE";
                        }

                        if(bpaService.get(date, user).isPresent()) {
                            return "EXIST DATE";
                        } else {
                            bpa.setDate(date);
                        }
                    } else {
                        return "ERROR FILE";
                    }
                } else if (line.startsWith("02")) {
                    Bpac bpac = bpacService.create(user, line, lineNumber, bpa, errorsFileList);
                    if(bpac != null) {
                        bpacList.add(bpac);
                    }

                } else if (line.startsWith("03")) {
                    Bpai bpai = bpaiService.create(line, lineNumber, bpa, user, errorsFileList);
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

            EncryptionService.encryptBpai(bpaiList);

            System.out.println("Criptografado: " + startTime.getTime() + " milissegundos: " + startTime.getTime()/1000);

            Long totalBytes = storageService.quantityBytes(titleBpa, bpacList, bpaiList);

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

    public String createBpac(User user, MultipartFile file, Bpa bpa, Bpac bpacS, List<ErrorsFile> errorsFiles) throws IllegalArgumentException {
        List<Bpac> bpacList = new ArrayList<>();


        try {
            // Obtém o fluxo de entrada do arquivo
            InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            int lineNumber = 1;
            String seq = bpacS.getSeq();
            String flh = bpacS.getFlh();

            if ("99".equals(seq) && "999".equals(flh)) {
                return "FILE FULL";
            }

            int flhInt = Integer.parseInt(flh);
            int seqInt = Integer.parseInt(seq);

            while ((line = br.readLine()) != null) {
                if (line.startsWith("02")) {

                    Bpac bpac = bpacService.create(user, line, lineNumber, bpa, errorsFiles);
                    if(bpac != null) {
                        if (flhInt == 999 && seqInt == 99) {
                            return "FILE FULL";
                        }

                        // Incrementa seq em cada iteração
                        seqInt = (seqInt % 99) + 1;

                        // Se flh for 1, incrementa seq
                        if (seqInt == 1) {
                            flhInt = (flhInt % 999) + 1;
                        }

                        // Formata os valores para o formato desejado
                        String formattedFlh = String.format("%03d", flhInt);
                        String formattedSeq = String.format("%02d", seqInt);

                        bpac.setSeq(formattedSeq);
                        bpac.setFlh(formattedFlh);
                        bpacList.add(bpac);
                    }
                }

                lineNumber++;
            }

            if(!errorsFiles.isEmpty()) {
                return "ERROR FILE";
            }

            Long totalBytes = storageService.quantityBytes(null, bpacList, null);

            if(user.getStorageFree() < totalBytes) {
                return "NOT STORAGE";
            }

            if(!bpacList.isEmpty()) {
                bpacService.save(bpacList);
            }

            userService.updateStorageAndSave(user, totalBytes, "sub");
            bpaService.updatebyte(bpa, totalBytes, true);

            return "CREATE";

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IOException e) {
            throw new NullPointerException();
        }
    }

    public String createBpai(MultipartFile file , Bpa bpa, Bpai bpaiS, User user, List<ErrorsFile> errorsFiles) throws IllegalArgumentException {
        List<Bpai> bpaiList = new ArrayList<>();

        try {
            InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            int lineNumber = 1;
            String seq = bpaiS.getSeq();
            String flh = bpaiS.getFlh();

            if ("99".equals(seq) && "999".equals(flh)) {
                return "FILE FULL";
            }

            int flhInt = Integer.parseInt(flh);
            int seqInt = Integer.parseInt(seq);

            while ((line = br.readLine()) != null) {

                if (line.startsWith("03")) {

                     Bpai bpai = bpaiService.create(line, lineNumber, bpa, user, errorsFiles);
                     if(bpai != null) {
                         // Verifica se ambos seq e flh atingiram seus valores máximos
                         if (flhInt == 999 && seqInt == 99) {
                             return "FILE FULL";
                         }

                         // Incrementa seq em cada iteração
                         seqInt = (seqInt % 99) + 1;

                         // Se flh for 1, incrementa seq
                         if (seqInt == 1) {
                             flhInt = (flhInt % 999) + 1;
                         }

                         // Formata os valores para o formato desejado
                         String formattedFlh = String.format("%03d", flhInt);
                         String formattedSeq = String.format("%02d", seqInt);

                         // Define os valores na entidade
                         bpai.setSeq(formattedSeq);
                         bpai.setFlh(formattedFlh);
                         bpaiList.add(bpai);
                     }
                }


                lineNumber++;
            }

            if(!errorsFiles.isEmpty()) {
                return "ERROR FILE";
            }

            EncryptionService.encryptBpai(bpaiList);

            Long totalBytes = storageService.quantityBytes(null, null, bpaiList);

            if(user.getStorageFree() < totalBytes) {
                return "NOT STORAGE";
            }
            if(!bpaiList.isEmpty()) {
                bpaiService.save(bpaiList);
            }

            userService.updateStorageAndSave(user, totalBytes, "sub");
            bpaService.updatebyte(bpa, totalBytes, true);

            return "CREATE";

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IOException e) {
            throw new NullPointerException();
        }
    }

    public String createOccupation(MultipartFile file, ParamNewOccupation paramNewOccupation, List<ErrorsFile> errorsFiles) {
        try {

            LinkOccupation linkOccupation = new LinkOccupation(paramNewOccupation.getName(), file.getSize(), paramNewOccupation.getDate());

            if(linkOccupationService.get(linkOccupation.getDate()).isPresent()) {
                return "EXIST DATE";
            }

            if(occupationService.isValidFile(file)) {
                return "FILE INVALID";
            }


            List<Occupation> occupationList = new ArrayList<>();
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));

            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                Occupation occupation = occupationService.create(line, lineNumber, linkOccupation, errorsFiles);
                occupationList.add(occupation);
                lineNumber++;
            }

            if(!errorsFiles.isEmpty()) {
                return "ERROR FILE";
            }

            linkOccupation.getOccupationList().addAll(occupationList);
            linkOccupationService.save(linkOccupation);

            return "CREATE";
        } catch (IllegalArgumentException | IOException e) {
            return "FAILURE";
        }
    }


    public String createCep(MultipartFile file, ParamNewCep paramNewCep, List<ErrorsFile> errorsFiles, StopWatch startTime) {
        try {

            LinkCep linkCep = new LinkCep(paramNewCep, file.getSize());

            if(linkCepService.exists(linkCep.getDate())) {
                return "EXIST DATE";
            }

            System.out.println("Viu se a data existe " + startTime.getTime() + " milissegundos: " + startTime.getTime()/1000);

            if(!cepService.isValidFile(file)) {
                return "FILE INVALID";
            }

            System.out.println("Verificou se o arq é valido " + startTime.getTime() + " milissegundos: " + startTime.getTime()/1000);

            List<Cep> cepList = new ArrayList<>();
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));

            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                Cep cep = cepService.create(line, lineNumber, linkCep, errorsFiles);
                cepList.add(cep);

                lineNumber++;
            }

            System.out.println("Leu todo o arq " + startTime.getTime() + " milissegundos: " + startTime.getTime()/1000);

            if(!errorsFiles.isEmpty()) {
                return "ERROR FILE";
            }

            linkCep.getCepList().addAll(cepList);
            linkCepService.save(linkCep);

            System.out.println("Salvo link e todos ceps " + startTime.getTime() + " milissegundos: " + startTime.getTime()/1000);

            return "CREATE";
        } catch (IllegalArgumentException | IOException e) {
            return "FAILURE";
        }
    }

    public String createProcedure(MultipartFile file, List<ErrorsFile> errorsFiles, ParamNewProcedure paramNewProcedure) {
        try {

            LinkProcedure linkProcedure = new LinkProcedure(paramNewProcedure.getName(), file.getSize(), paramNewProcedure.getDate());

            if(linkProcedureService.exists(linkProcedure.getDate())) {
                return "EXIST DATE";
            }

            if(!procedureService.isValidFile(file)) {
                return "FILE INVALID";
            }


            List<Procedure> procedureList = new ArrayList<>();
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.ISO_8859_1));

            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                Procedure procedure = procedureService.create(line, lineNumber, errorsFiles, linkProcedure);
                procedureList.add(procedure);;

                lineNumber++;
            }

            if(!errorsFiles.isEmpty()) {
                return "ERROR FILE";
            }

            linkProcedure.getProcedureList().addAll(procedureList);
            linkProcedureService.save(linkProcedure);

            return "CREATE";
        } catch (IOException e) {
            return "FAILURE";
        }
    }

    public String createFpo(MultipartFile file, ParamNewFpo paramNewFpo, List<ErrorsFile> errorsFileList) throws IllegalArgumentException {
        try {

            LinkFpo linkFpo = new LinkFpo(paramNewFpo, file.getSize());
            if(linkFpoService.exists(linkFpo.getDate())) {
                return "EXIST DATE";
            }

            if(!fpoService.isValidFile(file)) {
                return "FILE INVALID";
            }

            List<Fpo> fpoList = new ArrayList<>();
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));

            String line;
            int lineNumber = 2;
            while ((line = br.readLine()) != null) {
                try {
                    String pa = line.substring(2, 11);
                    if (pa.matches("\\d+")) {
                        Fpo fpo = fpoService.create(linkFpo, pa, line, lineNumber, errorsFileList);
                        fpoList.add(fpo);
                    }
                } catch (StringIndexOutOfBoundsException ignore) {
                }
                lineNumber++;
            }

            if(!errorsFileList.isEmpty()) {
                return "ERROR FILE";
            }

            linkFpo.getFpoList().addAll(fpoList);
            linkFpoService.save(linkFpo);

            return "CREATE";

        } catch (IOException e) {
            throw new NullPointerException();
        }
    }

    @Transactional
    public String createProfessionals(MultipartFile file, User user, ParamNewProfessionals paramNewProfessionals) {
        try {
            LinkProfessionals linkProfessionals = new LinkProfessionals(paramNewProfessionals, file.getSize(), user);

            if(linkProfessionalsService.exist(linkProfessionals.getDate())) {
                return "EXIST DATE";
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(file.getInputStream());
            Element root = document.getDocumentElement();
            NodeList profissionalElements = root.getElementsByTagName("DADOS_PROFISSIONAIS");

            List<ProfessionalComplete> professionalCompleteList = new ArrayList<>();

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
                    linkProfessionals,
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
            }

            EncryptionService.encrypt(professionalCompleteList);

            long totalBytes = storageService.quantityBytes(professionalCompleteList);

            if(user.getStorageFree() < totalBytes) {
                return "NOT STORAGE";
            }

            linkProfessionals.getProfessionalCompleteList().addAll(professionalCompleteList);
            linkProfessionalsService.save(linkProfessionals);

            userService.updateStorageAndSave(user, totalBytes, "sub");

            return "CREATE";
        } catch (IllegalArgumentException | ParserConfigurationException | IOException | SAXException e) {
            return "ERROR";
        }
    }

}
