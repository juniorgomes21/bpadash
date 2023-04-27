package br.com.bpadash.api.user.bpa;

import br.com.bpadash.dto.ErrorResponseDTO;
import br.com.bpadash.model.Bpac;
import br.com.bpadash.services.bpa.BpacService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.bpa.ScannerFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/dash/bpac")
public class BpacApi {

    @Autowired
    private ScannerFile scannerFile;

    @Autowired
    private BpaiService bpaiService;

    @Autowired
    private BpacService bpacService;


    /**
     * Lê um arquivo BPAC e salva no banco de dados.
     * @param file
     * @return
     */
    @PostMapping( value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createBpac(@RequestBody MultipartFile file) {
        try {
            if (!file.getOriginalFilename().endsWith(".txt")) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO("O arquivo enviado não é um arquivo de texto válido."));
            }

            List<Bpac> bpaiList;
            try {
                bpaiList = scannerFile.bpacCreate(file);
                if (bpaiList != null) {
                    bpacService.save(bpaiList);
                }
            } catch (StringIndexOutOfBoundsException e) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO("A estrutura do arquivo está incorreta o erro se encontra em " + e.getMessage()));
            }

            return ResponseEntity.ok().body(bpaiList);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO());
        }
    }
}
