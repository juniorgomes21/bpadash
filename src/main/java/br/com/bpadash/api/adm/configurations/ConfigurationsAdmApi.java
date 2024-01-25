package br.com.bpadash.api.adm.configurations;

import br.com.bpadash.errorValidation.ErrorResponseDTO;
import br.com.bpadash.model.Administrator;
import br.com.bpadash.model.bpa.Address;
import br.com.bpadash.model.user.AddressUser;
import br.com.bpadash.model.user.PackageUser;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.adm.ParamNewAdm;
import br.com.bpadash.params.user.ParamNewUser;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.adm.AdmServices;
import br.com.bpadash.services.adm.PackageUserService;
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
    @Autowired
    private PackageUserService packageUserService;


    @PostMapping("/package")
    public ResponseEntity<Object> createAdm() {
        try {
            String packageName = "bronze";
            Long sizeStorage  = 262144000L;
            int numberRules  = 20;

            PackageUser packageUser = new PackageUser(packageName, sizeStorage, numberRules);

            String packageName1 = "gold";
            Long sizeStorage1  = 524288000L;
            int numberRules1  = 50;

            PackageUser packageUser1 = new PackageUser(packageName1, sizeStorage1, numberRules1);

            String packageName2 = "platinum";
            Long sizeStorage2  = 1073741824L;
            int numberRules2  = 80;

            PackageUser packageUser2 = new PackageUser(packageName2, sizeStorage2, numberRules2);

            packageUserService.save(packageUser);
            packageUserService.save(packageUser1);
            packageUserService.save(packageUser2);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ErrorResponseDTO());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createAdm(@Valid @RequestBody ParamNewAdm paramNewAdm) {
        try {
            Address address = addressService.verifyCep(paramNewAdm.getCep());

            if(address == null) return ResponseEntity.badRequest().body(new ErrorResponseDTO("CEP INVALID"));

            AddressUser addressUser = new AddressUser(address);

            EncryptionService.encryptAddressUser(addressUser);

            Administrator adm = admServices.createAdministrador(paramNewAdm, addressUser);

            if(admServices.existe(adm)) return ResponseEntity.badRequest().body("ADM ALREADY REGISTERED");

            admServices.save(adm);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ErrorResponseDTO());
        }
    }


    @PostMapping("/create/user")
    public ResponseEntity<Object> createUser(@RequestBody @Valid ParamNewUser paramNewUser) {
        try {
            Address address = addressService.verifyCep(paramNewUser.getCep());

            if(address == null) return ResponseEntity.badRequest().body(new ErrorResponseDTO("CEP INVALID"));

            AddressUser addressUser = new AddressUser(address);

            EncryptionService.encryptAddressUser(addressUser);

            User user = userService.createUser(paramNewUser, addressUser);

            if(userService.existe(user)) return ResponseEntity.badRequest().body("USER ALREADY REGISTERED");

            userService.save(user);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ErrorResponseDTO());
        }
    }
}
