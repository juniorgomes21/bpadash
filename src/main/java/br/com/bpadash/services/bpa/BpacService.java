package br.com.bpadash.services.bpa;

import br.com.bpadash.dto.bpa.BpacDTO;
import br.com.bpadash.dto.sigtap.ErrorOccupationDTO;
import br.com.bpadash.dto.sigtap.ErrorPaDTO;
import br.com.bpadash.errorValidation.ErrorValidationDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpac;
import br.com.bpadash.model.sigtap.Fpo;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.bpa.ParamUpdateBpac;
import br.com.bpadash.params.bpa.ParamUpdateErrorsBpa;
import br.com.bpadash.repository.bpa.BpacRepository;
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


    public Optional<Bpac> get(Long id) {
        return bpacRepository.findById(id);
    }

    public List<Bpac> get(Bpa bpa) {

        return bpacRepository.findByBpa(bpa);
    }

    public Bpac getLast(Bpa bpa) {
        return bpacRepository.findTopByBpaOrderByFlhDescSeqDesc(bpa);
    }

    public Page<BpacDTO> get(Bpa bpa , Pageable pageable) {
        Page<Bpac> page = bpacRepository.findByBpa(bpa, pageable);

        List<BpacDTO> bpacDTOList = page.getContent().stream()
                .map(bpac -> new BpacDTO(bpac, bpa.getIdentifier()))
                .collect(Collectors.toList());

        return new PageImpl<>(bpacDTOList, pageable, page.getTotalElements());
    }

    public Bpac create(User user, String line, int lineNumber, Bpa bpa, List<ErrorsFile> errorsFileList) {

            List<ErrorValidationDTO> errors = new ArrayList<>();

            if (line.length() < 48) {
                errors.add(errorValidation("LINHA","A linha Não contém 49 caracteres."));

                return null;
            }

            String iden = line.substring(0, 2);
            String cnes = line.substring(2, 9);
            String cmp = line.substring(9, 15);
            String cbo = line.substring(15, 21);
            String flh = line.substring(21, 24);
            String seq = line.substring(24, 26);
            String pa = line.substring(26, 36);
            String idade = line.substring(36, 39);
            String qt = line.substring(39, 45);
            String org = line.substring(45, 48);
            String fim;

            try {
                fim = line.substring(48, 50);
            } catch (StringIndexOutOfBoundsException e) {
                fim = "  ";
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
        bpac.setFim(paramUpdateBpac.getFim());

        return this.save(bpac);
    }

    public int editAndSave(Bpac bpac, ParamUpdateErrorsBpa paramBpa, Bpa bpa) {
        int count = 0;

        switch (paramBpa.getKey()) {
            case "pa" -> count =  this.editPa(paramBpa.getPa(), bpac, bpa, count);
            case "cbo" -> count = this.editCbo(paramBpa.getCbo(), bpac, bpa, count);
        }

        return count;
    }

    private int editPa(String pa, Bpac bpac, Bpa bpa, int count) {
        String newPa = pa.split("-")[0];
        String updateAll = pa.split("-")[1];

        if(updateAll.equals("1")) {
            List<Bpac> bpacList = bpacRepository.findByPaAndBpa(pa.split("-")[2], bpa);

            bpacList.forEach( bpacx -> {
                bpacx.setPa(newPa);
            });

            count = this.saveAndFlush(bpacList).size();
        } else {
            bpac.setPa(newPa);
            this.saveAndFlush(bpac);

            count = 1;
        }

        return count;
    }

    private int editCbo(String cboParam, Bpac bpac, Bpa bpa, int count) {
        String cbo = cboParam.split("-")[0];
        String updateAll = cboParam.split("-")[1];

        if(updateAll.equals("1")) {
            List<Bpac> bpacList = bpacRepository.findByCboAndBpa(cboParam.split("-")[2], bpa);

            bpacList.forEach( bpaix -> {
                bpaix.setCbo(cbo);
            });

            count = this.save(bpacList).size();
        } else {
            bpac.setCbo(cbo);
            this.save(bpac);

            count = 1;
        }

        return count;
    }

    public Bpac save(Bpac bpac) {
        return bpacRepository.save(bpac);
    }

    public Bpac saveAndFlush(Bpac bpac) {
        return bpacRepository.saveAndFlush(bpac);
    }

    public List<Bpac> saveAndFlush(List<Bpac> bpacList) {
        return bpacRepository.saveAllAndFlush(bpacList);
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

    public void delete(Bpa bpa) {
        bpacRepository.deleteByBpa(bpa);
    }

    @Transactional
    public void deleteByIds(List<Long> listIds) {
        bpacRepository.deleteAllById(listIds);
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

            ErrorPaDTO errorPaDTO = new ErrorPaDTO(bpac.getId(), flh, seq, "", bpac.getPa(), "bpac");
            if (!paExists) {
                errorPaDTO.setMsg("FPO ");
            }

            if(!occupationPaSet.contains(pa)) {
                errorPaDTO.setMsg(errorPaDTO.getMsg() + "OCUPAÇÃO");
            }

            if(!procedurePaSet.contains(bpac.getPa())) {
                errorPaDTO.setMsg(errorPaDTO.getMsg() + " PROCEDIMENTO");
            }

            if(!errorPaDTO.getMsg().equals("")) {
                errorsPa.add(errorPaDTO);
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
