package br.com.bpadash.services.bpa;

import br.com.bpadash.dto.bpa.BpaiDTO;
import br.com.bpadash.dto.sigtap.ErrorOccupationDTO;
import br.com.bpadash.dto.sigtap.ErrorPaDTO;
import br.com.bpadash.dto.sigtap.ErrorQtMaxDTODTO;
import br.com.bpadash.errorValidation.ErrorValidationDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.enumModel.ZoneTime;
import br.com.bpadash.model.sigtap.*;
import br.com.bpadash.model.bpa.Address;
import br.com.bpadash.model.user.AddressUser;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.bpa.ParamUpdateBpai;
import br.com.bpadash.params.bpa.ParamUpdateErrorsBpa;
import br.com.bpadash.repository.bpa.BpaiRepository;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.sigtap.*;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BpaiService {

    @Autowired
    private CepService cepService;
    @Autowired
    private LinkCepService linkCepService;
    @Autowired
    private ProcedureService procedureService;
    @Autowired
    private LinkProcedureService linkProcedureService;
    @Autowired
    private BpaiRepository bpaiRepository;


    public Bpai create(String line, int lineNumber, Bpa bpa, User user, List<ErrorsFile> errorsFiles) {
        List<ErrorValidationDTO> errors = new ArrayList<>();

        if (line.length() < 247) {
            errors.add(errorValidation("LINHA","A linha Não contém pelo menos 247 caracteres."));
        }

        String ident = line.substring(0, 2);
        String cnes = line.substring(2, 9);
        if(user.getBpaiValidation().isCnes()) {
            if(!cnes.matches("\\d+")) {
                errors.add(errorValidation("CNE","O código CNES deverá ser preenchido apenas com números. Adicionar zeros à esquerda."));
            }
        }

        String cmp = line.substring(9, 15);
        if(user.getBpaiValidation().isCmp()) {
            if(!cmp.matches("\\d+")) {
                // TODO validar o formato AAAAMM.
                errors.add(errorValidation("CMP", "O campo deverá ser preenchido apenas com números formato AAAAMM."));
            }
        }

        String cnsmed = line.substring(15, 30);
        if(user.getBpaiValidation().isCnsmed()) {
            if(!cnsmed.matches("\\d+")) {
                errors.add(errorValidation("CBO", "Código conforme a Classificação Brasileira de Ocupações (CBO)."));
            }
        }

        String cbo = line.substring(30, 36);
        if(user.getBpaiValidation().isCbo()) {
            if(!cbo.matches("\\d+")) {
                errors.add(errorValidation("CBO", "Código conforme a Classificação Brasileira de Ocupações (CBO)."));
            }
        }

        String dtaten = line.substring(36, 44);  // 6
        String flh = line.substring(44, 47);
        if(user.getBpaiValidation().isFlh()) {
            if(!flh.matches("\\d+")) {
                errors.add(errorValidation("FLH", "Número da folha do BPA. Domínio [001..999]. Adicionar zeros à esquerda de um inteiro."));
            }
        }

        String seq = line.substring(47, 49);
        if(user.getBpaiValidation().isSeq()) {
            if(!seq.matches("\\d+")) {
                errors.add(errorValidation("SEQ", "Número sequencial da linha dentro da folha do BPA. Domínio [01..20]. Adicionar zeros à esquerda de um inteiro."));
            }
        }

        String pa = line.substring(49, 59);
        if(user.getBpaiValidation().isPa()) {
            if(!pa.matches("\\d+")) {
                errors.add(errorValidation("PA", "O campo deverá ser preenchido apenas com números. Adicionar zeros à esquerda."));
            }
        }

        String cnspac = line.substring(59, 74);
        if(user.getBpaiValidation().isCnspac()) {
            if(!pa.matches("\\d+")) {
                errors.add(errorValidation("CNSPAC", "O campo deverá ser preenchido apenas com números."));
            }
        }

        String sexo = line.substring(74, 75);
        if(user.getBpaiValidation().isSexo()) {
            List<String> sexos = new ArrayList<>(Arrays.asList("M", "F"));
            if(!sexos.contains(sexo)) {
                errors.add(errorValidation("SEXO", "Permitido apenas M - Masculino, F - Feminino."));
            }
        }

        String ibge = line.substring(75, 81);
        if(user.getBpaiValidation().isIbge()) {
            if(!ibge.matches("\\d+")) {
                errors.add(errorValidation("IBGE", "O campo deverá ser preenchido apenas com números."));
            }
        }

        String cid = line.substring(81, 85);

        String idade = line.substring(85, 88);
        if(user.getBpaiValidation().isIdade()) {
            if (!idade.matches("\\d+") || Integer.parseInt(idade) > 130) {
                errors.add(errorValidation("IDADE" , "Idade (0 a 130 anos). O campo deverá ser preenchido apenas com números. Adicionar zeros à esquerda."));
            }
        }

        String qt = line.substring(88, 94);
        if(user.getBpaiValidation().isCmp()) {
            if(!cmp.matches("\\d+")) {
                errors.add(errorValidation("QT", "O campo deverá ser preenchido apenas com números. Adicionar zeros à esquerda de um inteiro."));
            }
        }

        String caten = line.substring(94, 96);
        if(user.getBpaiValidation().isCaten()) {
            if(!caten.matches("\\d+")) {
                errors.add(errorValidation("CATEN", "O campo deverá ser preenchido apenas com números. Adicionar zeros à esquerda de um inteiro."));
            }
        }

        String naut = line.substring(96, 109);
        if(user.getBpaiValidation().isNaut()) {
            if(!cmp.matches("\\d+")) {
                errors.add(errorValidation("NAUT", "O campo deverá ser preenchido apenas com números."));
            }
        }

        String org = line.substring(109, 112);
        if(user.getBpaiValidation().isOrg()) {
            if(!org.equals("BPA")) {
                errors.add(errorValidation("ORG", "O campo deverá ser preenchido apenas com (\"BPA\", \"PNI\", \"SIE\", \"SIB\", \"MIN\", \"PAC\", \"SCL\" ou \"EXT\")"));
            }
        }

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

        if(!errors.isEmpty()) {
            errorsFiles.add(new ErrorsFile(String.valueOf(lineNumber), errors));

            return null;
        }

        Bpai bpai = new Bpai(
                bpa,
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

    public Optional<Bpai> get(Long id) {

        return bpaiRepository.findById(id);
    }

    public Page<BpaiDTO> get(Bpa bpa , Pageable pageable) {
        Page<Bpai> page = bpaiRepository.findByBpa(bpa, pageable);

        List<Bpai> bpaiList = page.getContent();

        EncryptionService.decryptBpai(bpaiList, true);

        List<BpaiDTO> bpaiDTOList = bpaiList.stream()
                .map(bpai -> new BpaiDTO(bpai, bpa.getIdentifier()))
                .collect(Collectors.toList());

        return new PageImpl<>(bpaiDTOList, pageable, page.getTotalElements());
    }

    public Bpai getLast(Bpa bpa) {
        return bpaiRepository.findTopByBpaOrderByFlhDescSeqDesc(bpa);
    }

    public Bpai editAndSave(Bpai bpai, ParamUpdateBpai paramUpdateBpai) {
        bpai.setCnes(paramUpdateBpai.getCnes());
        bpai.setCmp(paramUpdateBpai.getCmp());
        bpai.setCnsmed(paramUpdateBpai.getCnsmed());
        bpai.setCbo(paramUpdateBpai.getCbo());
        bpai.setDtaten(paramUpdateBpai.getDtaten());
        bpai.setFlh(paramUpdateBpai.getFlh());
        bpai.setSeq(paramUpdateBpai.getSeq());
        bpai.setPa(paramUpdateBpai.getPa());
        bpai.setCnspac(paramUpdateBpai.getCnspac());
        bpai.setSexo(paramUpdateBpai.getSexo());
        bpai.setIbge(paramUpdateBpai.getIbge());
        bpai.setCid(paramUpdateBpai.getCid());
        bpai.setIdade(paramUpdateBpai.getIdade());
        bpai.setQt(paramUpdateBpai.getQt());
        bpai.setCaten(paramUpdateBpai.getCaten());
        bpai.setNaut(paramUpdateBpai.getNaut());
        bpai.setOrg(paramUpdateBpai.getOrg());
        bpai.setNmpac(paramUpdateBpai.getNmpac());
        bpai.setDtnasc(paramUpdateBpai.getDtnasc());
        bpai.setRaca(paramUpdateBpai.getRaca());
        bpai.setEtnia(paramUpdateBpai.getEtnia());
        bpai.setNac(paramUpdateBpai.getNac());
        bpai.setSrv(paramUpdateBpai.getSrv());
        bpai.setClf(paramUpdateBpai.getClf());
        bpai.setEquipeSeq(paramUpdateBpai.getEquipeSeq());
        bpai.setEquipeArea(paramUpdateBpai.getEquipeArea());
        bpai.setCnpj(paramUpdateBpai.getCnpj());
        bpai.setCepPcnte(paramUpdateBpai.getCepPcnte());
        bpai.setLogradPcnte(paramUpdateBpai.getLogradPcnte());
        bpai.setEndPcnte(paramUpdateBpai.getEndPcnte());
        bpai.setComplPcnte(paramUpdateBpai.getComplPcnte());
        bpai.setNumPcnte(paramUpdateBpai.getNumPcnte());
        bpai.setBairroPcnte(paramUpdateBpai.getBairroPcnte());
        bpai.setDdtelPcnte(paramUpdateBpai.getDdtelPcnte());
        bpai.setEmailPcnte(paramUpdateBpai.getEmailPcnte());
        bpai.setIne(paramUpdateBpai.getIne());
        bpai.setFim(paramUpdateBpai.getFim());

        EncryptionService.encryptBpai(new ArrayList<>(List.of(bpai)));

        return this.save(bpai);
    }

    public int editAndSave(Bpai bpai, ParamUpdateErrorsBpa paramBpa, Bpa bpa, User user) {
        int count = 0;
        String key = paramBpa.getKey();

        switch (key) {
            case "pa" -> this.editPa(paramBpa.getPa(), bpai, bpa);
            case "birthDate" -> this.editDtNasc(paramBpa.getDateBpa(), paramBpa.getIds(), bpai);
            case "cep" -> this.editCep(paramBpa.getCep(), paramBpa.getIds(), bpai, user);
            case "cepBlank" -> this.editCepBlank(paramBpa.getIds(), user);
            case "qtService" -> this.editQtService(paramBpa.getQtService(), paramBpa.getIds(), bpai, user);
            case "dateBpaInvalid" -> this.editDateBpa(paramBpa.getDateBpaInvalid(), paramBpa.getIds(), bpai);
            case "race" -> this.editRace(paramBpa.getRace(), paramBpa.getIds(), bpai, user);
            case "cnsmedProfessional" -> this.editCnsmed(paramBpa.getCnsmed(), bpai, bpa);
            case "sexProcedure" -> this.editSex(paramBpa.getIds(), user);
            case "cbo" -> this.editCbo(paramBpa.getCbo(), bpai, bpa);
            case "ageMaxMin" -> this.editAge(paramBpa.getAge(), paramBpa.getIds(), bpai);
        }


        return count;
    }

    private void editAge(Integer age, List<Long> ids, Bpai bpai) {
        if(ids != null) {
            List<Bpai> bpaiList = bpaiRepository.findByIdIn(ids);

            EncryptionService.decryptBpaiDtNasc(bpaiList);
            LocalDate date = LocalDate.now(ZoneId.of(ZoneTime.BR.getBr()));

            bpaiList.forEach( bpaix -> {
                String dateNasc = bpaix.getDtnasc();

                try {
                    int year = Integer.parseInt(dateNasc.substring(0, 4));

                    if(year > 1900 && year < date.getYear()) {
                        int month = Integer.parseInt(dateNasc.substring(4, 6));
                        int day = Integer.parseInt(dateNasc.substring(6, 8));

                        int yearNasc = date.getYear() - year;

                        bpaix.setIdade(EncryptionService.encrypt(String.valueOf(yearNasc + month + day)));
                    }
                } catch (Exception ignored) {

                }
            });

            this.save(bpaiList);
        } else {
            bpai.setIdade(EncryptionService.encrypt(String.valueOf(age)));
            this.save(bpai);
        }
    }

    private void editCbo(String cboParam , Bpai bpai, Bpa bpa) {
        String cbo = cboParam.split("-")[0];
        String updateAll = cboParam.split("-")[1];

        if(updateAll.equals("1")) {
            List<Bpai> bpaiList = bpaiRepository.findByCboAndBpa(cboParam.split("-")[2], bpa);

            bpaiList.forEach( bpaix -> {
                bpaix.setCbo(cbo);
            });

            this.save(bpaiList);
        } else {
            bpai.setCbo(cbo);
            this.save(bpai);
        }
    }

    private void editSex(List<Long> ids, User user) {
        DatesSigtap datesSigtap = user.getDatesSigtap();
        List<Bpai> bpaiList = bpaiRepository.findByIdIn(ids);

        Optional<LinkProcedure> linkProcedureOptinal;
        if(datesSigtap.isDateCepAuto()) {
            linkProcedureOptinal = linkProcedureService.get();
        } else {
            linkProcedureOptinal = linkProcedureService.get(datesSigtap.getDateCep());
        }

        if(linkProcedureOptinal.isPresent()) {
            List<String> sexos = new ArrayList<>(Arrays.asList("N", "I"));

            LinkProcedure linkProcedure = linkProcedureOptinal.get();

            for (Bpai bpaix: bpaiList) {

                String pa = bpaix.getPa();

                Optional<Procedure> procedureOptional = linkProcedure.getProcedureList().stream().filter(procedure -> procedure.getCodProcedimento().equals(pa)).findFirst();


                if (procedureOptional.isPresent()) {
                    Procedure procedure = procedureOptional.get();

                    String sexo = procedure.getTpSexo();

                    if(!sexos.contains(sexo)) {
                        if(!sexo.equals(bpaix.getSexo())) {
                            bpaix.setSexo(EncryptionService.encrypt(sexo));
                        }
                    }

                }
            }

            this.save(bpaiList);
        }
    }

    private void editCnsmed(String cnsmedParam, Bpai bpai, Bpa bpa) {
        String cnsmed = cnsmedParam.split("-")[0];
        String updateAll = cnsmedParam.split("-")[1];

        if(updateAll.equals("1")) {
            List<Bpai> bpaiList = bpaiRepository.findByCnsmedAndBpa(cnsmedParam.split("-")[2], bpa);

            bpaiList.forEach( bpaix -> {
                bpaix.setCnsmed(cnsmed);
            });

            this.save(bpaiList);
        } else {
            bpai.setCnsmed(cnsmed);
            this.save(bpai);
        }
    }

    //TODO terminar esse método --- falta concluir
    private void editRace(String race, List<Long> ids, Bpai bpai, User user) {
        if(ids != null) {
            List<String> racesValids = new ArrayList<>(Arrays.asList("01", "02", "03", "04", "05"));
            List<Bpai> bpaiList = bpaiRepository.findByIdIn(ids);

            List<Bpa> bpaList = user.getBpas();

            Bpa bpa = this.get(bpaiList.get(0).getId()).get().getBpa();
            bpaList.remove(bpa);

            if(!bpaList.isEmpty()) {
                System.out.println(LocalDateTime.now());
                EncryptionService.decryptCnsPac(bpaiList);

                bpaiList.forEach( bpaix -> {

                    String key = EncryptionService.hashString(bpaix.getCnspac());

//                    Optional<Bpai> bpaiOptional = bpaiRepository.findFristByCnspacHas(key); //bpaList
//
//                    bpaiOptional.ifPresent(value -> System.out.println(value.getId()));

//                    bpaiOptional.ifPresent(value -> {
//                        String newRace = EncryptionService.decrypt(value.getRaca());
//
//                        if (racesValids.contains(newRace)) {
//                            bpaix.setRaca(value.getRaca());
//                        }
//                    });
                });
                System.out.println(LocalDateTime.now());

                EncryptionService.encryptCnsPac(bpaiList);
                System.out.println(LocalDateTime.now());

//                this.save(bpaiList);
            }
        } else {
            bpai.setRaca(EncryptionService.encrypt(race));
            this.save(bpai);
        }
    }

    private void editDateBpa(String dateBpaInvalid, List<Long> ids , Bpai bpai) {
        String dateBpa = dateBpaInvalid.replaceAll("-", "");

        if(ids != null) {
            List<Bpai> bpaiList = bpaiRepository.findByIdIn(ids);

            bpaiList.forEach( bpaix -> {
                bpaix.setDtaten(dateBpa);
            });

            this.save(bpaiList);
        } else {
            bpai.setDtaten(dateBpa);
            this.save(bpai);
        }
    }

    private void editQtService(List<Integer> qtService, List<Long> ids, Bpai bpai, User user) {
        if(ids != null) {
            DatesSigtap datesSigtap = user.getDatesSigtap();

            Optional<LinkProcedure> linkProcedure;
            if(datesSigtap.isDateCepAuto()) {
                linkProcedure = linkProcedureService.get();
            } else {
                linkProcedure = linkProcedureService.get(datesSigtap.getDateCep());
            }

            if(linkProcedure.isPresent()) {
                List<Procedure> procedureList = procedureService.get(linkProcedure.get());
                List<Bpai> bpaiList = bpaiRepository.findByIdIn(ids);

                bpaiList.forEach( bpaix -> {
                    Optional<Procedure> procedureOptional = procedureList.stream().filter(procedure -> procedure.getCodProcedimento().equals(bpaix.getPa())).findFirst();

                    if (procedureOptional.isPresent()) {
                        Procedure procedure = procedureOptional.get();

                        int qtMax = Integer.parseInt(procedure.getQtMaximaExecucao());

                        bpaix.setQt(String.valueOf(qtMax));
                    }
                });

                this.save(bpaiList);
            }

        } else {
            bpai.setQt(String.valueOf(qtService.get(0)));
            this.save(bpai);
        }
    }

    private void editCep(String cep , List<Long> cepsIds, Bpai bpai, User user) {
        if(cepsIds != null) {
            DatesSigtap datesSigtap = user.getDatesSigtap();

            Optional<LinkCep> linkCepOptional;
            if(datesSigtap.isDateCepAuto()) {
                linkCepOptional = linkCepService.get();
            } else {
                linkCepOptional = linkCepService.get(datesSigtap.getDateCep());
            }

            if(linkCepOptional.isPresent()) {
                List<String> cepList = cepService.getAllSort(linkCepOptional.get());

                // Ordena a lista
                List<Bpai> bpaiList = bpaiRepository.findByIdIn(cepsIds);

                EncryptionService.decryptBpaiCep(bpaiList);

                for (Bpai cepBpai : bpaiList) {
                    String targetNumber = cepBpai.getCepPcnte();

                    if(!targetNumber.isBlank()) {
                        // Realiza a busca binária
                        int index = Collections.binarySearch(cepList, targetNumber, Comparator.comparingInt(s -> Math.abs(Integer.parseInt(s) - Integer.parseInt(targetNumber))));

                        // Verifica se o índice encontrado é válido
                        String closestNumber;
                        if (index >= 0) {
                            closestNumber = cepList.get(index);
                        } else {
                            closestNumber = cepList.get(0);
                        }

                        cepBpai.setCepPcnte(EncryptionService.encrypt(closestNumber));
                    }
                }

                this.save(bpaiList);
            }

        } else {
            bpai.setCepPcnte(EncryptionService.encrypt(cep));

            this.save(bpai);
        }
    }

    private void editCepBlank(List<Long> cepsIds, User user) {
        if(cepsIds != null) {
            List<Bpai> bpaiList = bpaiRepository.findByIdIn(cepsIds);

            AddressUser address = user.getAddressUser();

            EncryptionService.decryptAddressUser(address);

            for (Bpai bpai: bpaiList) {
                bpai.setIbge(address.getIbge());
                bpai.setCepPcnte(address.getCep());
                bpai.setLogradPcnte(address.getCodLograud());
                bpai.setComplPcnte(address.getComplemento());
                bpai.setEndPcnte(address.getLogradouro());
                bpai.setBairroPcnte(address.getBairro());
            }

            this.save(bpaiList);
        }
    }


    private void editDtNasc(String dtNasc, List<Long> ids, Bpai bpai) {
        if(ids != null) {
            List<Bpai> bpaiList = bpaiRepository.findByIdIn(ids);
            int yearCurrent = LocalDate.now(ZoneId.of(ZoneTime.BR.getBr())).getYear();

            EncryptionService.decryptBpaiIdadeAndDtnasc(bpaiList);

            bpaiList.forEach( bpaix -> {
                int age = Integer.parseInt(bpaix.getIdade());

                if(age < 131) {
                    String year = String.valueOf(yearCurrent - age);
                    String month = bpaix.getDtnasc().substring(4,6);
                    String day = bpaix.getDtnasc().substring(6,8);

                    String birthDate = year + month + day;

                    bpaix.setDtnasc(birthDate);
                }
            });

            EncryptionService.encryptBpaiIdadeAndDtnasc(bpaiList);

            this.save(bpaiList);

        } else {
            bpai.setDtnasc(EncryptionService.encrypt(dtNasc));
            this.save(bpai);
        }
    }

    private void editPa(String pa, Bpai bpai, Bpa bpa) {
        String newPa = pa.split("-")[0];
        String updateAll = pa.split("-")[1];

        if(updateAll.equals("1")) {
            List<Bpai> bpaiList = bpaiRepository.findByPaAndBpa(pa.split("-")[2], bpa);

            bpaiList.forEach( bpaix -> {
                bpaix.setPa(newPa);
            });

            this.save(bpaiList);

        } else {
            bpai.setPa(newPa);
            this.save(bpai);
        }
    }

    @Transactional
    public void delete(List<Bpai> bpaiList) {
        bpaiRepository.deleteAll(bpaiList);
    }

    @Transactional
    public void delete(Bpai bpai) {
        bpaiRepository.delete(bpai);
    }

    public void delete(Bpa bpa) {
        bpaiRepository.deleteByBpa(bpa);
    }

    public void deleteById(List<Long> bpai) {
        bpaiRepository.deleteAllById(bpai);
    }

    public Bpai save(Bpai bpai) {
        return bpaiRepository.save(bpai);
    }

    public List<Bpai> save(List<Bpai> bpais) {
        return bpaiRepository.saveAll(bpais);
    }

    @Transactional
    public List<Bpai> save(List<Bpai> bpaiList, StopWatch startTime) {
        return bpaiRepository.saveAll(bpaiList);
    }

    public Long sizeByte(List<Long> listIds) {
        return bpaiRepository.calculateSizeById(listIds);
    }

    public List<Bpai> get(Bpa bpa) {
        return bpaiRepository.findByBpa(bpa);
    }

    private ErrorValidationDTO errorValidation(String field, String message) {
        return new ErrorValidationDTO(field, message);
    }

    public List<ErrorPaDTO> verifyErrorsPa(List<Fpo> fpoList, Set<String> procedurePaSet, Set<String> occupationPaSet, List<Bpai> bpaiListDB) {
        List<ErrorPaDTO> errorsPa = new ArrayList<>();

        bpaiListDB.forEach( bpai -> {

            String pa = bpai.getPa().substring(0, 9);
            String flh = bpai.getFlh();
            String seq = bpai.getSeq();

            boolean paExists = fpoList.stream().anyMatch(fpo -> fpo.getPa().equals(pa));
            ErrorPaDTO errorPaDTO = new ErrorPaDTO(bpai.getId(), flh, seq, "", bpai.getPa(), "bpai");

            if (!paExists) {
                errorPaDTO.setMsg("FPO ");
            }

            if(!occupationPaSet.contains(pa)) {
                errorPaDTO.setMsg(errorPaDTO.getMsg() + "OCUPAÇÃO");
            }

            if(!procedurePaSet.contains(bpai.getPa())) {
                errorPaDTO.setMsg(errorPaDTO.getMsg() + " PROCEDIMENTO");
            }

            if(!errorPaDTO.getMsg().equals("")) {
                errorsPa.add(errorPaDTO);
            }
        });


        return errorsPa;
    }

    public List<ErrorOccupationDTO> verifyErrorsOccupation(Set<String> occupationPa, Set<String> occupationCBO , List<Bpai> bpaiListDB) {
        List<ErrorOccupationDTO> errors = new ArrayList<>();

        for (Bpai bpai: bpaiListDB) {

            String pa = bpai.getPa().substring(0, 9);
            String cbo = bpai.getCbo();
            String flh = bpai.getFlh();
            String seq = bpai.getSeq();

            if (occupationPa.contains(pa)) {
                boolean exitsCBO = occupationCBO.stream().anyMatch(cboString -> cboString.contains(cbo));

                if(!exitsCBO) {
                    errors.add(new ErrorOccupationDTO(bpai.getId(), flh, seq, cbo, bpai.getPa(), "NOT EXIST CBO IN OCCUPATION BPAI"));
                }
            }
        }

        return errors;
    }

    public List<ErrorQtMaxDTODTO> verifyErrorsQtServices(List<Procedure> procedureList , List<Bpai> bpaiListDB) {
        List<ErrorQtMaxDTODTO> errors = new ArrayList<>();

        for (Bpai bpai: bpaiListDB) {

            String pa = bpai.getPa();
            String flh = bpai.getFlh();
            String seq = bpai.getSeq();

            Optional<Procedure> procedureOptional = procedureList.stream().filter(procedure -> procedure.getCodProcedimento().equals(pa)).findFirst();

            if (procedureOptional.isPresent()) {
                Procedure procedure = procedureOptional.get();

                int qtMax = Integer.parseInt(procedure.getQtMaximaExecucao());
                int qt = Integer.parseInt(bpai.getQt());

                if(qt > qtMax) {
                    errors.add(new ErrorQtMaxDTODTO(bpai.getId(), flh, seq, "MAXIMUM QUANTITY EXCEEDED", EncryptionService.decrypt(bpai.getNmpac()), qt, qtMax, pa));
                }
            }
        }

        return errors;
    }


}

