package br.com.bpadash.services.fpo;

import br.com.bpadash.errorValidation.ErrorValidationDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.sigtap.Fpo;
import br.com.bpadash.model.sigtap.LinkFpo;
import br.com.bpadash.model.sigtap.ProfessionalComplete;
import br.com.bpadash.model.user.User;
import br.com.bpadash.repository.fpo.FpoRepository;
import br.com.bpadash.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FpoService {

    @Autowired
    private FpoRepository fpoRepository;

    public Fpo get(LinkFpo linkFpo, String pa) {
        return fpoRepository.findByLinkFpoAndPa(linkFpo, pa).orElse(null);
    }

    public List<Fpo> get(LinkFpo linkFpo) {

        return fpoRepository.findByLinkFpo(linkFpo);
    }

    public Fpo create(LinkFpo linkFpo, String pa, String line, int lineNumber, List<ErrorsFile> errorsFiles) {
        List<ErrorValidationDTO> errors = new ArrayList<>();

        String description = "";
        String quantOrcada = "";
        String valueUnit = "";
        String valueOrcado = "";
        String quantProd = "";
        String valueProd = "";
        String quantApro = "";
        String valueApro = "";

        try {
            description = line.substring(14, 73).trim();

            quantOrcada = line.substring(85, 93).replace(".", "").trim();
            if(!quantOrcada.isEmpty() && !quantOrcada.matches("^\\d+$")) {
                errors.add(errorValidation("QUANTIDADE ORÇADA", quantOrcada));
            }

            valueUnit = line.substring(93, 104).replace(".", "").replace(",", ".").trim();
            if(!valueUnit.isEmpty() && !valueUnit.matches("^[0-9]+(\\.[0-9]+)?$")) {
                errors.add(errorValidation("VALOR UNITÁRIO", valueUnit));
            }

            valueOrcado = line.substring(104, 119).replace(".", "").replace(",", ".").trim();
            if(!valueOrcado.isEmpty() && !valueOrcado.matches("^[0-9]+(\\.[0-9]+)?$")) {
                errors.add(errorValidation("VALOR ORÇADO", valueOrcado));
            }

            quantProd = line.substring(119, 127).replace(".", "").trim();
            if(!quantProd.isEmpty() && !quantProd.matches("^\\d+$")) {
                errors.add(errorValidation("QUANTIDADE PRODUZIDO", quantProd));
            }

            valueProd = line.substring(127, 142).replace(".", "").replace(",", ".").trim();
            if(!valueProd.isEmpty() && !valueProd.matches("^[0-9]+(\\.[0-9]+)?$")) {
                errors.add(errorValidation("VALOR PRODUZIDO", valueProd));
            }

            quantApro = line.substring(143, 150).replace(".", "").trim();
            if(!quantApro.isEmpty() && !quantApro.matches("^\\d+$")) {
                errors.add(errorValidation("QUANTIDADE APROVADO", quantApro));
            }

            valueApro = line.substring(150, 165).replace(".", "").replace(",", ".").trim();
            if(!valueApro.isEmpty() && !valueApro.matches("^[0-9]+(\\.[0-9]+)?$")) {
                errors.add(errorValidation("VALOR APROVADO", valueApro));
            }

            if(!errors.isEmpty()) {
                errorsFiles.add(new ErrorsFile(String.valueOf(lineNumber), errors));
            }
        } catch (StringIndexOutOfBoundsException e) {
            ErrorsFile error = new ErrorsFile("STRUCTURE INVALID", String.valueOf(lineNumber));

            errorsFiles.add(error);
        }

        return new Fpo(
                linkFpo,
                pa,
                description,
                quantOrcada,
                valueUnit,
                valueOrcado,
                quantProd,
                valueProd,
                quantApro,
                valueApro
        );
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

    public boolean isValidFile(MultipartFile file) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String fraseEspecifica = "PROGRAMACAO FISICO";
            String line;
            int lineNumber = 1;
            while (lineNumber < 15) {
                line = br.readLine();
                if(line.toLowerCase().contains(fraseEspecifica.toLowerCase())) {
                    return true;
                }

                lineNumber++;
            }

            return false;

        } catch (IOException e) {
            return false;
        }
    }

    public void delete(LinkFpo link) {
        fpoRepository.deleteByLinkFpo(link);
    }
}
