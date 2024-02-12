package br.com.bpadash.api.user.graphics;

import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.bpa.ManagerBpa;
import br.com.bpadash.model.user.User;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.user.UserService;
import br.com.bpadash.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/graphics/dtaten")
public class DtatenGraphicsApi {

    @Autowired
    private UserService userService;
    @Autowired
    private BpaiService bpaiService;
    @Autowired
    private BpaService bpaService;

    @GetMapping("/{year}")
    public ResponseEntity<Object> used(@PathVariable Integer year, Authentication authentication) {
        User user = userService.get(authentication);

        List<Bpa> bpaList = bpaService.getForYear(user, year);

//        if(!bpaList.isEmpty()) {
//            Bpa bpa = bpaList.get(0);
//
//            if(bpa.getManagerBpa().isCalculateDtaten()) {
//
//                Map<String, Integer> porcentagensGrupos = bpaService.calculateDtaten(bpa, null);
//            } else  {
//
//            }
//
//
//            return ResponseEntity.ok(porcentagensGrupos);
//        }

        return ResponseEntity.badRequest().body("NOT FOUND");
    }

}
