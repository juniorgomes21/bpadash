package br.com.bpadash.api.user.bpa;

import br.com.bpadash.dto.ErrorResponseDTO;
import br.com.bpadash.model.Bpac;
import br.com.bpadash.model.Bpai;
import br.com.bpadash.repository.bpa.BpacRepository;
import br.com.bpadash.services.bpa.BpacService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.bpa.ScannerFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/dash/bpac")
public class BpacApi {

    @Autowired
    private BpacRepository bpacRepository;

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

    /**
     * Carrega do banco de dados todos os registros de BPAC.
     * @return
     */
    @GetMapping("/get/all")
    public ResponseEntity<Object> getBpai() {
        List<Bpac> bpac = bpacRepository.findAll();

        return ResponseEntity.ok(bpac);
    }

    /**
     * Carrega do banco de dados um registro BPAC baseado no seu ID.
     * @param idBpai
     * @return
     */
    @GetMapping("/get/{idBpai}")
    public ResponseEntity<Object> getBpai(@PathVariable Long idBpai) {
        Bpac bpac = bpacRepository.findById(idBpai).get();

        return ResponseEntity.ok(bpac);
    }
}
