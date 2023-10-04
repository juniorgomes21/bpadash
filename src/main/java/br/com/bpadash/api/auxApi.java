package br.com.bpadash.api;

import br.com.bpadash.model.TitleBpa;
import br.com.bpadash.model.User;
import br.com.bpadash.repository.UserRepository;
import br.com.bpadash.repository.bpa.BpaRepository;
import br.com.bpadash.repository.bpa.BpacRepository;
import br.com.bpadash.repository.bpa.BpaiRepository;
import br.com.bpadash.repository.bpa.TitleBpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/delete/bpai")
    public ResponseEntity<Object> deleteBpai() {

        bpaiRepository.deleteAll();

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete/all")
    public ResponseEntity<Object> deleteAll() {
        User user = userRepository.findById(1L).get();
        user.getBpas().clear();
        userRepository.save(user);

        titleBpaRepository.deleteAll();
        bpaiRepository.deleteAll();
        bpacRepository.deleteAll();
        bpaRepository.deleteAll();

        return ResponseEntity.ok().build();
    }

    @PostMapping("/ping")
    public ResponseEntity<Object> ping() {

        return ResponseEntity.ok("pong");
    }
}
