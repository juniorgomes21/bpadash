package br.com.bpadash.api.user.treatment;


import br.com.bpadash.dto.treatment.TreatmentPaCboDTO;
import br.com.bpadash.dto.treatment.TreatmentPaDTO;
import br.com.bpadash.dto.treatment.TreatmentPaDeleteDTO;
import br.com.bpadash.model.*;
import br.com.bpadash.model.treatment.RuleTreatmentPa;
import br.com.bpadash.model.treatment.RuleTreatmentPaCbo;
import br.com.bpadash.model.treatment.RuleTreatmentPaDelete;
import br.com.bpadash.params.bpa.ParamDateBpa;
import br.com.bpadash.params.treatment.ParamTreatmentPa;
import br.com.bpadash.params.treatment.ParamTreatmentPaCbo;
import br.com.bpadash.params.treatment.ParamTreatmentPaDelete;
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
@RequestMapping("/api/treatment")
public class TreatmentApi {

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


    @GetMapping("/get/pa")
    public ResponseEntity<TreatmentPaDTO> getTreatmentPa(Authentication authentication) {
        User user = userService.userInDb(1L);

        return ResponseEntity.ok(new TreatmentPaDTO(user.getTreatmentFile()));
    }

    @PostMapping("/create/pa")
    public ResponseEntity<Object> createTreatmentPa(@RequestBody @Valid ParamTreatmentPa paramTreatmentPa, Authentication authentication) {
        User user = userService.userInDb(1L);

        String response = treatmentFileService.isValidParans(paramTreatmentPa, user, false);

        if(!response.equals("OK")) {
            return ResponseEntity.badRequest().body(response);
        }

        RuleTreatmentPa ruleTreatmentPa = ruleTreatmentPaService.create(paramTreatmentPa);

        treatmentFileService.addAndSaveRulePa(ruleTreatmentPa, user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/edit/pa/{id}")
    public ResponseEntity<Object> editTreatmentPa(@PathVariable Long id, @RequestBody @Valid ParamTreatmentPa paramTreatmentPa, Authentication authentication) {
        User user = userService.userInDb(1L);

        String response = treatmentFileService.isValidParans(paramTreatmentPa, user, true);

        if(!response.equals("OK")) {
            return ResponseEntity.badRequest().body(response);
        }

        treatmentFileService.editAndSaveRulePa(paramTreatmentPa, id, user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/play/pa/{id}")
    public ResponseEntity<Object> playTreatmentPa(@PathVariable Long id, @RequestBody @Valid ParamDateBpa paramDateBpa, Authentication authentication) {
        User user = userService.userInDb(1L);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramDateBpa.getDateBpa()), user);

        if(bpaOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();
            RuleTreatmentPa ruleTreatmentPa = ruleTreatmentPaService.get(id);

            List<Bpac> bpacList = new ArrayList<>();
            if(ruleTreatmentPa.isExecuteBpac()) bpacList = bpacService.getBpacList(bpa);

            List<Bpai> bpaiList = new ArrayList<>();
            if(ruleTreatmentPa.isExecuteBpai()) bpaiList = bpaiService.getBpaiList(bpa);

            int count = treatmentFileService.executeRulePa(bpacList, bpaiList, ruleTreatmentPa);

            bpacService.save(bpacList);
            bpaiService.save(bpaiList);

            return ResponseEntity.ok(count);
        }


        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/delete/pa/{id}")
    public ResponseEntity<Object> deleteRulePa(@PathVariable Long id, Authentication authentication) {
        User user = userService.userInDb(1L);

        if(treatmentFileService.removeAndSaveRulePa(id, user) != null) ruleTreatmentPaService.delete(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/execute/file/{id}")
    public ResponseEntity<Object> updateExecuteFile(@PathVariable Long id, @RequestBody @Valid ParamUpdateExecuteFile paramUpdateExecuteFile, Authentication authentication) {

        RuleTreatmentPa ruleTreatmentPa = ruleTreatmentPaService.get(id);
        ruleTreatmentPaService.updateAndSave(ruleTreatmentPa, paramUpdateExecuteFile);

        return ResponseEntity.ok().build();
    }

    // PA AND CBO

    @GetMapping("/get/pa/cbo")
    public ResponseEntity<TreatmentPaCboDTO> getTreatmentPaCbo(Authentication authentication) {
        User user = userService.userInDb(1L);

        return ResponseEntity.ok(new TreatmentPaCboDTO(user.getTreatmentFile()));
    }

    @PostMapping("/play/pa/cbo/{id}")
    public ResponseEntity<Object> playTreatmentPaCbo(@PathVariable Long id, @RequestBody @Valid ParamDateBpa paramDateBpa, Authentication authentication) {
        User user = userService.userInDb(1L);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramDateBpa.getDateBpa()), user);

        if(bpaOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();
            RuleTreatmentPaCbo ruleTreatmentPaCbo = ruleTreatmentPaCboService.get(id);

            List<Bpac> bpacList = new ArrayList<>();
            if(ruleTreatmentPaCbo.isExecuteBpac()) bpacList = bpacService.getBpacList(bpa);

            List<Bpai> bpaiList = new ArrayList<>();
            if(ruleTreatmentPaCbo.isExecuteBpai()) bpaiList = bpaiService.getBpaiList(bpa);

            int count = treatmentFileService.executeRulePaCbo(bpacList, bpaiList, ruleTreatmentPaCbo);

            bpacService.save(bpacList);
            bpaiService.save(bpaiList);

            return ResponseEntity.ok(count);
        }


        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/create/pa/cbo")
    public ResponseEntity<Object> createTreatmentPa(@RequestBody @Valid ParamTreatmentPaCbo paramTreatmentPaCbo, Authentication authentication) {
        User user = userService.userInDb(1L);

        String response = treatmentFileService.isValidParans(paramTreatmentPaCbo, user, false);

        if(!response.equals("OK")) {
            return ResponseEntity.badRequest().body(response);
        }

        RuleTreatmentPaCbo ruleTreatmentPaCbo = ruleTreatmentPaCboService.create(paramTreatmentPaCbo);

        treatmentFileService.addRulePaCbo(ruleTreatmentPaCbo, user);

        userService.save(user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/edit/pa/cbo/{id}")
    public ResponseEntity<Object> editTreatmentPa(@PathVariable Long id, @RequestBody @Valid ParamTreatmentPaCbo paramTreatmentPaCbo, Authentication authentication) {
        User user = userService.userInDb(1L);

        String response = treatmentFileService.isValidParans(paramTreatmentPaCbo, user, true);

        if(!response.equals("OK")) {
            return ResponseEntity.badRequest().body(response);
        }

        treatmentFileService.editAndSaveRulePaCbo(paramTreatmentPaCbo, id, user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete/pa/cbo/{id}")
    public ResponseEntity<Object> deleteRulePaCbo(@PathVariable Long id, Authentication authentication) {
        User user = userService.userInDb(1L);

        if(treatmentFileService.removeAndSaveRulePaCbo(id, user)) ruleTreatmentPaCboService.delete(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/execute/pa/cbo/file/{id}")
    public ResponseEntity<Object> updateExecutePaCboFile(@PathVariable Long id, @RequestBody @Valid ParamUpdateExecuteFile paramUpdateExecuteFile, Authentication authentication) {

        RuleTreatmentPaCbo ruleTreatmentPaCbo = ruleTreatmentPaCboService.get(id);
        ruleTreatmentPaCboService.updateAndSave(ruleTreatmentPaCbo, paramUpdateExecuteFile);

        return ResponseEntity.ok().build();
    }

    // DELETE PA

    @GetMapping("/get/pa/delete")
    public ResponseEntity<TreatmentPaDeleteDTO> getTreatmentPaDelete(Authentication authentication) {
        User user = userService.userInDb(1L);

        return ResponseEntity.ok(new TreatmentPaDeleteDTO(user.getTreatmentFile()));
    }

    @PostMapping("/create/pa/delete")
    public ResponseEntity<Object> createTreatmentPaDelete(@RequestBody @Valid ParamTreatmentPaDelete paramTreatmentPaDelete, Authentication authentication) {
        User user = userService.userInDb(1L);

        String response = treatmentFileService.isValidParans(paramTreatmentPaDelete, user, false);

        if(!response.equals("OK")) {
            return ResponseEntity.badRequest().body(response);
        }

        RuleTreatmentPaDelete ruleTreatmentPaDelete = ruleTreatmentPaDeleteService.create(paramTreatmentPaDelete);

        treatmentFileService.addAndSaveRulePaDelete(ruleTreatmentPaDelete, user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/play/pa/delete/{id}")
    public ResponseEntity<Object> playTreatmentPaDelete(@PathVariable Long id, @RequestBody @Valid ParamDateBpa paramDateBpa, Authentication authentication) {
        User user = userService.userInDb(1L);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramDateBpa.getDateBpa()), user);

        if(bpaOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();
            RuleTreatmentPaDelete ruleTreatmentPaDelete = ruleTreatmentPaDeleteService.get(id);

            List<Bpac> bpacList = new ArrayList<>();
            if(ruleTreatmentPaDelete.isExecuteBpac()) bpacList = bpacService.getBpacList(bpa);

            List<Bpai> bpaiList = new ArrayList<>();
            if(ruleTreatmentPaDelete.isExecuteBpai()) bpaiList = bpaiService.getBpaiList(bpa);

            int count = treatmentFileService.executeRulePaDelete(bpacList, bpaiList, ruleTreatmentPaDelete, user);

            bpacService.save(bpacList);
            bpaiService.save(bpaiList);

            return ResponseEntity.ok(count);
        }


        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/edit/pa/delete/{id}")
    public ResponseEntity<Object> editTreatmentPaDelete(@PathVariable Long id, @RequestBody @Valid ParamTreatmentPaDelete paramTreatmentPaDelete, Authentication authentication) {
        User user = userService.userInDb(1L);

        String response = treatmentFileService.isValidParans(paramTreatmentPaDelete, user, true);

        if(!response.equals("OK")) {
            return ResponseEntity.badRequest().body(response);
        }

        treatmentFileService.editAndSaveRulePaDelete(paramTreatmentPaDelete, id, user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/execute/pa/file/{id}")
    public ResponseEntity<Object> updateExecuteFilePa(@PathVariable Long id, @RequestBody @Valid ParamUpdateExecuteFile paramUpdateExecuteFile, Authentication authentication) {

        RuleTreatmentPaDelete ruleTreatmentPaDelete = ruleTreatmentPaDeleteService.get(id);
        ruleTreatmentPaDeleteService.updateAndSave(ruleTreatmentPaDelete, paramUpdateExecuteFile);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/pa/delete/{id}")
    public ResponseEntity<Object> deleteRulePaDelete(@PathVariable Long id, Authentication authentication) {
        User user = userService.userInDb(1L);

        if(treatmentFileService.removeAndSaveRulePaDelete(id, user)) ruleTreatmentPaDeleteService.delete(id);

        return ResponseEntity.ok().build();
    }

    // CEP

    @GetMapping("/get/cep")
    public ResponseEntity<TreatmentPaDeleteDTO> getCep(Authentication authentication) {
        User user = userService.userInDb(1L);
//        DatesSigtap datesSigtap = datesSigtapService.get(user);
//        Optional<LinkCep> linkCepOptional;
//        if(datesSigtap.isDateProcedureAuto()) {
//            linkCepOptional = linkCepService.get(user);
//        } else {
//            linkCepOptional = linkCepService.get(datesSigtap.getDateCep(), user);
//        }
//        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramInconsistency.getDateBPA()), user);
//
//        if(bpaOptional.isPresent()) {
//            Bpa bpa = bpaOptional.get();
//
//            List<Bpai> bpaiListDB = bpaiService.getBpaiList(bpa);
//
//            EncryptionService.decryptBpaiCep(bpaiListDB);
//
//            List<ErrorCEPsDTO> errorsCEPs = cepService.verifyErrors(bpaiListDB);
//        }

        return ResponseEntity.ok(new TreatmentPaDeleteDTO(user.getTreatmentFile()));
    }
}
