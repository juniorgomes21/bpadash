package br.com.bpadash.api;

import br.com.bpadash.repository.bpa.BpaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aux")
public class auxApi {

    @Autowired
    private BpaiRepository bpaiRepository;

    @PostMapping("/delete/bpai")
    public ResponseEntity<Object> deleteBpai() {

        bpaiRepository.deleteAll();

        return ResponseEntity.ok().build();
    }
}
