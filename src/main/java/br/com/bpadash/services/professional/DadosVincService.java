package br.com.bpadash.services.professional;

import br.com.bpadash.model.sigtap.DadosVinc;
import br.com.bpadash.repository.professional.DadosVincRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DadosVincService {

    @Autowired
    private DadosVincRepository dadosVincRepository;


    public DadosVinc save(DadosVinc dadosVinc) {
        return dadosVincRepository.save(dadosVinc);
    }

    public List<DadosVinc> save(List<DadosVinc> list) {
        return dadosVincRepository.saveAll(list);
    }
}
