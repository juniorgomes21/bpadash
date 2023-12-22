package br.com.bpadash.services.sigtap;

import br.com.bpadash.dto.sigtap.ErrorAgeProcedureDTO;
import br.com.bpadash.dto.sigtap.ErrorPaDTO;
import br.com.bpadash.dto.sigtap.ErrorProcedureDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.*;
import br.com.bpadash.projections.ProcedurePaProjection;
import br.com.bpadash.repository.sigtap.ProcedureRepository;
import br.com.bpadash.services.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProcedureService {

    @Autowired
    private ProcedureRepository procedureRepository;

    public Procedure create(String line, int numberLine, List<ErrorsFile> errorsFiles, LinkProcedure linkProcedure) {
        String codProcedimento = "";
        String naProcedimento ="";
        String tpComplexidade = "";
        String tpSexo = "";
        String qtMaximaExecucao = "";
        String qtDiasPermanencia = "";
        String qtPontos = "";
        String vlIdadeMinima = "";
        String vlIdadeMaxima = "";
        String vlSh = "";
        String vlSa = "";
        String vlSp = "";
        String codFinanceiro = "";
        String codRubrica = "";
        String qtTempoPermanencia = "";
        String dtCompetencia = "";
        try {
            codProcedimento = line.substring(0, 10);
            naProcedimento = line.substring(10, 260);
            tpComplexidade = line.substring(260, 261);
            tpSexo = line.substring(261, 262);
            qtMaximaExecucao = line.substring(262, 266);
            qtDiasPermanencia = line.substring(266, 270);
            qtPontos = line.substring(270, 274);
            vlIdadeMinima = line.substring(274, 278);
            vlIdadeMaxima = line.substring(278, 282);
            vlSh = line.substring(282, 292);
            vlSa = line.substring(292, 302);
            vlSp = line.substring(302, 312);
            codFinanceiro = line.substring(312, 314);
            codRubrica = line.substring(314, 320);
            qtTempoPermanencia = line.substring(320, 324);
            dtCompetencia = line.substring(324, 330);
        } catch (StringIndexOutOfBoundsException e) {
            ErrorsFile error = new ErrorsFile("STRUCTURE INVALID", String.valueOf(numberLine));

            errorsFiles.add(error);
        }

        return new Procedure(
            codProcedimento,
            naProcedimento,
            tpComplexidade,
            tpSexo,
            qtMaximaExecucao,
            qtDiasPermanencia,
            qtPontos,
            vlIdadeMinima,
            vlIdadeMaxima,
            vlSh,
            vlSa,
            vlSp,
            codFinanceiro,
            codRubrica,
            qtTempoPermanencia,
            dtCompetencia,
            linkProcedure
        );
    }

    public Procedure save(Procedure procedure) {
        return procedureRepository.save(procedure);
    }

    public List<Procedure> save(List<Procedure> procedureList) {
        return procedureRepository.saveAll(procedureList);
    }

    public boolean isValidFile(MultipartFile file) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));

            String line;
            int lineNumber = 1;
            while (lineNumber < 11) {
                line = br.readLine();
                if(line.length() != 330) {
                    return false;
                }

                lineNumber++;
            }

            return true;

        } catch (IOException e) {
            return false;
        }
    }

    public List<Procedure> get(LinkProcedure linkProcedure) {
        return procedureRepository.findByLinkProcedure(linkProcedure);
    }

    public List<ErrorPaDTO> verifyErrorsPaBpac(List<Bpac> bpacListDB , List<Procedure> procedureList) {
        List<ErrorPaDTO> errors = new ArrayList<>();

        Set<String> paList = procedureList.stream().map(Procedure::getCodProcedimento).collect(Collectors.toSet());

        for (Bpac bpac: bpacListDB) {

            String pa = bpac.getPa();

            if (!paList.contains(pa)) {
                errors.add(new ErrorPaDTO(bpac.getId(), bpac.getFlh(), bpac.getSeq(), "NOT EXIST PA IN OCCUPATION", pa, "OCCUPATION"));
            }
        }

        return errors;
    }

    public List<ErrorProcedureDTO> verifyErrorsPaAndSexBpai(List<Bpai> bpaiListDB, List<Procedure> procedureList) {

        List<ErrorProcedureDTO> errors = new ArrayList<>();
        List<String> sexos = new ArrayList<>(Arrays.asList("N", "I"));

        for (Bpai bpai: bpaiListDB) {

            String pa = bpai.getPa();
            String flh = bpai.getFlh();
            String seq = bpai.getSeq();

            Optional<Procedure> procedureOptional = procedureList.stream().filter(procedure -> procedure.getCodProcedimento().equals(pa)).findFirst();

            if (procedureOptional.isPresent()) {
                Procedure procedure = procedureOptional.get();

                String sexo = procedure.getTpSexo();

                if(!sexos.contains(sexo)) {
                    if(!sexo.equals(bpai.getSexo())) {
                       errors.add(new ErrorProcedureDTO(bpai.getId(), flh, seq, "ERROR SEX", pa, bpai.getSexo(), sexo, "SEX"));
                    }
                }

            }
        }

        return errors;
    }

    public List<ErrorAgeProcedureDTO> verifyErrorsAge(List<Bpai> bpaiListDB , List<Procedure> procedureList) {
        List<ErrorAgeProcedureDTO> errors = new ArrayList<>();

        for (Bpai bpai: bpaiListDB) {

            String pa = bpai.getPa();
            int age = Integer.parseInt(bpai.getIdade());

            Optional<Procedure> procedureOptional = procedureList.stream().filter(procedure -> procedure.getCodProcedimento().equals(pa)).findFirst();

            if (procedureOptional.isPresent()) {
                Procedure procedure = procedureOptional.get();

                int ageMin = Integer.parseInt(procedure.getVlIdadeMinima()) / 12;
                int ageMax = Integer.parseInt(procedure.getVlIdadeMaxima()) / 12;
                String name = EncryptionService.decrypt(bpai.getNmpac());

                boolean ageMinB = age < ageMin;
                if(ageMinB || age > ageMax) {
                    errors.add(new ErrorAgeProcedureDTO(
                            bpai.getId(),
                            "AGE INVALID",
                            bpai.getFlh(),
                            bpai.getSeq(),
                            name,
                            age,
                            ageMin,
                            ageMax,
                            ageMinB,
                            EncryptionService.decrypt(bpai.getDtnasc())
                    ));
                }
            }
        }

        return errors;
    }
}
