package br.com.bpadash.services.adm;

import br.com.bpadash.model.ContactMessage;
import br.com.bpadash.repository.ContactMessageRepository;
import br.com.bpadash.services.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MessagesService {

    @Autowired
    private ContactMessageRepository contactMessageRepository;
    @Autowired
    private ContactMessageService contactMessageService;


    public List<ContactMessage> get() {

        return contactMessageRepository.findByConclude(false, Sort.by(Sort.Direction.ASC, "date"));
    }

    public Optional<ContactMessage> get(Long id) {
        return contactMessageRepository.findById(id);
    }

    public ContactMessage save(ContactMessage contactMessage) {
        return contactMessageRepository.save(contactMessage);
    }

    @Transactional
    public void delete(Long id) {
        contactMessageRepository.deleteById(id);
    }
}
