package br.com.bpadash.api.user.treatment;

import br.com.bpadash.dto.treatment.TreatmentPaDTO;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpac;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.user.User;
import br.com.bpadash.model.treatment.RuleTreatmentPa;
import br.com.bpadash.params.bpa.ParamDateBpa;
import br.com.bpadash.params.treatment.ParamTreatmentPa;
import br.com.bpadash.params.treatment.ParamUpdateExecuteFile;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpacService;
import br.com.bpadash.services.bpa.BpaiService;
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
@RequestMapping("/api/treatment/replacement/pa")
public class PaTreatment {

    @Autowired
    private BpaService bpaService;
    @Autowired
    private BpaiService bpaiService;
    @Autowired
    private BpacService bpacService;
    @Autowired
    private UserService userService;
    @Autowired
    private TreatmentFileService treatmentFileService;
    @Autowired
    private RuleTreatmentPaService ruleTreatmentPaService;


    @GetMapping("/get")
    public ResponseEntity<TreatmentPaDTO> getTreatmentPa(Authentication authentication) {
        User user = userService.get(authentication);

        return ResponseEntity.ok(new TreatmentPaDTO(user.getTreatmentFile()));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createTreatmentPa(@RequestBody @Valid ParamTreatmentPa paramTreatmentPa, Authentication authentication) {
        User user = userService.get(authentication);

        String response = treatmentFileService.isValidParans(paramTreatmentPa, user, false);

        if(!response.equals("OK")) {
            return ResponseEntity.badRequest().body(response);
        }

        RuleTreatmentPa ruleTreatmentPa = ruleTreatmentPaService.create(paramTreatmentPa);

        treatmentFileService.addAndSaveRulePa(ruleTreatmentPa, user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Object> editTreatmentPa(@PathVariable Long id, @RequestBody @Valid ParamTreatmentPa paramTreatmentPa, Authentication authentication) {
        User user = userService.get(authentication);

        String response = treatmentFileService.isValidParans(paramTreatmentPa, user, true);

        if(!response.equals("OK")) {
            return ResponseEntity.badRequest().body(response);
        }

        treatmentFileService.editAndSaveRulePa(paramTreatmentPa, id, user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/execute/{id}")
    public ResponseEntity<Object> playTreatmentPa(@PathVariable Long id, @RequestBody @Valid ParamDateBpa paramDateBpa, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramDateBpa.getDateBpa()), user);

        if(bpaOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();
            int count;
            if(id == 0L) {
                List<RuleTreatmentPa> ruleTreatmentPas = user.getTreatmentFile().getRuleTreatmentPaList();
                List<Bpac> bpacList = bpacService.get(bpa);
                List<Bpai> bpaiList = bpaiService.get(bpa);

                count = treatmentFileService.executeRulePa(bpacList, bpaiList, ruleTreatmentPas);

                bpacService.save(bpacList);
                bpaiService.save(bpaiList);

            } else {
                RuleTreatmentPa ruleTreatmentPa = ruleTreatmentPaService.get(id);

                List<Bpac> bpacList = new ArrayList<>();
                if(ruleTreatmentPa.isExecuteBpac()) bpacList = bpacService.get(bpa);

                List<Bpai> bpaiList = new ArrayList<>();
                if(ruleTreatmentPa.isExecuteBpai()) bpaiList = bpaiService.get(bpa);

                count = treatmentFileService.executeRulePa(bpacList, bpaiList, new ArrayList<>(List.of(ruleTreatmentPa)));

                bpacService.save(bpacList);
                bpaiService.save(bpaiList);
            }

            return ResponseEntity.ok(count);
        }


        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> deleteRulePa(@PathVariable Long id, Authentication authentication) {
        User user = userService.get(authentication);

        if(treatmentFileService.removeAndSaveRulePa(id, user)) ruleTreatmentPaService.delete(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/execute/file/{id}")
    public ResponseEntity<Object> updateExecuteFile(@PathVariable Long id, @RequestBody @Valid ParamUpdateExecuteFile paramUpdateExecuteFile) {

        RuleTreatmentPa ruleTreatmentPa = ruleTreatmentPaService.get(id);
        ruleTreatmentPaService.updateAndSave(ruleTreatmentPa, paramUpdateExecuteFile);

        return ResponseEntity.ok().build();
    }

}
