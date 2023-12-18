package br.com.bpadash.api;

import br.com.bpadash.model.*;
import br.com.bpadash.model.treatment.TreatmentFile;
import br.com.bpadash.repository.UserRepository;
import br.com.bpadash.repository.bpa.BpaRepository;
import br.com.bpadash.repository.bpa.BpacRepository;
import br.com.bpadash.repository.bpa.BpaiRepository;
import br.com.bpadash.repository.bpa.TitleBpaRepository;
import br.com.bpadash.repository.professional.ProfessionalCompleteRepository;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/aux")
public class auxApi {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TitleBpaRepository titleBpaRepository;

    @Autowired
    private BpaRepository bpaRepository;

    @Autowired
    private BpaiRepository bpaiRepository;

    @Autowired
    private BpacRepository bpacRepository;

    @Autowired
    private ProfessionalCompleteRepository professionalCompleteRepository;

    @PostMapping("/delete/bpai")
    public ResponseEntity<Object> deleteBpai() {

        bpaiRepository.deleteAll();

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete/all")
    public ResponseEntity<Object> deleteAll() {

        titleBpaRepository.deleteAll();
        bpaiRepository.deleteAll();
        bpacRepository.deleteAll();
        bpaRepository.deleteAll();

        return ResponseEntity.ok().build();
    }

    @PostMapping("/ping")
    public ResponseEntity<Object> ping() {
        User user = userRepository.getById(1L);
        Bpa bpa = bpaRepository.getById(4L);

        List<Bpac> bpacList = new ArrayList<>();
        for (int i = 0; i <= 400; i++) {

        }

        return ResponseEntity.ok("pong");
    }

    @PostMapping("/cripto/idade")
    public ResponseEntity<Object> crp() {

        User user = userRepository.getById(1L);
        LocalDate date = Utilities.formatDate("2023-11-1");

        Bpa bpa = bpaRepository.findByDateAndUser(date, user).get();

        List<Bpai> bpaiList = bpaiRepository.findByBpa(bpa);

        for (Bpai bpai: bpaiList) {
            bpai.setIdade(EncryptionService.encrypt(bpai.getIdade()));
        }

        bpaiRepository.saveAll(bpaiList);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/dcripto/dtnasc")
    public ResponseEntity<Object> dcrp() {

        User user = userRepository.getById(1L);
        LocalDate date = Utilities.formatDate("2023-11-1");

        Bpa bpa = bpaRepository.findByDateAndUser(date, user).get();

        List<Bpai> bpaiList = bpaiRepository.findByBpa(bpa);

        for (Bpai bpai: bpaiList) {
            bpai.setDtnasc(EncryptionService.decrypt(bpai.getDtnasc()));
        }

        bpaiRepository.saveAll(bpaiList);

        return ResponseEntity.ok().build();
    }
}
