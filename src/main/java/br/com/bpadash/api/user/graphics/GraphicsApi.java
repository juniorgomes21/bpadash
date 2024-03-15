package br.com.bpadash.api.user.graphics;


import br.com.bpadash.dto.graphics.SexGraphicsDTO;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.user.User;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.graphics.GraphicsService;
import br.com.bpadash.services.user.UserService;
import br.com.bpadash.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/graphics")
public class GraphicsApi {
    @Autowired
    private UserService userService;
    @Autowired
    private BpaService bpaService;
    @Autowired
    private BpaiService bpaiService;
    @Autowired
    private GraphicsService graphicsService;


    // AGE

    @GetMapping("/age/{dateBpa}/{cacheId}")
    public ResponseEntity<Object> getForAge(@PathVariable String dateBpa, @PathVariable String cacheId, Authentication authentication) {
        User user = userService.userLogged(authentication);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(dateBpa), user);

        if(bpaOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();

            SexGraphicsDTO sexGraphicsDTO = graphicsService.dataAge(bpa, cacheId);

            return ResponseEntity.ok(sexGraphicsDTO);
        }

        return ResponseEntity.badRequest().body("NOT FOUND");
    }

}
