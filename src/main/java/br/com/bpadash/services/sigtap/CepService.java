package br.com.bpadash.services.sigtap;

import br.com.bpadash.dto.sigtap.ErrorCEPsInvalidsDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.bpa.Address;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.sigtap.Cep;
import br.com.bpadash.model.sigtap.LinkCep;
import br.com.bpadash.projections.CepProjection;
import br.com.bpadash.repository.sigtap.CepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CepService {

    @Autowired
    private CepRepository cepRepository;


    public Cep create(String line, int numberLine, LinkCep linkCep, List<ErrorsFile> errorsFiles) {
        String cep = "";
        String es = "";
        String cod = "";

        try {
            cep = line.substring(0, 8);
            es = line.substring(9, 11);
            cod = line.substring(12 ,18);
        } catch (StringIndexOutOfBoundsException e) {
            ErrorsFile error = new ErrorsFile("STRUCTURE INVALID", String.valueOf(numberLine));

            errorsFiles.add(error);
        }

        return new Cep(cep, es, cod, linkCep);
    }

    public List<Cep> save(List<Cep> cepList) {
        return cepRepository.saveAll(cepList);
    }

    public boolean isValidFile(MultipartFile file) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));

            String line;
            int lineNumber = 1;
            while (lineNumber < 11) {
                line = br.readLine();
                if(line.length() != 18) {
                    return false;
                }

                lineNumber++;
            }

            return true;

        } catch (IOException e) {
            return false;
        }
    }

    public Address consult(String cep) {
        try {
            String url = "https://opencep.com/v1/" + cep;
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Address> response = restTemplate.exchange(url , HttpMethod.GET , null , new ParameterizedTypeReference<>() {});

            return response.getBody();

        } catch (Exception e) {
            return null;
        }
    }

    public List<ErrorCEPsInvalidsDTO> verifyErrors(List<Bpai> bpaiListDB, LinkCep linkCep) {
        List<ErrorCEPsInvalidsDTO> errorsCEPs = new ArrayList<>();

        Set<String> cepsSet = cepRepository.findByLinkCep(linkCep)
                .stream()
                .map(CepProjection::getCep).collect(Collectors.toSet());

        bpaiListDB.forEach( bpai -> {
            String cep = bpai.getCepPcnte();

            if(cep.isBlank()) {
                errorsCEPs.add(new ErrorCEPsInvalidsDTO(bpai.getId(), bpai.getFlh(), bpai.getSeq(), "CEP IS BLANK", ""));
            } else if (!cepsSet.contains(cep)) {
                errorsCEPs.add(new ErrorCEPsInvalidsDTO(bpai.getId(), bpai.getFlh(), bpai.getSeq(), "CEP INVALID", cep));
            }
        });

        return errorsCEPs;
    }

    private boolean existCEP(String cep , LinkCep linkCep) {
        return cepRepository.existsByCepAndLinkCep(cep, linkCep);
    }

    public List<String> getAllSort(LinkCep linkCep) {
        return cepRepository.findByLinkCep(linkCep)
                .stream()
                .map(CepProjection::getCep)
                .sorted(Comparator.comparingInt(Integer::parseInt))
                .toList();
    }

    public List<ErrorCEPsInvalidsDTO> verifyErrorsBlank(List<Bpai> bpaiListDB, LinkCep linkCep) {
        List<ErrorCEPsInvalidsDTO> errorsCEPs = new ArrayList<>();

        Set<String> cepsSet = cepRepository.findByLinkCep(linkCep)
                .stream()
                .map(CepProjection::getCep).collect(Collectors.toSet());

        bpaiListDB.forEach( bpai -> {
            String cep = bpai.getCepPcnte();
            String lograd = bpai.getLogradPcnte();
            String complement = bpai.getComplPcnte();
            String endPcnte = bpai.getEndPcnte();
            String bairroPcnte = bpai.getBairroPcnte();

            if (cepsSet.contains(cep)) {
                if(lograd.trim().isEmpty() || complement.trim().isEmpty() || endPcnte.trim().isEmpty() || bairroPcnte.trim().isEmpty()) {
                    errorsCEPs.add(new ErrorCEPsInvalidsDTO(bpai.getId(), bpai.getFlh(), bpai.getSeq(), "ADDRESS INVÁLID" , cep));
                }
            }
        });

        return errorsCEPs;
    }
}
