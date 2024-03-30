package br.com.bpadash.services;

import br.com.bpadash.model.ContactMessage;
import br.com.bpadash.repository.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactMessageService {

    @Autowired
    private ContactMessageRepository contactMessageRepository;


    public void save(ContactMessage contactMessage) {
        contactMessageRepository.save(contactMessage);
    }
}
