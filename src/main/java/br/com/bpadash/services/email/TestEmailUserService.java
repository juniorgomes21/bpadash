package br.com.bpadash.services.email;

import br.com.bpadash.model.email.TestEmailUser;
import br.com.bpadash.repository.email.TestEmailUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestEmailUserService {

    @Autowired
    private TestEmailUserRepository testEmailUserRepository;


    public TestEmailUser save(TestEmailUser testEmailUser) {
        return testEmailUserRepository.save(testEmailUser);
    }

    public List<TestEmailUser> getAll() {
        return testEmailUserRepository.findAll();
    }
}
