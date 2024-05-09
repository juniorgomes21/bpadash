package br.com.bpadash.services.bpa;

import br.com.bpadash.dto.bpa.BpaiDTO;
import br.com.bpadash.dto.sigtap.ErrorOccupationDTO;
import br.com.bpadash.dto.sigtap.ErrorPaDTO;
import br.com.bpadash.dto.sigtap.ErrorQtMaxDTODTO;
import br.com.bpadash.errorValidation.ErrorValidationDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.bpa.ManagerBpa;
import br.com.bpadash.model.enumModel.ZoneTime;
import br.com.bpadash.model.sigtap.*;
import br.com.bpadash.model.user.AddressUser;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.bpa.ParamFilterCriteria;
import br.com.bpadash.params.bpa.ParamUpdateBpai;
import br.com.bpadash.params.bpa.ParamUpdateErrorsBpa;
import br.com.bpadash.repository.bpa.BpaiRepository;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import br.com.bpadash.services.sigtap.*;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BpaiService {

    @Autowired
    private BpaiRepository bpaiRepository;
    @Autowired
    private CepService cepService;
    @Autowired
    private LinkCepService linkCepService;
    @Autowired
    private ProcedureService procedureService;
    @Autowired
    private LinkProcedureService linkProcedureService;
    @Autowired
    private EntityManager entityManager;


    public Bpai create(String line, int lineNumber, Bpa bpa, User user, List<ErrorsFile> errorsFiles) {

        List<ErrorValidationDTO> errors = new ArrayList<>();

        if (line.length() < 247) {
            errors.add(errorValidation("LINHA","A linha Não contém pelo menos 247 caracteres."));

            errorsFiles.add(new ErrorsFile(String.valueOf(lineNumber), errors));

            return null;
        }

        String ident = line.substring(0, 2);
        String cnes = line.substring(2, 9);
        String cmp = line.substring(9, 15);
        String cnsmed = line.substring(15, 30);
        String cbo = line.substring(30, 36);
        String dtaten = line.substring(36, 44);
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
        String nmpac = line.substring(112, 142);
        String dtnasc = line.substring(142, 150);
        String raca = line.substring(150, 152);
        String etnia = line.substring(152, 156);
        String nac = line.substring(156, 159);
        String srv = line.substring(159, 162);
        String clf = line.substring(162, 165);
        String equipe_seq = line.substring(165, 173);
        String equipe_area = line.substring(173, 177);
        String cnpj = line.substring(177, 191);
        String cep_pcnte = line.substring(191, 199);
        String lograd_pcnte = line.substring(199, 202);
        String end_pcnte = line.substring(202, 232);
        String compl_pcnte = line.substring(232, 242);

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

        EnCryptionAESService.decryptBpai(bpaiList, true);

        List<BpaiDTO> bpaiDTOList = bpaiList.stream()
                .map(bpai -> new BpaiDTO(bpai, bpa.getIdentifier()))
                .collect(Collectors.toList());

        return new PageImpl<>(bpaiDTOList, pageable, page.getTotalElements());
    }


    public Page<BpaiDTO> getFiltered(Bpa bpa, ParamFilterCriteria paramFilterCriteria, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bpai> query = builder.createQuery(Bpai.class);
        Root<Bpai> root = query.from(Bpai.class);

        List<Predicate> predicates = new ArrayList<>();

        // Construir predicados com base nos parâmetros fornecidos
        if (paramFilterCriteria != null) {
            if (paramFilterCriteria.getPa() != null && !paramFilterCriteria.getPa().isEmpty()) {
                predicates.add(builder.equal(root.get("pa"), paramFilterCriteria.getPa()));
            }
            if (paramFilterCriteria.getCnes() != null && !paramFilterCriteria.getCnes().isEmpty()) {
                predicates.add(builder.equal(root.get("cnes"), paramFilterCriteria.getCnes()));
            }
            if (paramFilterCriteria.getCnsmed() != null && !paramFilterCriteria.getCnsmed().isEmpty()) {
                predicates.add(builder.equal(root.get("cnsmed"), EnCryptionAESService.encrypt(paramFilterCriteria.getCnsmed())));
            }
            if (paramFilterCriteria.getCbo() != null && !paramFilterCriteria.getCbo().isEmpty()) {
                predicates.add(builder.equal(root.get("cbo"), paramFilterCriteria.getCbo()));
            }
            if (paramFilterCriteria.getSex() != null && !paramFilterCriteria.getSex().isEmpty()) {
                predicates.add(builder.equal(root.get("sexo"), EnCryptionAESService.encrypt(paramFilterCriteria.getSex())));
            }
            if (paramFilterCriteria.getIbge() != null && !paramFilterCriteria.getIbge().isEmpty()) {
                predicates.add(builder.equal(root.get("ibge"), EnCryptionAESService.encrypt(paramFilterCriteria.getIbge())));
            }
            if (paramFilterCriteria.getRace() != null && !paramFilterCriteria.getRace().equals("00")) {
                predicates.add(builder.equal(root.get("raca"), EnCryptionAESService.encrypt(paramFilterCriteria.getRace())));
            }
            // Adicione outras condições conforme necessário para os outros parâmetros
        }

        // Aplicar os predicados à consulta
        query.where(predicates.toArray(new Predicate[0]));

        // Aplicar a paginação
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int firstResult = pageNumber * pageSize;

        query.orderBy(builder.asc(root.get("id"))); // Substitua "id" pelo nome do campo que deseja ordenar
        List<Bpai> bpaiList = entityManager.createQuery(query)
                .setFirstResult(firstResult)
                .setMaxResults(pageSize)
                .getResultList();

        EnCryptionAESService.decryptBpai(bpaiList, true);

        // Mapear para DTOs
        List<BpaiDTO> bpaiDTOList = bpaiList.stream()
                .map(bpai -> new BpaiDTO(bpai, bpa.getIdentifier()))
                .collect(Collectors.toList());

        long totalElements = getTotalElements(builder, paramFilterCriteria);

        // Retornar como uma página paginada
        return new PageImpl<>(bpaiDTOList, pageable, totalElements);
    }

    private long getTotalElements(CriteriaBuilder builder, ParamFilterCriteria paramFilterCriteria) {
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<Bpai> root = countQuery.from(Bpai.class);
        countQuery.select(builder.count(root));

        List<Predicate> predicates = new ArrayList<>();

        // Construir predicados com base nos parâmetros fornecidos
        if (paramFilterCriteria != null) {
            if (paramFilterCriteria.getPa() != null && !paramFilterCriteria.getPa().isEmpty()) {
                predicates.add(builder.equal(root.get("pa"), paramFilterCriteria.getPa()));
            }
            if (paramFilterCriteria.getCnes() != null && !paramFilterCriteria.getCnes().isEmpty()) {
                predicates.add(builder.equal(root.get("cnes"), paramFilterCriteria.getCnes()));
            }
            if (paramFilterCriteria.getCnsmed() != null && !paramFilterCriteria.getCnsmed().isEmpty()) {
                predicates.add(builder.equal(root.get("cnsmed"), EnCryptionAESService.encrypt(paramFilterCriteria.getCnsmed())));
            }
            if (paramFilterCriteria.getCbo() != null && !paramFilterCriteria.getCbo().isEmpty()) {
                predicates.add(builder.equal(root.get("cbo"), paramFilterCriteria.getCbo()));
            }
            if (paramFilterCriteria.getSex() != null && !paramFilterCriteria.getSex().isEmpty()) {
                predicates.add(builder.equal(root.get("sexo"), EnCryptionAESService.encrypt(paramFilterCriteria.getSex())));
            }
            if (paramFilterCriteria.getIbge() != null && !paramFilterCriteria.getIbge().isEmpty()) {
                predicates.add(builder.equal(root.get("ibge"), EnCryptionAESService.encrypt(paramFilterCriteria.getIbge())));
            }
            if (paramFilterCriteria.getRace() != null && !paramFilterCriteria.getRace().equals("00")) {
                predicates.add(builder.equal(root.get("raca"), EnCryptionAESService.encrypt(paramFilterCriteria.getRace())));
            }
        }

        // Aplicar os predicados à consulta de contagem
        countQuery.where(predicates.toArray(new Predicate[0]));

        // Executar a consulta de contagem e retornar o resultado
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    public Bpai getLast(Bpa bpa) {
        return bpaiRepository.findTopByBpaOrderByFlhDescSeqDesc(bpa);
    }

    public Bpai editAndSave(Bpai bpai, ParamUpdateBpai paramUpdateBpai) {

        bpai.setPa(paramUpdateBpai.getPa());
        bpai.setSexo(paramUpdateBpai.getSexo());
        bpai.setRaca(paramUpdateBpai.getRaca());
        bpai.setIdade(paramUpdateBpai.getIdade());
        bpai.setCnes(paramUpdateBpai.getCnes());
        bpai.setCmp(paramUpdateBpai.getCmp());
        bpai.setCnsmed(paramUpdateBpai.getCnsmed());
        bpai.setCbo(paramUpdateBpai.getCbo());
        bpai.setDtaten(paramUpdateBpai.getDtaten());
        bpai.setFlh(paramUpdateBpai.getFlh());
        bpai.setSeq(paramUpdateBpai.getSeq());
        bpai.setCnspac(paramUpdateBpai.getCnspac());
        bpai.setIbge(paramUpdateBpai.getIbge());
        bpai.setCid(paramUpdateBpai.getCid());
        bpai.setQt(paramUpdateBpai.getQt());
        bpai.setCaten(paramUpdateBpai.getCaten());
        bpai.setNaut(paramUpdateBpai.getNaut());
        bpai.setOrg(paramUpdateBpai.getOrg());
        bpai.setNmpac(paramUpdateBpai.getNmpac());
        bpai.setDtnasc(paramUpdateBpai.getDtnasc());
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

        EnCryptionAESService.encryptBpaiInitial(new ArrayList<>(List.of(bpai)));

        return this.saveAndFlush(bpai);
    }

    public int editAndSave(Bpai bpai, ParamUpdateErrorsBpa paramBpa, Bpa bpa, User user) {
        int count = 0;
        String key = paramBpa.getKey();

        switch (key) {
            case "pa" -> count = this.editPa(paramBpa.getPa(), bpai, bpa, count);
            case "birthDate" -> count = this.editDtNasc(paramBpa.getDate(), paramBpa.getIds(), bpai, count);
            case "cep" -> count = this.editCep(paramBpa.getCep(), paramBpa.getIds(), bpai, user, count);
            case "cepBlank" -> count = this.editCepBlank(paramBpa.getIds(), user, count);
            case "qtService" -> count = this.editQtService(paramBpa.getQtService(), paramBpa.getIds(), bpai, user, count);
            case "dateBpaInvalid" -> count = this.editDateBpa(paramBpa.getDateBpaInvalid(), paramBpa.getIds(), bpai, count);
            case "race" -> count = this.editRace(paramBpa.getRace(), paramBpa.getIds(), bpai, user, count);
            case "cnsmedProfessional" -> count = this.editCnsmed(paramBpa.getCnsmed(), bpai, bpa, count);
            case "sexProcedure" -> count = this.editSex(paramBpa.getIds(), user, count);
            case "cbo" -> count = this.editCbo(paramBpa.getCbo(), bpai, bpa, count);
            case "ageMaxMin" -> count = this.editAge(paramBpa.getAge(), paramBpa.getIds(), bpai, count);
        }

        return count;
    }

    private int editAge(Integer age, List<Long> ids, Bpai bpai, int count) {
        if(ids != null) {
            List<Bpai> bpaiList = bpaiRepository.findByIdIn(ids);

            EnCryptionAESService.decryptBpaiDtNasc(bpaiList);
            LocalDate date = LocalDate.now(ZoneId.of(ZoneTime.BR.getBr()));

            bpaiList.forEach( bpaix -> {
                String dateNasc = bpaix.getDtnasc();

                try {
                    int year = Integer.parseInt(dateNasc.substring(0, 4));

                    if(year > 1900 && year < date.getYear()) {
                        int month = Integer.parseInt(dateNasc.substring(4, 6));
                        int day = Integer.parseInt(dateNasc.substring(6, 8));

                        int yearNasc = date.getYear() - year;

                        bpaix.setIdade(EnCryptionAESService.encrypt(String.valueOf(yearNasc + month + day)));
                    }
                } catch (Exception ignored) {

                }
            });

            count = this.saveAndFlush(bpaiList).size();
        } else {
            bpai.setIdade(EnCryptionAESService.encrypt(String.valueOf(age)));
            this.saveAndFlush(bpai);

            count = 1;
        }

        return count;
    }

    private int editCbo(String cboParam , Bpai bpai, Bpa bpa, int count) {
        String cbo = cboParam.split("-")[0];
        String updateAll = cboParam.split("-")[1];

        if(updateAll.equals("1")) {
            List<Bpai> bpaiList = bpaiRepository.findByCboAndBpa(cboParam.split("-")[2], bpa);

            bpaiList.forEach( bpaix -> {
                bpaix.setCbo(cbo);
            });

            count = this.save(bpaiList).size();
        } else {
            bpai.setCbo(cbo);
            this.save(bpai);

            count = 1;
        }

        return count;
    }

    private int editSex(List<Long> ids, User user, int count) {
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
                            bpaix.setSexo(EnCryptionAESService.encrypt(sexo));
                        }
                    }

                }
            }

            return this.saveAndFlush(bpaiList).size();
        }

        return count;
    }

    private int editCnsmed(String cnsmedParam, Bpai bpai, Bpa bpa, int count) {
        String cnsmed = EnCryptionAESService.encrypt(cnsmedParam.split("-")[0]);
        String updateAll = cnsmedParam.split("-")[1];

        if(updateAll.equals("1")) {
            List<Bpai> bpaiList = bpaiRepository.findByCnsmedAndBpa(EnCryptionAESService.encrypt(cnsmedParam.split("-")[2]), bpa);

            bpaiList.forEach( bpaix -> {
                bpaix.setCnsmed(cnsmed);
            });

            count = this.save(bpaiList).size();
        } else {
            bpai.setCnsmed(cnsmed);
            this.save(bpai);

            count = 1;
        }

        return count;
    }


    //TODO terminar esse método --- falta concluir
    private int editRace(String race, List<Long> ids, Bpai bpai, User user, int count) {
        if(ids != null) {
            List<String> racesValids = new ArrayList<>(Arrays.asList("01", "02", "03", "04", "05"));
            List<Bpai> bpaiList = bpaiRepository.findByIdIn(ids);

            List<Bpa> bpaList = user.getBpas();

            Bpa bpa = this.get(bpaiList.get(0).getId()).get().getBpa();
            bpaList.remove(bpa);

            if(!bpaList.isEmpty()) {
                System.out.println(LocalDateTime.now());
                EnCryptionAESService.decryptCnsPac(bpaiList);

                bpaiList.forEach( bpaix -> {

                    String key = EnCryptionAESService.hashString(bpaix.getCnspac());

//                    Optional<Bpai> bpaiOptional = bpaiRepository.findFristByCnspacHas(key); //bpaList
//
//                    bpaiOptional.ifPresent(value -> System.out.println(value.getId()));

//                    bpaiOptional.ifPresent(value -> {
//                        String newRace = EnCryptionAESService.decrypt(value.getRaca());
//
//                        if (racesValids.contains(newRace)) {
//                            bpaix.setRaca(value.getRaca());
//                        }
//                    });
                });
                System.out.println(LocalDateTime.now());

                EnCryptionAESService.encryptCnsPac(bpaiList);
                System.out.println(LocalDateTime.now());

//                count = this.save(bpaiList).size();
            }
        } else {
            bpai.setRaca(EnCryptionAESService.encrypt(race));
            this.saveAndFlush(bpai);

            count = 1;
        }

        return count;
    }

    private int editDateBpa(String dateBpaInvalid, List<Long> ids , Bpai bpai, int count) {
        String dateBpa = dateBpaInvalid.replaceAll("-", "");

        if(ids != null) {
            List<Bpai> bpaiList = bpaiRepository.findByIdIn(ids);

            bpaiList.forEach( bpaix -> {
                bpaix.setDtaten(EnCryptionAESService.encrypt(dateBpa));
            });

            count = this.save(bpaiList).size();
        } else {
            bpai.setDtaten(EnCryptionAESService.encrypt(dateBpa));
            this.save(bpai);

            count = 1;
        }

        return count;
    }

    private int editQtService(List<Integer> qtService, List<Long> ids, Bpai bpai, User user, int count) {
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

                        bpaix.setQt(String.format("%06d", qtMax));
                    }
                });

                count = this.saveAndFlush(bpaiList).size();
            }

        } else {
            bpai.setQt(String.format("%06d", qtService.get(0)));
            this.saveAndFlush(bpai);

            count = 1;
        }

        return count;
    }

    private int editCep(String cep , List<Long> cepsIds, Bpai bpai, User user, int count) {
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

                EnCryptionAESService.decryptBpaiCep(bpaiList);

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

                        cepBpai.setCepPcnte(EnCryptionAESService.encrypt(closestNumber));
                    }
                }

                count = this.save(bpaiList).size();
            }

        } else {
            bpai.setCepPcnte(EnCryptionAESService.encrypt(cep));

            this.save(bpai);

            count = 1;
        }

        return count;
    }

    private int editCepBlank(List<Long> cepsIds, User user, int count) {
        if(cepsIds != null) {
            List<Bpai> bpaiList = bpaiRepository.findByIdIn(cepsIds);

            AddressUser address = user.getAddressUser();

            EnCryptionAESService.decryptAddressUser(address);

            for (Bpai bpai: bpaiList) {
                bpai.setIbge(address.getIbge());
                bpai.setCepPcnte(address.getCep());
                bpai.setLogradPcnte(address.getCodLograud());
                bpai.setComplPcnte(address.getComplemento());
                bpai.setEndPcnte(address.getLogradouro());
                bpai.setBairroPcnte(address.getBairro());
            }

           return this.save(bpaiList).size();
        }

        return 0;
    }

    private int editDtNasc(String dtNasc, List<Long> ids, Bpai bpai, int count) {
        if(ids != null) {
            List<Bpai> bpaiList = bpaiRepository.findByIdIn(ids);
            int yearCurrent = LocalDate.now(ZoneId.of(ZoneTime.BR.getBr())).getYear();

            EnCryptionAESService.decryptBpaiIdadeAndDtnasc(bpaiList);

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

            EnCryptionAESService.encryptBpaiIdadeAndDtnasc(bpaiList);

            count = this.save(bpaiList).size();

        } else {
            bpai.setDtnasc(EnCryptionAESService.encrypt(dtNasc));
            this.save(bpai);

            count = 1;
        }

        return count;
    }

    private int editPa(String pa, Bpai bpai, Bpa bpa, int count) {
        String newPa = pa.split("-")[0];
        String updateAll = pa.split("-")[1];

        if(updateAll.equals("1")) {
            List<Bpai> bpaiList = bpaiRepository.findByPaAndBpa(pa.split("-")[2], bpa);

            bpaiList.forEach( bpaix -> {
                bpaix.setPa(newPa);
            });

           count = this.saveAndFlush(bpaiList).size();

        } else {
            bpai.setPa(newPa);
            this.saveAndFlush(bpai);

            count = 1;
        }

        return count;
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

    public Bpai saveAndFlush(Bpai bpai) {
        return bpaiRepository.saveAndFlush(bpai);
    }

    public List<Bpai> saveAndFlush(List<Bpai> bpais) {
        return bpaiRepository.saveAllAndFlush(bpais);
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
                    errors.add(new ErrorQtMaxDTODTO(bpai.getId(), flh, seq, "MAXIMUM QUANTITY EXCEEDED", EnCryptionAESService.decrypt(bpai.getNmpac()), qt, qtMax, pa));
                }
            }
        }

        return errors;
    }


    public void EntityManagerDetach(List<Bpai> bpaiList) {
        for (Bpai bpai : bpaiList) {
            entityManager.detach(bpai);
        }
    }

}
