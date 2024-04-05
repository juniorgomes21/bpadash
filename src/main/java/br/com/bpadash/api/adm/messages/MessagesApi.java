package br.com.bpadash.api.adm.messages;

import br.com.bpadash.dto.adm.ContactMessageDTO;
import br.com.bpadash.model.ContactMessage;
import br.com.bpadash.services.adm.MessagesService;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/adm/messages")
public class MessagesApi {

    @Autowired
    private MessagesService messagesService;

    @GetMapping
    public ResponseEntity<List<ContactMessageDTO>> getMessages() {

        List<ContactMessage> contactMessageList = messagesService.get();

        List<ContactMessageDTO> contactMessageDTOs = new ArrayList<>();
        contactMessageList.forEach( contact -> {
            contactMessageDTOs.add(new ContactMessageDTO(contact));
        });

        return ResponseEntity.ok(contactMessageDTOs);
    }

    @PostMapping("/conclude/{id}")
    public ResponseEntity<Object> concludeMessage(@PathVariable Long id) {
        Optional<ContactMessage> contactMessageOptional = messagesService.get(id);

        if(contactMessageOptional.isPresent()) {
            ContactMessage contactMessage = contactMessageOptional.get();

            contactMessage.setConclude(true);

            messagesService.save(contactMessage);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().body("NOT FOUND MESSAGE");
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> deleteMessage(@PathVariable Long id) {

        messagesService.delete(id);

        return ResponseEntity.ok().build();
    }
}
