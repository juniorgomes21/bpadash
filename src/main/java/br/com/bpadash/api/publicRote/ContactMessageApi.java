package br.com.bpadash.api.publicRote;

import br.com.bpadash.model.ContactMessage;
import br.com.bpadash.params.ContactMessageParam;
import br.com.bpadash.services.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/contact/message")
public class ContactMessageApi {

    @Autowired
    private ContactMessageService contactMessageService;

    @PostMapping
    public ResponseEntity<Object> createMessage(@RequestBody @Valid ContactMessageParam contactMessageParam) {

        contactMessageService.save(new ContactMessage(contactMessageParam));

        return ResponseEntity.ok().build();
    }
}
