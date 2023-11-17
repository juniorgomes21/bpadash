package br.com.bpadash.services.fpo;

import br.com.bpadash.errorValidation.ErrorValidationDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.Fpo;
import br.com.bpadash.model.LinkFpo;
import br.com.bpadash.model.User;
import br.com.bpadash.repository.fpo.FpoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FpoService {

    @Autowired
    private FpoRepository fpoRepository;

    public Fpo create(LinkFpo linkFpo, String pa, String line , int lineNumber, List<ErrorsFile> errorsFileList) {
        List<ErrorValidationDTO> errors = new ArrayList<>();

        String description = line.substring(14, 73).trim();

        String quantOrcada = line.substring(85, 93).replace(".", "").trim();
        if(!quantOrcada.isEmpty() && !quantOrcada.matches("^\\d+$")) {
            errors.add(errorValidation("QUANTIDADE ORÇADA", quantOrcada));
        }

        String valueUnit = line.substring(93, 104).replace(".", "").replace(",", ".").trim();
        if(!valueUnit.isEmpty() && !valueUnit.matches("^[0-9]+(\\.[0-9]+)?$")) {
            errors.add(errorValidation("VALOR UNITÁRIO", valueUnit));
        }

        String valueOrcado = line.substring(104, 119).replace(".", "").replace(",", ".").trim();
        if(!valueOrcado.isEmpty() && !valueOrcado.matches("^[0-9]+(\\.[0-9]+)?$")) {
            errors.add(errorValidation("VALOR ORÇADO", valueOrcado));
        }

        String quantProd = line.substring(119, 127).replace(".", "").trim();
        if(!quantProd.isEmpty() && !quantProd.matches("^\\d+$")) {
            errors.add(errorValidation("QUANTIDADE PRODUZIDO", quantProd));
        }

        String valueProd = line.substring(127, 142).replace(".", "").replace(",", ".").trim();
        if(!valueProd.isEmpty() && !valueProd.matches("^[0-9]+(\\.[0-9]+)?$")) {
            errors.add(errorValidation("VALOR PRODUZIDO", valueProd));
        }

        String quantApro = line.substring(143, 150).replace(".", "").trim();
        if(!quantApro.isEmpty() && !quantApro.matches("^\\d+$")) {
            errors.add(errorValidation("QUANTIDADE APROVADO", quantApro));
        }

        String valueApro = line.substring(150, 165).replace(".", "").replace(",", ".").trim();
        if(!valueApro.isEmpty() && !valueApro.matches("^[0-9]+(\\.[0-9]+)?$")) {
            errors.add(errorValidation("VALOR APROVADO", valueApro));
        }

        if(!errors.isEmpty()) {
            errorsFileList.add(new ErrorsFile(String.valueOf(lineNumber), errors));

            return null;
        }

        return new Fpo(linkFpo, pa, description, quantOrcada, valueUnit, valueOrcado, quantProd, valueProd, quantApro, valueApro);
    }

    public Optional<Fpo> get(LocalDate date, User user) {

        return null;
    }

    public void save(Fpo fpo) {
        fpoRepository.save(fpo);
    }

    public void save(List<Fpo> fpoList) {
        fpoRepository.saveAll(fpoList);
    }

    @Transactional
    public void deleteAll() {
        fpoRepository.deleteAll();
    }

    private ErrorValidationDTO errorValidation(String field, String value) {
        return new ErrorValidationDTO(field, "O valor (" + value + ") não corresponde a um número válido.");
    }
}
