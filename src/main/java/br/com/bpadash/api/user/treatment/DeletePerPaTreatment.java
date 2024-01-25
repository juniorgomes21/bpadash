package br.com.bpadash.api.user.treatment;

import br.com.bpadash.dto.treatment.TreatmentPaDeleteDTO;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpac;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.user.User;
import br.com.bpadash.model.treatment.RuleTreatmentPaDelete;
import br.com.bpadash.params.bpa.ParamDateBpa;
import br.com.bpadash.params.treatment.ParamTreatmentPaDelete;
import br.com.bpadash.params.treatment.ParamUpdateExecuteFile;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpacService;
import br.com.bpadash.services.bpa.BpaiService;
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
@RequestMapping("/api/treatment/deleteperpa")
public class DeletePerPaTreatment {

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
    @Autowired
    private RuleTreatmentPaDeleteService ruleTreatmentPaDeleteService;


    @GetMapping("/get")
    public ResponseEntity<TreatmentPaDeleteDTO> getTreatmentPaDelete(Authentication authentication) {
        User user = userService.get(authentication);

        return ResponseEntity.ok(new TreatmentPaDeleteDTO(user.getTreatmentFile()));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createTreatmentPaDelete(@RequestBody @Valid ParamTreatmentPaDelete paramTreatmentPaDelete, Authentication authentication) {
        User user = userService.get(authentication);

        String response = treatmentFileService.isValidParans(paramTreatmentPaDelete, user, false);

        if(!response.equals("OK")) {
            return ResponseEntity.badRequest().body(response);
        }

        RuleTreatmentPaDelete ruleTreatmentPaDelete = ruleTreatmentPaDeleteService.create(paramTreatmentPaDelete);

        treatmentFileService.addAndSaveRulePaDelete(ruleTreatmentPaDelete, user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Object> editTreatmentPaDelete(@PathVariable Long id, @RequestBody @Valid ParamTreatmentPaDelete paramTreatmentPaDelete, Authentication authentication) {
        User user = userService.get(authentication);

        String response = treatmentFileService.isValidParans(paramTreatmentPaDelete, user, true);

        if(!response.equals("OK")) {
            return ResponseEntity.badRequest().body(response);
        }

        treatmentFileService.editAndSaveRulePaDelete(paramTreatmentPaDelete, id, user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> deleteRulePaDelete(@PathVariable Long id, Authentication authentication) {
        User user = userService.get(authentication);

        if(treatmentFileService.removeAndSaveRulePaDelete(id, user)) ruleTreatmentPaDeleteService.delete(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/execute/{id}")
    public ResponseEntity<Object> playTreatmentPaDelete(@PathVariable Long id, @RequestBody @Valid ParamDateBpa paramDateBpa, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramDateBpa.getDateBpa()), user);

        if(bpaOptional.isPresent()) {

            int count;
            Bpa bpa = bpaOptional.get();

            if(id == 0L) {
                List<RuleTreatmentPaDelete> ruleTreatmentPaDeletes = user.getTreatmentFile().getRuleTreatmentPaDeleteList();
                List<Bpac> bpacList = bpacService.get(bpa);
                List<Bpai> bpaiList = bpaiService.get(bpa);

                count = treatmentFileService.executeRulePaDelete(bpacList, bpaiList, ruleTreatmentPaDeletes, user);

                bpacService.save(bpacList);
                bpaiService.save(bpaiList);
            } else {
                RuleTreatmentPaDelete ruleTreatmentPaDelete = ruleTreatmentPaDeleteService.get(id);

                List<Bpac> bpacList = new ArrayList<>();
                if(ruleTreatmentPaDelete.isExecuteBpac()) bpacList = bpacService.get(bpa);

                List<Bpai> bpaiList = new ArrayList<>();
                if(ruleTreatmentPaDelete.isExecuteBpai()) bpaiList = bpaiService.get(bpa);

                count = treatmentFileService.executeRulePaDelete(bpacList, bpaiList, new ArrayList<>(List.of(ruleTreatmentPaDelete)), user);

                bpacService.save(bpacList);
                bpaiService.save(bpaiList);
            }

            return ResponseEntity.ok(count);
        }


        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/update/execute/file/{id}")
    public ResponseEntity<Object> updateExecuteFilePa(@PathVariable Long id, @RequestBody @Valid ParamUpdateExecuteFile paramUpdateExecuteFile) {

        RuleTreatmentPaDelete ruleTreatmentPaDelete = ruleTreatmentPaDeleteService.get(id);
        ruleTreatmentPaDeleteService.updateAndSave(ruleTreatmentPaDelete, paramUpdateExecuteFile);

        return ResponseEntity.ok().build();
    }

}
