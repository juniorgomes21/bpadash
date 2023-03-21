package br.com.bpadash.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ADM")
public class IsValidToken {

    @GetMapping("/test/token")
    public ResponseEntity<Boolean> isValidToken() {

        return ResponseEntity.ok(true);
    }
}
