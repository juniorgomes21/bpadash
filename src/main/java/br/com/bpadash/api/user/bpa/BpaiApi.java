package br.com.bpadash.api.user.bpa;

import br.com.bpadash.dto.ErrorResponseDTO;
import br.com.bpadash.model.Bpai;
import br.com.bpadash.repository.bpa.BpaiRepository;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.bpa.ScannerFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/dash/bpai")
public class BpaiApi {

    @Autowired
    private BpaiRepository bpaiRepository;

    @Autowired
    private ScannerFile scannerFile;

    @Autowired
    private BpaiService bpaiService;

    /**
     * Lê um arquivo BPAI e salva no banco de dados.
     * @param file
     * @return
     */
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> bpaCreate(@RequestParam("file") MultipartFile file) {
        try {
            // Verifica se o arquivo é um arquivo de texto (.txt)
            if (!file.getOriginalFilename().endsWith(".txt")) {
                return ResponseEntity.badRequest().body("O arquivo enviado não é um arquivo de texto válido.");
            }

            List<Bpai> bpaiList;
            try {
                bpaiList = scannerFile.bpaiCreate(file);
                if (bpaiList != null) {
                    bpaiService.save(bpaiList);
                }
            } catch (StringIndexOutOfBoundsException e) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO("A estrutura do arquivo está incorreta o erro se encontra em " + e.getMessage()));
            }

//            String response = bpaiService.isValidBpai(bpaiList);

            return ResponseEntity.ok().body(bpaiList);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ErrorResponseDTO());
        }
    }


    /**
     * Carrega do banco de dados uma entidade BPAI baseado no seu ID.
     * @param idBpai
     * @return
     */
    @GetMapping("/get/{idBpai}")
    public ResponseEntity<Object> getBpai(@PathVariable Long idBpai) {
        Bpai bpai = bpaiRepository.findById(idBpai).get();

        return ResponseEntity.ok(bpai);
    }
}
