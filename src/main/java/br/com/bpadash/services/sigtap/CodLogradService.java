package br.com.bpadash.services.sigtap;

import br.com.bpadash.model.user.CodLograd;
import br.com.bpadash.repository.CodLogradRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CodLogradService {

    @Autowired
    private CodLogradRepository codLogradRepository;

    public List<CodLograd> get() {
        return codLogradRepository.findAll();
    }

    public String get(String lougrad) {

        Optional<CodLograd> codsOptional = codLogradRepository.findFirstByKeyCodContaining(lougrad);

        return codsOptional.isPresent() ? codsOptional.get().getCod() : "000";
    }

//    @PostMapping(value = "/ping", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Object> ping(@RequestPart("file") MultipartFile file) throws IOException {
//
//        InputStream inputStream = file.getInputStream();
//        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//
//        String line;
//        String cod = "";
//        String key = "";
//        List<CodLograd> codLograds = new ArrayList<>();
//        while ((line = br.readLine()) != null) {
//            if(line.startsWith("    <td>")) {
//                // Encontre as posições iniciais e finais das tags <td>
//                int startIndex = line.indexOf("<td>") + 4;
//                int endIndex = line.indexOf("</td>");
//
//                // Verifique se as tags <td> foram encontradas
//                if (endIndex != -1) {
//                    // Extraia o valor entre as tags <td>
//                    String value = line.substring(startIndex, endIndex);
//
//                    if(cod.equals("")) {
//                        cod = value;
//                    } else {
//                        key = value;
//                    }
//
//                    if(!cod.equals("") && !key.equals("")) {
//                        codLograds.add(new CodLograd(cod, key));
//                        cod = "";
//                        key = "";
//                    }
//                }
//            }
//        }
//
//        codLogradRepository.saveAll(codLograds);
//
//        return ResponseEntity.ok(codLograds);
//    }
}
