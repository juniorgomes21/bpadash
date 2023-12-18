package br.com.bpadash.services.bpa;

import br.com.bpadash.dto.bpa.BpacDTO;
import br.com.bpadash.dto.sigtap.ErrorOccupationDTO;
import br.com.bpadash.dto.sigtap.ErrorPaDTO;
import br.com.bpadash.errorValidation.ErrorValidationDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.*;
import br.com.bpadash.params.bpa.ParamUpdateBpac;
import br.com.bpadash.params.bpa.ParamUpdateErrorsBpa;
import br.com.bpadash.repository.bpa.BpacRepository;
import br.com.bpadash.repository.bpa.BpacValidationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BpacService {

    @Autowired
    private BpacRepository bpacRepository;

    @Autowired
    private BpacValidationRepository bpacValidationRepository;

    public Bpac create(User user, String line, int lineNumber, Bpa bpa, List<ErrorsFile> errorsFileList) {

            List<ErrorValidationDTO> errors = new ArrayList<>();
            
            if (line.length() < 48) {
                errors.add(errorValidation("LINHA","A linha Não contém 49 caracteres."));
            }

            String iden = line.substring(0, 2);
            String cnes = line.substring(2, 9);
            if(user.getBpacValidation().isCnes()) {
                if(!cnes.matches("\\d+")) {
                    errors.add(errorValidation("CNE","O código CNES deverá ser preenchido apenas com números. Adicionar zeros à esquerda."));
                }
            }

            String cmp = line.substring(9, 15);
            if(user.getBpacValidation().isCmp()) {
                if(!cmp.matches("\\d+")) {
                    errors.add(errorValidation("CMP", "O campo deverá ser preenchido apenas com números formato AAAAMM."));
                }
            }

            //TODO verificar validação
            String cbo = line.substring(15, 21);
            if(false) {
                if(false) {
                    errors.add(errorValidation("CBO", "Código conforme a Classificação Brasileira de Ocupações (CBO)."));
                }
            }

            String flh = line.substring(21, 24);
            if(user.getBpacValidation().isFlh()) {
                if(!flh.matches("\\d+")) {
                    errors.add(errorValidation("FLH", "Número da folha do BPA. Domínio [001..999]. Adicionar zeros à esquerda de um inteiro."));
                }
            }

            String seq = line.substring(24, 26);
            if(user.getBpacValidation().isSeq()) {
                if(!seq.matches("\\d+")) {
                    errors.add(errorValidation("SEQ", "Número sequencial da linha dentro da folha do BPA. Domínio [01..20]. Adicionar zeros à esquerda de um inteiro."));
                }
            }

            String pa = line.substring(26, 36);
            if(user.getBpacValidation().isPa()) {
                if(!pa.matches("\\d+")) {
                    errors.add(errorValidation("PA", "O campo deverá ser preenchido apenas com números. Adicionar zeros à esquerda."));
                }
            }

            String idade = line.substring(36, 39);
            if(user.getBpacValidation().isIdade()) {
                if (!idade.matches("\\d+") || Integer.parseInt(idade) > 130) {
                    errors.add(errorValidation("IDADE" , "Idade (0 a 130 anos). O campo deverá ser preenchido apenas com números. Adicionar zeros à esquerda."));
                }
            }

            String qt = line.substring(39, 45);
            if(user.getBpacValidation().isQt()) {
                if(!cmp.matches("\\d+")) {
                    errors.add(errorValidation("QT", "O campo deverá ser preenchido apenas com números. Adicionar zeros à esquerda de um inteiro."));
                }
            }

            //TODO verificar validação
            String org = line.substring(45, 48);
            if(user.getBpacValidation().isOrg()) {
                if(!org.equals("BPA")) {
                    errors.add(errorValidation("ORG", "O campo deverá ser preenchido apenas com (\"BPA\", \"PNI\", \"SIE\", \"SIB\", \"MIN\", \"PAC\", \"SCL\" ou \"EXT\")"));
                }
            }

            String fim;

            try {
                fim = line.substring(48, 50);
            } catch (StringIndexOutOfBoundsException e) {
                fim = "  ";
            }

            if(!errors.isEmpty()) {
                errorsFileList.add(new ErrorsFile(String.valueOf(lineNumber), errors));

                return null;
            }

            return new Bpac(
                    bpa,
                    iden,
                    cnes,
                    cmp,
                    cbo,
                    flh,
                    seq,
                    pa,
                    idade,
                    qt,
                    org,
                    fim
            );
    }

    public Bpac bpacId(Long id) {
        Optional<Bpac> optionalBpac = bpacRepository.findById(id);

        return optionalBpac.get();
    }

    public Long sizeByte(List<Long> listIds) {
        return bpacRepository.calculateSizeById(listIds);
    }

    public Bpac editAndSave(Bpac bpac , ParamUpdateBpac paramUpdateBpac) {
        bpac.setCnes(paramUpdateBpac.getCnes());
        bpac.setCmp(paramUpdateBpac.getCmp());
        bpac.setCbo(paramUpdateBpac.getCbo());
        bpac.setFlh(paramUpdateBpac.getFlh());
        bpac.setSeq(paramUpdateBpac.getSeq());
        bpac.setPa(paramUpdateBpac.getPa());
        bpac.setIdade(paramUpdateBpac.getIdade());
        bpac.setQt(paramUpdateBpac.getQt());
        bpac.setOrg(paramUpdateBpac.getOrg());

        return this.save(bpac);
    }

    public String editAndSave(Bpac bpac , ParamUpdateErrorsBpa paramBpa) {
        boolean hasChange = false;

        if(paramBpa.getPa() != null && !bpac.getPa().equals(paramBpa.getPa())) {
            bpac.setPa(paramBpa.getPa());
            hasChange = true;

        } else if (paramBpa.getCbo() != null) {
            bpac.setCbo(paramBpa.getCbo());
            hasChange = true;
        }

        if(hasChange) this.save(bpac);

        return "OK";
    }

    public Bpac save(Bpac bpac) {
        return bpacRepository.save(bpac);
    }

    public List<Bpac> save(List<Bpac> bpacList) {
        return bpacRepository.saveAll(bpacList);
    }

    @Transactional
    public void delete(Bpac bpac) {
        bpacRepository.delete(bpac);
    }

    @Transactional
    public void delete(List<Bpac> bpacList) {
        bpacRepository.deleteAll(bpacList);
    }

    @Transactional
    public void delete(Bpa bpa) {
        bpacRepository.deleteByBpa(bpa);
    }

    @Transactional
    public void deleteByIds(List<Long> listIds) {
        bpacRepository.deleteAllById(listIds);
    }

    public List<Bpac> getBpacList(Bpa bpa) {

        return bpacRepository.findByBpa(bpa);
    }

    public Optional<Bpac> get(Long id) {

        return bpacRepository.findById(id);
    }

    public Page<BpacDTO> get(Bpa bpa , Pageable pageable) {
        Page<Bpac> page = bpacRepository.findByBpa(bpa, pageable);

        List<BpacDTO> bpacDTOList = page.getContent().stream()
                .map(bpac -> new BpacDTO(bpac, bpa.getIdentifier()))
                .collect(Collectors.toList());

        return new PageImpl<>(bpacDTOList, pageable, page.getTotalElements());
    }

    private ErrorValidationDTO errorValidation(String field, String message) {
        return new ErrorValidationDTO(field, message);
    }

    public List<ErrorPaDTO> verifyErrorsPa(List<Fpo> fpoList, Set<String> procedurePaSet, Set<String> occupationPaSet, List<Bpac> bpacListDB) {
        List<ErrorPaDTO> errorsPa = new ArrayList<>();

        bpacListDB.forEach( bpac -> {

            String pa = bpac.getPa().substring(0, 9);
            String flh = bpac.getFlh();
            String seq = bpac.getSeq();

            boolean paExists = fpoList.stream().anyMatch(fpo -> fpo.getPa().equals(pa));

            if (!paExists) {
                errorsPa.add(new ErrorPaDTO(bpac.getId(), flh, seq, "BPAC - NOT EXIST PA", bpac.getPa(), "FPO"));
            }

            if(!occupationPaSet.contains(pa)) {
                errorsPa.add(new ErrorPaDTO(bpac.getId(), flh, seq, "NOT EXIST PA", bpac.getPa(), "OCCUPATION"));
            }

            if(!procedurePaSet.contains(bpac.getPa())) {
                errorsPa.add(new ErrorPaDTO(bpac.getId(), flh, seq, "NOT EXIST PA", bpac.getPa(), "PROCEDURE"));
            }


        });

        return errorsPa;
    }

    public List<ErrorOccupationDTO> verifyErrorsOccupation(Set<String> occupationPa, Set<String> occupationCBO, List<Bpac> bpacListDB) {
        List<ErrorOccupationDTO> errors = new ArrayList<>();

        for (Bpac bpac: bpacListDB) {

            String pa = bpac.getPa().substring(0, 9);
            String cbo = bpac.getCbo();
            String flh = bpac.getFlh();
            String seq = bpac.getSeq();


            if (occupationPa.contains(pa)) {

                boolean exitsCBO = occupationCBO.stream().anyMatch(cboString -> cboString.contains(cbo));

                if(!exitsCBO) {
                    errors.add(new ErrorOccupationDTO(bpac.getId(), flh, seq, cbo, bpac.getPa(), "NOT EXIST CBO IN OCCUPATION BPAC"));
                }
            }
        }


        return errors;
    }

}
