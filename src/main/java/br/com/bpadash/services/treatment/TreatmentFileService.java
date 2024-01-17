package br.com.bpadash.services.treatment;

import br.com.bpadash.model.Bpac;
import br.com.bpadash.model.Bpai;
import br.com.bpadash.model.TitleBpa;
import br.com.bpadash.model.User;
import br.com.bpadash.model.treatment.RuleTreatmentPa;
import br.com.bpadash.model.treatment.RuleTreatmentPaCbo;
import br.com.bpadash.model.treatment.RuleTreatmentPaDelete;
import br.com.bpadash.model.treatment.TreatmentFile;
import br.com.bpadash.params.treatment.ParamTreatmentPa;
import br.com.bpadash.params.treatment.ParamTreatmentPaCbo;
import br.com.bpadash.params.treatment.ParamTreatmentPaDelete;
import br.com.bpadash.repository.treatment.TreatmentFileRepository;
import br.com.bpadash.services.bpa.BpacService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.user.StorageService;
import br.com.bpadash.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TreatmentFileService {

    @Autowired
    private TreatmentFileRepository treatmentFileRepository;

    @Autowired
    private StorageService storageService;

    @Autowired
    private UserService userService;

    @Autowired
    private BpacService bpacService;

    @Autowired
    private BpaiService bpaiService;


    public void addAndSaveRulePa(RuleTreatmentPa ruleTreatmentPa , User user) {
        TreatmentFile treatmentFile = user.getTreatmentFile();

        treatmentFile.setCount(treatmentFile.getCount() - 1);
        treatmentFile.getRuleTreatmentPaList().add(ruleTreatmentPa);

        this.save(treatmentFile);
    }

    public void addAndSaveRulePaDelete(RuleTreatmentPaDelete ruleTreatmentPaDelete , User user) {
        TreatmentFile treatmentFile = user.getTreatmentFile();

        treatmentFile.setCount(treatmentFile.getCount() - 1);
        treatmentFile.getRuleTreatmentPaDeleteList().add(ruleTreatmentPaDelete);

        this.save(treatmentFile);
    }

    public void editAndSaveRulePa(ParamTreatmentPa paramTreatmentPa , Long id , User user) {
        TreatmentFile treatmentFile = user.getTreatmentFile();

        Optional<RuleTreatmentPa> ruleTreatmentPaOptional = treatmentFile.getRuleTreatmentPaList().stream().filter(rule -> rule.getId().equals(id)).findFirst();

        if(ruleTreatmentPaOptional.isPresent()) {
            RuleTreatmentPa ruleTreatmentPa = ruleTreatmentPaOptional.get();

            ruleTreatmentPa.setPaCurrent(paramTreatmentPa.getPaCurrent());
            ruleTreatmentPa.setPaNew(paramTreatmentPa.getNewPa());

            this.save(treatmentFile);
        }
    }

    public void editAndSaveRulePaDelete(ParamTreatmentPaDelete paramTreatmentPaDelete , Long id , User user) {
        TreatmentFile treatmentFile = user.getTreatmentFile();

        Optional<RuleTreatmentPaDelete> ruleTreatmentPaDeleteOptional = treatmentFile.getRuleTreatmentPaDeleteList().stream().filter(rule -> rule.getId().equals(id)).findFirst();

        if(ruleTreatmentPaDeleteOptional.isPresent()) {
            RuleTreatmentPaDelete ruleTreatmentPaDelete = ruleTreatmentPaDeleteOptional.get();

            ruleTreatmentPaDelete.setPa(paramTreatmentPaDelete.getPa());

            this.save(treatmentFile);
        }
    }

    public void editAndSaveRulePaCbo(ParamTreatmentPaCbo paramTreatmentPaCbo , Long id , User user) {
        TreatmentFile treatmentFile = user.getTreatmentFile();

        Optional<RuleTreatmentPaCbo> ruleTreatmentPaCboOptional = treatmentFile.getRuleTreatmentPaCboList().stream().filter(rule -> rule.getId().equals(id)).findFirst();

        if(ruleTreatmentPaCboOptional.isPresent()) {
            RuleTreatmentPaCbo ruleTreatmentPaCbo = ruleTreatmentPaCboOptional.get();

            ruleTreatmentPaCbo.setPa(paramTreatmentPaCbo.getPa());
            ruleTreatmentPaCbo.setCboCurrent(paramTreatmentPaCbo.getCboCurrent());
            ruleTreatmentPaCbo.setCboNew(paramTreatmentPaCbo.getCboNew());

            this.save(treatmentFile);
        }
    }

    public String removeAndSaveRulePa(Long id, User user) {
        TreatmentFile treatmentFile = user.getTreatmentFile();

        Optional<RuleTreatmentPa> ruleTreatmentPaOptional = treatmentFile.getRuleTreatmentPaList().stream().filter(rule -> rule.getId().equals(id)).findFirst();
        if(ruleTreatmentPaOptional.isPresent()) {
            treatmentFile.setCount(treatmentFile.getCount() + 1);
            treatmentFile.getRuleTreatmentPaList().remove(ruleTreatmentPaOptional.get());

            this.save(treatmentFile);

            return "OK";
        }

        return null;
    }

    public boolean removeAndSaveRulePaDelete(Long id , User user) {
        TreatmentFile treatmentFile = user.getTreatmentFile();

        Optional<RuleTreatmentPaDelete> ruleTreatmentPaDeleteOptional = treatmentFile.getRuleTreatmentPaDeleteList().stream().filter(rule -> rule.getId().equals(id)).findFirst();
        if(ruleTreatmentPaDeleteOptional.isPresent()) {
            treatmentFile.setCount(treatmentFile.getCount() + 1);
            treatmentFile.getRuleTreatmentPaDeleteList().remove(ruleTreatmentPaDeleteOptional.get());

            this.save(treatmentFile);

            return true;
        }

        return false;
    }

    public int executeRulePa(List<Bpac> bpacList, List<Bpai> bpaiList, List<RuleTreatmentPa> ruleTreatmentPaList) {
        int count = 0;

        for(RuleTreatmentPa rule: ruleTreatmentPaList) {
            if(rule.isExecuteBpac()) {
                for(Bpac bpac: bpacList) {
                    if(bpac.getPa().equals(rule.getPaCurrent())) {
                        bpac.setPa(rule.getPaNew());
                        count++;
                    }
                }
            }

            if(rule.isExecuteBpai()) {
                for(Bpai bpai: bpaiList) {
                    if(bpai.getPa().equals(rule.getPaCurrent())) {
                        bpai.setPa(rule.getPaNew());
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public int executeRulePaDelete(List<Bpac> bpacList, List<Bpai> bpaiList, List<RuleTreatmentPaDelete> ruleTreatmentPaDeletes,User user) {
        int count = 0;

        List<Bpac> bpacProcessedList = new ArrayList<>();
        List<Bpai> bpaiProcessedList = new ArrayList<>();

        for (RuleTreatmentPaDelete rule: ruleTreatmentPaDeletes) {
            if(rule.isExecuteBpac()) {
                for(Bpac bpac: bpacList) {
                    if(bpac.getPa().equals(rule.getPa())) {
                        bpacProcessedList.add(bpac);
                        count++;
                    }
                }
            }

            if(rule.isExecuteBpai()) {
                for(Bpai bpai: bpaiList) {
                    if(bpai.getPa().equals(rule.getPa())) {
                        bpaiProcessedList.add(bpai);
                        count++;
                    }
                }
            }
        }

        bpacList.removeAll(bpacProcessedList);
        bpaiList.removeAll(bpaiProcessedList);

        Long quantityBytes = storageService.quantityBytes(new TitleBpa(), bpacProcessedList, bpaiProcessedList);

        userService.updateStorageAndSave(user, quantityBytes, "add");

        bpacService.delete(bpacProcessedList);
        bpaiService.delete(bpaiProcessedList);

        return count;
    }

    public int executeRulePaCbo(List<Bpac> bpacList , List<Bpai> bpaiList , List<RuleTreatmentPaCbo> ruleTreatmentPaCbos) {
        int count = 0;

        for (RuleTreatmentPaCbo rule: ruleTreatmentPaCbos) {
            if(rule.isExecuteBpac()) {
                for(Bpac bpac: bpacList) {
                    if(bpac.getPa().equals(rule.getPa()) && bpac.getCbo().equals(rule.getCboCurrent())) {
                        bpac.setCbo(rule.getCboNew());
                        count++;
                    }
                }
            }

            if(rule.isExecuteBpai()) {
                for(Bpai bpai: bpaiList) {
                    if(bpai.getPa().equals(rule.getPa()) && bpai.getCbo().equals(rule.getCboCurrent())) {
                        bpai.setCbo(rule.getCboNew());
                        count++;
                    }
                }
            }
        }


        return count;
    }

    public void addRulePaCbo(RuleTreatmentPaCbo ruleTreatmentPaCbo , User user) {
        TreatmentFile treatmentFile = user.getTreatmentFile();

        treatmentFile.setCount(treatmentFile.getCount() - 1);
        treatmentFile.getRuleTreatmentPaCboList().add(ruleTreatmentPaCbo);
    }

    public TreatmentFile save(TreatmentFile treatmentFile) {
        return treatmentFileRepository.save(treatmentFile);
    }

    public String isValidParans(ParamTreatmentPa paramTreatmentPa , User user, boolean edit) {
        boolean isEquals = paramTreatmentPa.getPaCurrent().equals(paramTreatmentPa.getNewPa());

        if(isEquals) {
            return "PARANS IQUALS";
        }

        if(!edit && user.getTreatmentFile().getCount() == 0) {
            return "REACHED MAX LENGTH";
        }

        if(user.getTreatmentFile().getRuleTreatmentPaList().stream().anyMatch(rule -> rule.getPaCurrent().equals(paramTreatmentPa.getPaCurrent()))) {
            return "EXIST RULE PA";
        }

        return "OK";
    }

    public String isValidParans(ParamTreatmentPaCbo paramTreatmentPaCbo , User user, boolean edit) {
        boolean isEquals = paramTreatmentPaCbo.getCboCurrent().equals(paramTreatmentPaCbo.getCboNew());

        if(isEquals) {
            return "PARANS IQUALS";
        }

        if(!edit && user.getTreatmentFile().getCount() == 0) {
            return "REACHED MAX LENGTH";
        }

        if(user.getTreatmentFile().getRuleTreatmentPaCboList().stream().anyMatch(rule -> rule.getCboCurrent().equals(paramTreatmentPaCbo.getCboCurrent()))) {
            return "EXIST RULE";
        }

        return "OK";
    }


    public String isValidParans(ParamTreatmentPaDelete paramTreatmentPaDelete , User user, boolean edit) {

        if(!edit && user.getTreatmentFile().getCount() == 0) {
            return "REACHED MAX LENGTH";
        }

        if(user.getTreatmentFile().getRuleTreatmentPaDeleteList().stream().anyMatch(rule -> rule.getPa().equals(paramTreatmentPaDelete.getPa()))) {
            return "EXIST RULE";
        }

        return "OK";
    }

    public boolean removeAndSaveRulePaCbo(Long id , User user) {
        TreatmentFile treatmentFile = user.getTreatmentFile();

        Optional<RuleTreatmentPaCbo> ruleTreatmentPaCboOptional = treatmentFile.getRuleTreatmentPaCboList().stream().filter(rule -> rule.getId().equals(id)).findFirst();
        if(ruleTreatmentPaCboOptional.isPresent()) {
            treatmentFile.setCount(treatmentFile.getCount() + 1);
            treatmentFile.getRuleTreatmentPaCboList().remove(ruleTreatmentPaCboOptional.get());

            this.save(treatmentFile);

            return true;
        }

        return false;
    }

}
