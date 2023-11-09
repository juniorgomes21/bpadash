package br.com.bpadash.api.adm.cep;

import br.com.bpadash.services.bpa.ScannerFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/adm/cep")
public class CepApi {


//    @Autowired
//    private CepRepository cepRepository;
//
//    @Autowired
//    private CepService cepService;

    @Autowired
    private ScannerFile scannerFile;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createCeps(@RequestPart("file") MultipartFile file) {

//        scannerFile.createCeps(file);

        return ResponseEntity.ok().build();
    }
}
