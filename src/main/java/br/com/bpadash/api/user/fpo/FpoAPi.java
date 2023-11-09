package br.com.bpadash.api.user.fpo;

import br.com.bpadash.model.Fpo;
import br.com.bpadash.services.bpa.ScannerFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/fpo")
public class FpoAPi {

    @Autowired
    private ScannerFile scannerFile;

    @PostMapping("/create")
    public ResponseEntity<List<Fpo>> createFpo(@RequestPart("file") MultipartFile file, Authentication authentication) {

        List<Fpo> fpoList = scannerFile.createFpo(file);

        return ResponseEntity.ok(fpoList);
    }
}
