package br.com.bpadash.services.bpa;

import br.com.bpadash.dto.bpa.BpaiDTO;
import br.com.bpadash.dto.sigtap.ErrorOccupationDTO;
import br.com.bpadash.dto.sigtap.ErrorPaDTO;
import br.com.bpadash.dto.sigtap.ErrorProcedureDTO;
import br.com.bpadash.dto.sigtap.ErrorQtMaxDTODTO;
import br.com.bpadash.errorValidation.ErrorValidationDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.*;
import br.com.bpadash.params.bpa.ParamUpdateBpai;
import br.com.bpadash.params.bpa.ParamUpdateErrorsBpa;
import br.com.bpadash.repository.bpa.BpaiRepository;
import br.com.bpadash.repository.bpa.BpaiValidationRepository;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.user.UserService;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BpaiService {

    @Autowired
    private BpaiRepository bpaiRepository;

    @Autowired
    private BpaiValidationRepository bpaiValidationRepository;


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
                errors.add(errorValidation("CMP", "O campo deverá ser preenchido apenas com números formato AAAAMM."));
            }
        }

        String cnsmed = line.substring(15, 30);  // 4
        String cbo = line.substring(30, 36);
        if(false) {
            if(false) {
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

        List<BpaiDTO> bpacDTOList = page.getContent().stream()
                .map(bpai -> new BpaiDTO(bpai, bpa.getIdentifier()))
                .collect(Collectors.toList());

        return new PageImpl<>(bpacDTOList, pageable, page.getTotalElements());
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

        return this.save(bpai);
    }

    public String editAndSave(Bpai bpai, ParamUpdateErrorsBpa paramBpa, User user, Bpa bpa) {
        boolean hasChange = false;

        if(paramBpa.getPa() != null && !bpai.getPa().equals(paramBpa.getPa())) {
            bpai.setPa(paramBpa.getPa());
            hasChange = true;
        } else if(paramBpa.getDate() != null && !EncryptionService.decrypt(bpai.getDtnasc()).equals(paramBpa.getDate())) {
            bpai.setDtnasc(EncryptionService.encrypt(paramBpa.getDate()));
            hasChange = true;
        } else if(paramBpa.getCep() != null && !EncryptionService.decrypt(bpai.getCepPcnte()).equals(paramBpa.getCep())) {
            bpai.setCepPcnte(EncryptionService.encrypt(paramBpa.getCep()));
            hasChange = true;
        } else if(paramBpa.getQtService() != null && !bpai.getQt().equals(String.valueOf(paramBpa.getQtService().get(0)))) {
            bpai.setQt(String.valueOf(paramBpa.getQtService().get(0)));
            hasChange = true;
        } else if(paramBpa.getDateBpaInvalid() != null) {
            bpai.setDtaten(paramBpa.getDateBpaInvalid().split("/")[1].replaceAll("-", ""));
            hasChange = true;
        } else if (paramBpa.getRace() != null) {
            bpai.setRaca(EncryptionService.encrypt(paramBpa.getRace()));
            hasChange = true;
        } else if (paramBpa.getCnsmed() != null && !bpai.getCnsmed().equals(paramBpa.getCnsmed())) {
            String cnsmed = paramBpa.getCnsmed().split("-")[0];
            String updateAll = paramBpa.getCnsmed().split("-")[1];

            if(updateAll.equals("1")) {
                List<Bpai> bpaiList = bpaiRepository.findByCnsmed(paramBpa.getCnsmed().split("-")[2]);

                bpaiList.forEach( bpaix -> {
                    bpaix.setCnsmed(cnsmed);
                });

                this.save(bpaiList);
            } else {
                bpai.setCnsmed(cnsmed);
                hasChange = true;
            }
        } else if (paramBpa.getSexCurrent() != null) {
            bpai.setSexo(EncryptionService.encrypt(paramBpa.getSexCurrent()));
            hasChange = true;
        } else if (paramBpa.getCbo() != null) {
            bpai.setCbo(paramBpa.getCbo());
            hasChange = true;
        } else if (paramBpa.getAge() != null) {
            bpai.setIdade(EncryptionService.encrypt(String.valueOf(paramBpa.getAge())));
            hasChange = true;
        }


        if(hasChange) this.save(bpai);

        return "OK";
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

    public List<Bpai> getBpaiList(Bpa bpa) {
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

            if (!paExists) {
                errorsPa.add(new ErrorPaDTO(bpai.getId(), flh, seq, "NOT EXIST PA IN BPAI", pa, "FPO"));
            }

            if(!occupationPaSet.contains(pa)) {
                errorsPa.add(new ErrorPaDTO(bpai.getId(), flh, seq, "NOT EXIST PA", pa, "OCCUPATION"));
            }

            if(!procedurePaSet.contains(bpai.getPa())) {
                errorsPa.add(new ErrorPaDTO(bpai.getId(), flh, seq, "NOT EXIST PA", pa, "PROCEDURE"));
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

