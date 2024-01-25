package br.com.bpadash.api.user.treatment;

import br.com.bpadash.model.bpa.Address;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.user.AddressUser;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.bpa.ParamDateBpa;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.sigtap.AddressService;
import br.com.bpadash.services.treatment.RuleTreatmentPaService;
import br.com.bpadash.services.treatment.TreatmentFileService;
import br.com.bpadash.services.user.StorageService;
import br.com.bpadash.services.user.UserService;
import br.com.bpadash.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/treatment/address")
public class AddressTreatment {

    @Autowired
    private BpaService bpaService;
    @Autowired
    private BpaiService bpaiService;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private TreatmentFileService treatmentFileService;
    @Autowired
    private RuleTreatmentPaService ruleTreatmentPaService;


    /**
     * Faz a Substituição do endereço do paciênte pelo endereço do utilizador.
     * @param paramDateBpa
     * @param authentication
     * @return
     */
    @Transactional
    @PostMapping("/execute")
    public ResponseEntity<Object> playTreatment(@RequestBody @Valid ParamDateBpa paramDateBpa, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramDateBpa.getDateBpa()), user);

        if(bpaOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();
            int count;

            List<Bpai> bpaiList = bpaiService.get(bpa);

            Long bytesOld = storageService.quantityBytes(null, null, bpaiList);

            EncryptionService.decryptAddressUser(user.getAddressUser());

            count = addressService.executeTreatmentCepBlank(bpaiList, user.getAddressUser());

            Long bytesNew = storageService.quantityBytes(null, null, bpaiList);

            userService.updateStorageBpaiAndSave(user, bytesOld, bytesNew);

            bpaiService.save(bpaiList);

            return ResponseEntity.ok(count);
        }

        return ResponseEntity.badRequest().build();
    }


    /**
     * Faz a uma chamada de api buscando o CEP, caso o mesmo não tenha sido encontrado no banco de dados local, e
     * substitui o endereço do paciênte pelo endereço.
     * @param paramDateBpa
     * @param authentication
     * @return
     */
    @Transactional
    @PostMapping("/execute/cep")
    public ResponseEntity<Object> playTreatmentCep(@RequestBody @Valid ParamDateBpa paramDateBpa, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramDateBpa.getDateBpa()), user);

        if(bpaOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();
            int count;

            List<Bpai> bpaiList = bpaiService.get(bpa);

            Long bytesOld = storageService.quantityBytes(null, null, bpaiList);

            EncryptionService.decryptBpaiCep(bpaiList);

            count = addressService.executeTreatment(bpaiList);

            EncryptionService.encryptBpaiAddress(bpaiList);

            Long bytesNew = storageService.quantityBytes(null, null, bpaiList);

            userService.updateStorageBpaiAndSave(user, bytesOld, bytesNew);

            bpaiService.save(bpaiList);

            return ResponseEntity.ok(count);
        }

        return ResponseEntity.badRequest().build();
    }

}
