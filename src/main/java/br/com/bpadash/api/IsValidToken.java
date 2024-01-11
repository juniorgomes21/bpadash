package br.com.bpadash.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/token")
public class IsValidToken {

    @GetMapping
    public ResponseEntity<Boolean> isValidToken() {

        return ResponseEntity.ok(true);
    }
}
