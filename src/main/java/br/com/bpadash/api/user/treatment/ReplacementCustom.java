package br.com.bpadash.api.user.treatment;

import br.com.bpadash.dto.treatment.TreatmentReplaceCustomDTO;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpac;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.user.User;
import br.com.bpadash.model.treatment.RuleReplacementCustom;
import br.com.bpadash.params.bpa.ParamDateBpa;
import br.com.bpadash.params.treatment.ParamTreatmentReplaceCustom;
import br.com.bpadash.params.treatment.ParamUpdateExecuteFile;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpacService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.treatment.RuleTreatmentReplaceCustomService;
import br.com.bpadash.services.treatment.TreatmentFileService;
import br.com.bpadash.services.user.UserService;
import br.com.bpadash.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/treatment/replacement/custom")
public class ReplacementCustom {

    @Autowired
    private UserService userService;
    @Autowired
    private BpaService bpaService;
    @Autowired
    private BpacService bpacService;
    @Autowired
    private BpaiService bpaiService;
    @Autowired
    private RuleTreatmentReplaceCustomService ruleReplaceCustomService;
    @Autowired
    private TreatmentFileService treatmentFileService;


    @GetMapping("/get")
    public ResponseEntity<Object> get(Authentication authentication) {
        User user = userService.get(authentication);

        return ResponseEntity.ok(new TreatmentReplaceCustomDTO(user.getTreatmentFile()));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createTreatmentReplaceCustom(@RequestBody @Valid ParamTreatmentReplaceCustom paramTreatmentReplaceCustom, Authentication authentication) {
        User user = userService.get(authentication);

        String response = ruleReplaceCustomService.isValidParans(paramTreatmentReplaceCustom, user, false);

        if(!response.equals("OK")) {
            return ResponseEntity.badRequest().body(response);
        }

        RuleReplacementCustom ruleReplacementCustom = ruleReplaceCustomService.create(paramTreatmentReplaceCustom);

        treatmentFileService.addAndSaveRuleReplacementCustom(ruleReplacementCustom, user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Object> editTreatmentReplaceCustom(@PathVariable Long id, @RequestBody @Valid ParamTreatmentReplaceCustom paramTreatmentReplaceCustom, Authentication authentication) {
        User user = userService.get(authentication);

        String response = ruleReplaceCustomService.isValidParans(paramTreatmentReplaceCustom, user, true);

        if(!response.equals("OK")) {
            return ResponseEntity.badRequest().body(response);
        }

        treatmentFileService.editAndSaveRuleReplacementCustom(paramTreatmentReplaceCustom, id, user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/execute/{id}")
    public ResponseEntity<Object> playTreatmentReplaceCustom(@PathVariable Long id, @RequestBody @Valid ParamDateBpa paramDateBpa, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramDateBpa.getDateBpa()), user);

        if(bpaOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();

            List<Bpac> bpacModify = new ArrayList<>();
            List<Bpai> bpaiModify = new ArrayList<>();

            if(id == 0L) {
                List<String> values = new ArrayList<>(List.of(
                        "cnspac",
                        "cid",
                        "Nmpac",
                        "dtnasc",
                        "cnsmed",
                        "dtaten",
                        "idade",
                        "cepPcnte",
                        "logradPcnte",
                        "endPcnte",
                        "complPcnte",
                        "numPcnte",
                        "sexo",
                        "raca",
                        "bairroPcnte",
                        "ddtelPcnte",
                        "emailPcnte"
                ));

                List<RuleReplacementCustom> ruleReplacementCustomList = user.getTreatmentFile().getRuleReplacementCustoms();

                boolean countExecuteBpac = ruleReplacementCustomList.stream().anyMatch(RuleReplacementCustom::isExecuteBpac);
                boolean countExecuteBpai = ruleReplacementCustomList.stream().anyMatch(RuleReplacementCustom::isExecuteBpai);

                List<Bpac> bpacList = new ArrayList<>();
                if (countExecuteBpac)  bpacList = bpacService.get(bpa);

                List<Bpai> bpaiList = new ArrayList<>();

                boolean isDecrypt = false;
                if (countExecuteBpai) {
                    bpaiList = bpaiService.get(bpa);

                    isDecrypt = ruleReplacementCustomList.stream().anyMatch( rule ->
                            values.contains(rule.getField()) ||
                            values.contains(rule.getCriterionOne()) ||
                            values.contains(rule.getCriterionTwo()) ||
                            values.contains(rule.getCriterionThree()));

                    if(isDecrypt) EncryptionService.decryptBpai(bpaiList, true);
                }

                for (RuleReplacementCustom rule: ruleReplacementCustomList) {
                    if(rule.isExecuteBpac()) {
                        ruleReplaceCustomService.executeBpac(rule, bpacList, bpacModify);
                    } else {
                        ruleReplaceCustomService.executeBpai(rule, bpaiList, bpaiModify, true);
                    }
                }

                if(bpacModify.size() > 0) bpacService.saveAndFlush(bpacModify);
                if(bpaiModify.size() > 0) {
                    if(isDecrypt) EncryptionService.encryptBpai(bpaiList, true);
                    bpaiService.saveAndFlush(bpaiModify);
                }

            } else {
                RuleReplacementCustom ruleReplacementCustom = ruleReplaceCustomService.get(id);

                if(ruleReplacementCustom.isExecuteBpac()) {
                    List<Bpac> bpacList = bpacService.get(bpa);

                    ruleReplaceCustomService.executeBpac(ruleReplacementCustom, bpacList, bpacModify);

                    bpacService.saveAndFlush(bpacModify);
                } else {
                    List<Bpai> bpaiList = bpaiService.get(bpa);

                    ruleReplaceCustomService.executeBpai(ruleReplacementCustom, bpaiList, bpaiModify, false);

                    bpaiService.saveAndFlush(bpaiModify);
                }
            }

            bpaService.calculateAll(user, bpa, null, null, true);
            bpaService.save(bpa);

            return ResponseEntity.ok(bpacModify.size() + bpaiModify.size());
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> deleteRuleReplaceCustom(@PathVariable Long id, Authentication authentication) {
        User user = userService.get(authentication);

        if(treatmentFileService.removeAndSaveRuleCustomService(id, user)) ruleReplaceCustomService.delete(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/execute/file/{id}")
    public ResponseEntity<Object> updateExecuteFile(@PathVariable Long id, @RequestBody @Valid ParamUpdateExecuteFile paramUpdateExecuteFile) {

        RuleReplacementCustom ruleReplacementCustom = ruleReplaceCustomService.get(id);
        ruleReplaceCustomService.updateAndSave(ruleReplacementCustom, paramUpdateExecuteFile);

        return ResponseEntity.ok().build();
    }

}
