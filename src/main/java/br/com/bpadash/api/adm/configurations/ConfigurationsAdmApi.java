package br.com.bpadash.api.adm.configurations;

import br.com.bpadash.errorValidation.ErrorResponseDTO;
import br.com.bpadash.params.ParamNewAdm;
import br.com.bpadash.services.adm.AdmServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/ADM/configurations")
public class ConfigurationsAdmApi {

    @Autowired
    private AdmServices admServices;

    @PostMapping("/create")
    public ResponseEntity<Object> createAdm(@Valid @RequestBody ParamNewAdm paramNewAdm) {
        try {
            admServices.createAdministrador(paramNewAdm);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO());
        }
    }
}
