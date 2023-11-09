package br.com.bpadash.services.fpo;

import br.com.bpadash.model.Fpo;
import org.springframework.stereotype.Service;

@Service
public class FpoService {


    public Fpo create(String pa , String line , int lineNumber) {
        String description = line.substring(14, 73).trim();
        String quantOrcada = line.substring(85, 93).replace(".", "").trim();
        String valueUnit = line.substring(93, 104).replace(".", "").replace(",", ".").trim();
        String valueOrcado = line.substring(104, 119).replace(".", "").replace(",", ".").trim();
        String quantProd = line.substring(119, 127).replace(".", "").trim();
        String valueProd = line.substring(127, 142).replace(".", "").replace(",", ".").trim();
        String quantApro = line.substring(143, 150).replace(".", "").trim();
        String valueApro = line.substring(150, 165).replace(".", "").replace(",", ".").trim();

        return new Fpo(pa, description, quantOrcada, valueUnit, valueOrcado, quantProd, valueProd, quantApro, valueApro);
    }
}
