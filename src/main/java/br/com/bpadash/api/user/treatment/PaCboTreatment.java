package br.com.bpadash.api.user.treatment;

import br.com.bpadash.dto.treatment.TreatmentPaCboDTO;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpac;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.user.User;
import br.com.bpadash.model.treatment.RuleTreatmentPaCbo;
import br.com.bpadash.params.bpa.ParamDateBpa;
import br.com.bpadash.params.treatment.ParamTreatmentPaCbo;
import br.com.bpadash.params.treatment.ParamUpdateExecuteFile;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpacService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.treatment.RuleTreatmentPaCboService;
import br.com.bpadash.services.treatment.RuleTreatmentPaDeleteService;
import br.com.bpadash.services.treatment.RuleTreatmentPaService;
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
@RequestMapping("/api/treatment/replacement/pa/cbo")
public class PaCboTreatment {

    @Autowired
    private UserService userService;
    @Autowired
    private TreatmentFileService treatmentFileService;
    @Autowired
    private RuleTreatmentPaService ruleTreatmentPaService;
    @Autowired
    private RuleTreatmentPaCboService ruleTreatmentPaCboService;
    @Autowired
    private RuleTreatmentPaDeleteService ruleTreatmentPaDeleteService;
    @Autowired
    private BpaService bpaService;
    @Autowired
    private BpaiService bpaiService;
    @Autowired
    private BpacService bpacService;


    @GetMapping("/get")
    public ResponseEntity<TreatmentPaCboDTO> getTreatmentPaCbo(Authentication authentication) {
        User user = userService.get(authentication);

        return ResponseEntity.ok(new TreatmentPaCboDTO(user.getTreatmentFile()));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createTreatmentPa(@RequestBody @Valid ParamTreatmentPaCbo paramTreatmentPaCbo, Authentication authentication) {
        User user = userService.get(authentication);

        String response = treatmentFileService.isValidParans(paramTreatmentPaCbo, user, false);

        if(!response.equals("OK")) {
            return ResponseEntity.badRequest().body(response);
        }

        RuleTreatmentPaCbo ruleTreatmentPaCbo = ruleTreatmentPaCboService.create(paramTreatmentPaCbo);

        treatmentFileService.addRulePaCbo(ruleTreatmentPaCbo, user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Object> editTreatmentPa(@PathVariable Long id, @RequestBody @Valid ParamTreatmentPaCbo paramTreatmentPaCbo, Authentication authentication) {
        User user = userService.get(authentication);

        String response = treatmentFileService.isValidParans(paramTreatmentPaCbo, user, true);

        if(!response.equals("OK")) {
            return ResponseEntity.badRequest().body(response);
        }

        treatmentFileService.editAndSaveRulePaCbo(paramTreatmentPaCbo, id, user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> deleteRulePaCbo(@PathVariable Long id, Authentication authentication) {
        User user = userService.get(authentication);

        if(treatmentFileService.removeAndSaveRulePaCbo(id, user)) ruleTreatmentPaCboService.delete(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/execute/{id}")
    public ResponseEntity<Object> playTreatmentPaCbo(@PathVariable Long id, @RequestBody @Valid ParamDateBpa paramDateBpa, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramDateBpa.getDateBpa()), user);

        if(bpaOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();
            int count;

            if(id == 0L) {
                List<RuleTreatmentPaCbo> ruleTreatmentPaCbos = user.getTreatmentFile().getRuleTreatmentPaCboList();
                List<Bpac> bpacList = bpacService.get(bpa);
                List<Bpai> bpaiList = bpaiService.get(bpa);

                count = treatmentFileService.executeRulePaCbo(bpacList, bpaiList, ruleTreatmentPaCbos);

                bpacService.save(bpacList);
                bpaiService.save(bpaiList);
            } else {
                RuleTreatmentPaCbo ruleTreatmentPaCbo = ruleTreatmentPaCboService.get(id);

                List<Bpac> bpacList = new ArrayList<>();
                if(ruleTreatmentPaCbo.isExecuteBpac()) bpacList = bpacService.get(bpa);

                List<Bpai> bpaiList = new ArrayList<>();
                if(ruleTreatmentPaCbo.isExecuteBpai()) bpaiList = bpaiService.get(bpa);

                count = treatmentFileService.executeRulePaCbo(bpacList, bpaiList, new ArrayList<>(List.of(ruleTreatmentPaCbo)));

                bpacService.save(bpacList);
                bpaiService.save(bpaiList);
            }

            return ResponseEntity.ok(count);
        }


        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/update/execute/file/{id}")
    public ResponseEntity<Object> updateExecutePaCboFile(@PathVariable Long id, @RequestBody @Valid ParamUpdateExecuteFile paramUpdateExecuteFile) {

        RuleTreatmentPaCbo ruleTreatmentPaCbo = ruleTreatmentPaCboService.get(id);
        ruleTreatmentPaCboService.updateAndSave(ruleTreatmentPaCbo, paramUpdateExecuteFile);

        return ResponseEntity.ok().build();
    }

}
