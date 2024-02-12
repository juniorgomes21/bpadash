package br.com.bpadash.api.user.graphics;

import br.com.bpadash.model.sigtap.Fpo;
import br.com.bpadash.model.user.User;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.fpo.FpoService;
import br.com.bpadash.services.fpo.LinkFpoService;
import br.com.bpadash.services.professional.LinkProfessionalsService;
import br.com.bpadash.services.professional.ProfessionalService;
import br.com.bpadash.services.user.StorageService;
import br.com.bpadash.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/graphics/storage")
public class StorageGraphicsApi {

    @Autowired
    private UserService userService;
    @Autowired
    private BpaService bpaService;
    @Autowired
    private LinkFpoService linkFpoService;
    @Autowired
    private LinkProfessionalsService linkProfessionalsService;
    @Autowired
    private StorageService storageService;

    @RequestMapping("/files")
    public ResponseEntity<Object> storageGraphics(Authentication authentication) {
        User user = userService.get(authentication);


        Long sizeByteFpo = linkFpoService.calculateByte(user);
        Long sizeByteProfe = linkProfessionalsService.calculateByte(user);
        Long sizeByteBpa = bpaService.calculateByte(user);

        Map<String, Object> contagemFiles = new HashMap<>();

        contagemFiles.put("sizeByteBpa", StorageService.formatBytes(sizeByteBpa));
        contagemFiles.put("sizeByteFpo", StorageService.formatBytes(sizeByteFpo));
        contagemFiles.put("sizeByteProfe", StorageService.formatBytes(sizeByteProfe));

        List<Double> percents = userService.caculatePercentStorage(user, sizeByteBpa, sizeByteFpo, sizeByteProfe);

        contagemFiles.put("percents", percents);


        return ResponseEntity.ok(contagemFiles);
    }
}
