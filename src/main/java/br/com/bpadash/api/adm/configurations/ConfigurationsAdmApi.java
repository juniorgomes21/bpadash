package br.com.bpadash.api.adm.configurations;

import br.com.bpadash.errorValidation.ErrorResponseDTO;
import br.com.bpadash.model.Administrator;
import br.com.bpadash.model.bpa.Address;
import br.com.bpadash.model.user.*;
import br.com.bpadash.params.adm.ParamNewAdm;
import br.com.bpadash.params.user.ParamNewUser;
import br.com.bpadash.services.adm.AdmServices;
import br.com.bpadash.services.adm.PackageUserService;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import br.com.bpadash.services.sigtap.AddressService;
import br.com.bpadash.services.sigtap.CepService;
import br.com.bpadash.services.user.EmployeeService;
import br.com.bpadash.services.user.SessionUserService;
import br.com.bpadash.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    private EmployeeService employeeService;
    @Autowired
    private PackageUserService packageUserService;
    @Autowired
    private SessionUserService sessionUserService;


    @PostMapping("/package")
    public ResponseEntity<Object> createAdm() {
        try {
            String packageName = "bronze";
            Long sizeStorage  = 262144000L;
            int numberRules  = 25;
            int session = 3; // TODO mudar para 5

            PackageUser packageUser = new PackageUser(packageName, sizeStorage, numberRules, session);

            String packageName1 = "gold";
            Long sizeStorage1  = 524288000L;
            int numberRules1  = 50;
            int sessionGold = 10;


            PackageUser packageUser1 = new PackageUser(packageName1, sizeStorage1, numberRules1, sessionGold);

            String packageName2 = "platinum";
            Long sizeStorage2  = 1073741824L;
            int numberRules2  = 100;
            int sessionPlatinum = 15;

            PackageUser packageUser2 = new PackageUser(packageName2, sizeStorage2, numberRules2, sessionPlatinum);

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

            EnCryptionAESService.encryptAddressUser(addressUser);

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
    public ResponseEntity<Object> createUser(@RequestBody @Valid ParamNewUser paramNewUser, Authentication authentication) {
        try {
            Administrator administrator = admServices.logged(authentication);

            if(admServices.testPassword(administrator, paramNewUser.getPasswordAdm())) {

                Address address = addressService.verifyCep(paramNewUser.getCep());

                if(address == null) return ResponseEntity.badRequest().body(new ErrorResponseDTO("CEP INVALID"));

                AddressUser addressUser = new AddressUser(address);

                EnCryptionAESService.encryptAddressUser(addressUser);

                PackageUser packageUser = packageUserService.get(paramNewUser.getPackageUser());

                User user = userService.createUser(paramNewUser, addressUser, packageUser);

                if(userService.existe(user)) return ResponseEntity.badRequest().body("USER ALREADY REGISTERED");

                userService.save(user);

                return ResponseEntity.ok().build();
            }

            return ResponseEntity.badRequest().body("INCORRECT PASSWORD");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ErrorResponseDTO());
        }
    }

}
