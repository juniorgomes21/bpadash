package br.com.bpadash.services.sigtap;

import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.sigtap.LinkOccupation;
import br.com.bpadash.model.sigtap.Occupation;
import br.com.bpadash.projections.CodProcedureProjection;
import br.com.bpadash.repository.sigtap.OccupationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class OccupationService {

    @Autowired
    private LinkOccupationService linkOccupationService;
    @Autowired
    private OccupationRepository occupationRepository;

    public Occupation create(String line, int lineNumber, LinkOccupation linkOccupation, List<ErrorsFile> errorsFiles) {
        String codProcedimento = "";
        String codOcupacao = "";
        String dtCompetencia = "";

        try {
            codProcedimento = line.substring(0, 9);
            codOcupacao = line.substring(9, 16);
            dtCompetencia = line.substring(16, 22);
        } catch (StringIndexOutOfBoundsException e) {
            ErrorsFile error = new ErrorsFile("STRUCTURE INVALID", String.valueOf(lineNumber));

            errorsFiles.add(error);
        }

        return new Occupation(codProcedimento, codOcupacao, dtCompetencia, linkOccupation);
    }

    public Occupation save(Occupation occupation) {
        return occupationRepository.save(occupation);
    }

    public List<Occupation> save(List<Occupation> occupationList) {
        return occupationRepository.saveAll(occupationList);
    }

    public boolean isValidFile(MultipartFile file) {

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));

            String line;
            int lineNumber = 1;
            while (lineNumber < 11) {
                line = br.readLine();
                if(!(line.length() == 22)) {
                    return false;
                }

                lineNumber++;
            }

            return true;

        } catch (IOException e) {
            return false;
        }
    }

    public void addLink(List<Occupation> occupationList , LinkOccupation linkOccupation) {
        for (Occupation occupation: occupationList) {
            occupation.setLinkOccupation(linkOccupation);
        }
    }

    public List<CodProcedureProjection> get(LinkOccupation linkOccupation) {
        return occupationRepository.findByLinkOccupation(linkOccupation, CodProcedureProjection.class);
    }
}
