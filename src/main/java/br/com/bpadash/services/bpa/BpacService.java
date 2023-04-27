package br.com.bpadash.services.bpa;

import br.com.bpadash.model.Bpac;
import br.com.bpadash.repository.bpa.BpacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BpacService {

    @Autowired
    private BpacRepository bpacRepository;


    public void save(List<Bpac> bpacList) {
        bpacRepository.saveAll(bpacList);
    }
}
