package br.com.bpadash.api.adm.configurations;

import br.com.bpadash.errorValidation.ErrorResponseDTO;
import br.com.bpadash.model.Address;
import br.com.bpadash.params.adm.ParamNewAdm;
import br.com.bpadash.params.user.ParamNewUser;
import br.com.bpadash.services.adm.AdmServices;
import br.com.bpadash.services.sigtap.AddressService;
import br.com.bpadash.services.sigtap.CepService;
import br.com.bpadash.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/adm/configurations")
public class ConfigurationsAdmApi {

    @Autowired
    private UserService userService;
    @Autowired
    private CepService cepService;
    @Autowired
    private AdmServices admServices;
    @Autowired
    private AddressService addressService;

    @PostMapping("/create")
    public ResponseEntity<Object> createAdm(@Valid @RequestBody ParamNewAdm paramNewAdm) {
        try {
            Address address = addressService.verifyCep(paramNewAdm.getCep());

            if(address == null) return ResponseEntity.badRequest().body(new ErrorResponseDTO("CEP INVALID"));

            admServices.createAdministrador(paramNewAdm, address);

            return ResponseEntity.ok().build();
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(new ErrorResponseDTO());
        }
    }

    @PostMapping("/create/user")
    public ResponseEntity<Object> createUser(@RequestBody @Valid ParamNewUser paramNewUser) {
        try {
            Address address = addressService.verifyCep(paramNewUser.getCep());

            if(address == null) return ResponseEntity.badRequest().body(new ErrorResponseDTO("CEP INVALID"));

            userService.createUser(paramNewUser, address);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ErrorResponseDTO());
        }
    }
}
