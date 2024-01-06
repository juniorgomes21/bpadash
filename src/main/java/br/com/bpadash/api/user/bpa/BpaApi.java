package br.com.bpadash.api.user.bpa;

import br.com.bpadash.dto.DatesDTO;
import br.com.bpadash.dto.bpa.*;
import br.com.bpadash.dto.sigtap.*;
import br.com.bpadash.errorValidation.ErrorValidationDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.*;
import br.com.bpadash.params.bpa.ParamNewBpa;
import br.com.bpadash.params.sigtap.ParamInconsistency;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.bpa.*;
import br.com.bpadash.services.fpo.FpoService;
import br.com.bpadash.services.fpo.LinkFpoService;
import br.com.bpadash.services.professional.LinkProfessionalsService;
import br.com.bpadash.services.professional.ProfessionalService;
import br.com.bpadash.services.scanner.ScannerFile;
import br.com.bpadash.services.sigtap.*;
import br.com.bpadash.services.user.UserService;
import br.com.bpadash.utilities.Utilities;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bpa")
public class BpaApi {

    @Autowired
    private ScannerFile scannerFile;
    @Autowired
    private BpaService bpaService;
    @Autowired
    private TitleBpaService titleBpaService;
    @Autowired
    private UserService userService;
    @Autowired
    private FpoService fpoService;
    @Autowired
    private LinkFpoService linkFpoService;
    @Autowired
    private LinkCepService linkCepService;
    @Autowired
    private LinkProcedureService linkProcedureService;
    @Autowired
    private LinkOccupationService linkOccupationService;
    @Autowired
    private LinkProfessionalsService linkProfessionalsService;
    @Autowired
    private ProfessionalService professionalService;
    @Autowired
    private BpacService bpacService;
    @Autowired
    private BpaiService bpaiService;
    @Autowired
    private CepService cepService;
    @Autowired
    private ProcedureService procedureService;
    @Autowired
    private DatesSigtapService datesSigtapService;

    
    @GetMapping("/get/all")
    public ResponseEntity<List<BpaDTO>> getAll(Authentication authentication) {
        User user = userService.userInDb(1L);

        List<BpaDTO> bpaDTOList = bpaService.getAll(user);

        return ResponseEntity.ok(bpaDTOList);
    }

    @GetMapping("/get/{month}/{year}")
    public ResponseEntity<BpaDTO> getBpa(@PathVariable int month, @PathVariable int year, Authentication authentication) {
        User user = userService.userInDb(1L);
        Bpa bpa = bpaService.getForDate(month, year, user);

        if(bpa == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(new BpaDTO(bpa));
    }

    @GetMapping("/generateFile/{identifier}")
    public ResponseEntity<byte[]> generateTextFile(@PathVariable String identifier, Authentication authentication) {
        try {
            User user = userService.userInDb(1L);
            Bpa bpa = bpaService.get(identifier, user);

            StringBuilder fileContent = bpaService.createFile(bpa);

            byte[] fileBytes = fileContent.toString().getBytes();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "arquivo.txt");

            return ResponseEntity.ok().headers(headers).body(fileBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> deleteMany(@RequestBody List<String> identifiers, Authentication authentication) {
        User user = userService.userInDb(1L);

        identifiers.forEach(identifier -> {
            Bpa bpa = bpaService.get(identifier, user);
            bpaService.delete(bpa, user);
        });


        return ResponseEntity.ok().build();
    }


    /**
     * Se idade em (bpaI) é inferior a 1900 ou superior à data atual
     * @param paramInconsistency
     * @return
     */
    @PostMapping("/inconsistency/date/age")
    public ResponseEntity<Object> inconsistencyDates(@RequestBody ParamInconsistency paramInconsistency) {

        User user = userService.userInDb(1L);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramInconsistency.getDateBPA()), user);

        if(bpaOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();

            List<Bpai> bpaiListDB = bpaiService.getBpaiList(bpa);

            EncryptionService.decryptBpaiDtNasc(bpaiListDB);

            List<ErrorAgeDatesDTO> errorAgeDatesDTOS = bpaService.verifyErrorsDate(bpaiListDB);

            return ResponseEntity.ok(errorAgeDatesDTOS);
        }

        return ResponseEntity.badRequest().body("NOT DATE EXISTS BPA");
    }


    /**
     * Idade em (bpaI) está dentro do intervalo de idade máxima e mínima em tb_procedimento
     * @param paramInconsistency
     * @return
     */
    @PostMapping("/inconsistency/date/procedure")
    public ResponseEntity<Object> inconsistencyDatesProcedure(@RequestBody ParamInconsistency paramInconsistency) {

        User user = userService.userInDb(1L);
        DatesSigtap datesSigtap = datesSigtapService.get(user);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramInconsistency.getDateBPA()), user);

        Optional<LinkProcedure> linkProcedureOptional;
        if(datesSigtap.isDateProcedureAuto()) {
            linkProcedureOptional = linkProcedureService.get();
        } else {
            linkProcedureOptional = linkProcedureService.get(datesSigtap.getDateProcedure());
        }

        if(bpaOptional.isPresent() && linkProcedureOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();
            LinkProcedure linkProcedure = linkProcedureOptional.get();

            List<Bpai> bpaiListDB = bpaiService.getBpaiList(bpa);

            EncryptionService.decryptBpaiIdade(bpaiListDB);

            List<ErrorAgeProcedureDTO> errosDates = procedureService.verifyErrorsAge(bpaiListDB, linkProcedure.getProcedureList());

            return ResponseEntity.ok(errosDates);

        }

        return ResponseEntity.badRequest().body(bpaOptional.isPresent() ? "NOT DATE EXISTS PROCEDURE" : "NOT DATE EXISTS BPA");
    }


    /**
     * Cep BPAI contido em (banco de ceps válidos).
     * @param paramInconsistency
     * @return
     */
    @PostMapping("/inconsistency/cep")
    public ResponseEntity<Object> inconsistencyCep(@RequestBody ParamInconsistency paramInconsistency) {
        User user = userService.userInDb(1L);
        DatesSigtap datesSigtap = datesSigtapService.get(user);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramInconsistency.getDateBPA()), user);

        Optional<LinkCep> linkCepOptional;
        if(datesSigtap.isDateProcedureAuto()) {
            linkCepOptional = linkCepService.get();
        } else {
            linkCepOptional = linkCepService.get(datesSigtap.getDateCep());
        }

        if(bpaOptional.isPresent() && linkCepOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();
            LinkCep linkCep = linkCepOptional.get();

            List<Bpai> bpaiListDB = bpaiService.getBpaiList(bpa);

            EncryptionService.decryptBpaiCep(bpaiListDB);

            List<ErrorCEPsInvalidsDTO> errorsCEPs = cepService.verifyErrors(bpaiListDB, linkCep);

            return ResponseEntity.ok(errorsCEPs);

        }

        return ResponseEntity.badRequest().body(bpaOptional.isPresent() ? "NOT DATE EXISTS CEP" : "NOT DATE EXISTS BPA");
    }


    /**
     * Endereço em branco em BPAI.
     * @param paramInconsistency
     * @return
     */
    @PostMapping("/inconsistency/cep/blank")
    public ResponseEntity<Object> inconsistencyCepBlank(@RequestBody ParamInconsistency paramInconsistency) {
        User user = userService.userInDb(1L);
        DatesSigtap datesSigtap = datesSigtapService.get(user);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramInconsistency.getDateBPA()), user);

        Optional<LinkCep> linkCepOptional;
        if(datesSigtap.isDateProcedureAuto()) {
            linkCepOptional = linkCepService.get();
        } else {
            linkCepOptional = linkCepService.get(datesSigtap.getDateCep());
        }

        if(bpaOptional.isPresent() && linkCepOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();
            LinkCep linkCep = linkCepOptional.get();

            List<Bpai> bpaiListDB = bpaiService.getBpaiList(bpa);

            EncryptionService.decryptBpaiAddress(bpaiListDB);

            List<ErrorCEPsInvalidsDTO> errorsCEPs = cepService.verifyErrorsBlank(bpaiListDB, linkCep);

            return ResponseEntity.ok(errorsCEPs);

        }

        return ResponseEntity.badRequest().body(bpaOptional.isPresent() ? "NOT DATE EXISTS CEP" : "NOT DATE EXISTS BPA");
    }


    /**
     * Quantidade de procedimentos em (bpaI) está permitido em tb_procedimento (quantidade máxima).
     * @param paramInconsistency
     * @return
     */
    @PostMapping("/inconsistency/qtServices")
    public ResponseEntity<Object> inconsistencyQtServices(@RequestBody ParamInconsistency paramInconsistency) {

        User user = userService.userInDb(1L);
        DatesSigtap datesSigtap = datesSigtapService.get(user);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramInconsistency.getDateBPA()), user);

        Optional<LinkProcedure> linkProcedureOptional;
        if(datesSigtap.isDateProcedureAuto()) {
            linkProcedureOptional = linkProcedureService.get();
        } else {
            linkProcedureOptional = linkProcedureService.get(datesSigtap.getDateProcedure());
        }

        if(bpaOptional.isPresent() && linkProcedureOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();
            LinkProcedure linkProcedure = linkProcedureOptional.get();

            List<Bpai> bpaiListDB = bpaiService.getBpaiList(bpa);

            List<ErrorQtMaxDTODTO> errors = bpaiService.verifyErrorsQtServices(linkProcedure.getProcedureList(), bpaiListDB);

            return ResponseEntity.ok(errors);
        }

        return ResponseEntity.badRequest().body(bpaOptional.isPresent() ? "NOT DATE EXISTS FPO" : "NOT DATE EXISTS BPA");
    }


    /**
     * Data atendimento em (bpaI) está dentro da compência (cmp) do mês do arquivo
     * @param paramInconsistency
     * @return
     */
    @PostMapping("/inconsistency/service")
    public ResponseEntity<Object> inconsistencyMonthBpa(@RequestBody ParamInconsistency paramInconsistency) {

        User user = userService.userInDb(1L);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramInconsistency.getDateBPA()), user);

        if(bpaOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();

            TitleBpa titleBpa = titleBpaService.get(bpa);
            List<Bpai> bpaiListDB = bpaiService.getBpaiList(bpa);

            List<ErrorDtAtendDTODTO> errors = bpaService.verifyErrorsDtAtend(bpaiListDB, titleBpa);

            return ResponseEntity.ok(errors);

        }

        return ResponseEntity.badRequest().body("NOT DATE EXISTS BPA");
    }


    /**
     * Raca em (bpaI) é diferente de ["01", "02", "03", "04", "05"]
     * @param paramInconsistency
     * @return
     */
    @PostMapping("/inconsistency/race")
    public ResponseEntity<Object> inconsistencyRace(@RequestBody ParamInconsistency paramInconsistency) {

        User user = userService.userInDb(1L);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramInconsistency.getDateBPA()), user);

        if(bpaOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();

            List<Bpai> bpaiListDB = bpaiService.getBpaiList(bpa);

            EncryptionService.decryptRace(bpaiListDB);

            List<ErrorRaceDTO> errors = bpaService.verifyErrorsRace(bpaiListDB);

            return ResponseEntity.ok(errors);

        }

        return ResponseEntity.badRequest().body("NOT DATE EXISTS BPA");
    }


    /**
     * CNSMED em (bpaI) está em Profissionais.XML (Arquivo XML extraído do SCNES)
     * @param paramInconsistency
     * @return
     */
    @PostMapping("/inconsistency/professionals")
    public ResponseEntity<Object> inconsistencyProfessionals(@RequestBody ParamInconsistency paramInconsistency) {

        User user = userService.userInDb(1L);
        DatesSigtap datesSigtap = datesSigtapService.get(user);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramInconsistency.getDateBPA()), user);

        Optional<LinkProfessionals> linkProfessionalsOptional;
        if (datesSigtap.isDateProfessionalsAuto()) {
            linkProfessionalsOptional = linkProfessionalsService.get();
        } else {
            linkProfessionalsOptional = linkProfessionalsService.get(datesSigtap.getDateProfessionals());
        }

        if(bpaOptional.isPresent() && linkProfessionalsOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();
            LinkProfessionals linkProfessionals = linkProfessionalsOptional.get();

            List<Bpai> bpaiListDB = bpaiService.getBpaiList(bpa);

            List<ProfessionalComplete> professionalCompleteList = linkProfessionals.getProfessionalCompleteList();

            EncryptionService.decryptProfessionalCns(professionalCompleteList);

            List<ErrorSigTapDTO> errors = professionalService.verifyErrors(bpaiListDB, professionalCompleteList);

            return ResponseEntity.ok(errors);

        }

        return ResponseEntity.badRequest().body(bpaOptional.isPresent() ? "NOT DATE EXISTS PROFESSIONALS" : "NOT DATE EXISTS BPA");
    }


    /**
     * Se PA de (bpaC e bpaI) estão em FPO.
     * @param paramInconsistency
     * @return
     */
    @PostMapping("/inconsistency/fpo")
    public ResponseEntity<Object> inconsistencyFpo(@RequestBody ParamInconsistency paramInconsistency)  {

        User user = userService.userInDb(1L);
        DatesSigtap datesSigtap = datesSigtapService.get(user);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramInconsistency.getDateBPA()), user);

        Optional<LinkFpo> linkFpoOptional;
        if(datesSigtap.isDateFpoAuto()) {
            linkFpoOptional = linkFpoService.get();
        } else {
            linkFpoOptional = linkFpoService.get(datesSigtap.getDateFpo());
        }

        Optional<LinkProcedure> linkProcedureOptional;
        if(datesSigtap.isDateProcedureAuto()) {
            linkProcedureOptional = linkProcedureService.get();
        } else {
            linkProcedureOptional = linkProcedureService.get(datesSigtap.getDateProcedure());
        }

        Optional<LinkOccupation> linkOccupationOptional;
        if(datesSigtap.isDateOccupationAuto()) {
            linkOccupationOptional = linkOccupationService.get();
        } else {
            linkOccupationOptional = linkOccupationService.get(datesSigtap.getDateOccupation());
        }

        if(bpaOptional.isPresent() && linkFpoOptional.isPresent() && linkProcedureOptional.isPresent() && linkOccupationOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();

            LinkFpo linkFpo = linkFpoOptional.get();
            LinkProcedure linkProcedure = linkProcedureOptional.get();
            LinkOccupation linkOccupation = linkOccupationOptional.get();

            List<Bpac> bpacListDB = bpacService.getBpacList(bpa);
            List<Bpai> bpaiListDB = bpaiService.getBpaiList(bpa);

            //Campo PA de (BPAC e BPAI) e campo SEXO de BPAI estão em tb_procedimento
            Set<String> procedurePa = linkProcedure.getProcedureList().stream().map(Procedure::getCodProcedimento).collect(Collectors.toSet());
            //Se PA e CBO de (bpaC e bpaI) estão em tb_procedimento_ocupação
            Set<String> occupationPa = linkOccupation.getOccupationList().stream().map(Occupation::getCodProcedimento).collect(Collectors.toSet());

            List<ErrorPaDTO> errorsPaBpacDTOS = bpacService.verifyErrorsPa(linkFpo.getFpoList(), procedurePa, occupationPa, bpacListDB);
            List<ErrorPaDTO> errorsPaBpaiDTOS = bpaiService.verifyErrorsPa(linkFpo.getFpoList(), procedurePa, occupationPa, bpaiListDB);

            return ResponseEntity.ok(new InconsistencyPaDTO(errorsPaBpacDTOS, errorsPaBpaiDTOS));

        }

        return ResponseEntity.badRequest().body(bpaOptional.isPresent() ? "NOT DATE EXISTS FPO" : "NOT DATE EXISTS BPA");
    }


    /**
     * Campo PA de (BPAC e BPAI) e campo SEXO de BPAI estão em tb_procedimento
     * @param paramInconsistency
     * @return
     */
    @PostMapping("/inconsistency/procedure")
    public ResponseEntity<Object> inconsistencyProdution(@RequestBody ParamInconsistency paramInconsistency) {
        User user = userService.userInDb(1L);
        DatesSigtap datesSigtap = datesSigtapService.get(user);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramInconsistency.getDateBPA()), user);

        Optional<LinkProcedure> linkProcedureOptional;
        if(datesSigtap.isDateProcedureAuto()) {
            linkProcedureOptional = linkProcedureService.get();
        } else {
            linkProcedureOptional = linkProcedureService.get(datesSigtap.getDateProcedure());
        }

        if(bpaOptional.isPresent() && linkProcedureOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();
            LinkProcedure linkProcedure = linkProcedureOptional.get();

            List<Bpac> bpacListDB = bpacService.getBpacList(bpa);
            List<Bpai> bpaiListDB = bpaiService.getBpaiList(bpa);

            EncryptionService.decryptSex(bpaiListDB);

            List<ErrorPaDTO> errorsPaBpac = procedureService.verifyErrorsPaBpac(bpacListDB, linkProcedure.getProcedureList());
            List<ErrorProcedureDTO> errorsPaBpai = procedureService.verifyErrorsPaAndSexBpai(bpaiListDB, linkProcedure.getProcedureList());

            return ResponseEntity.ok(new InconsistencyProcedureDTO(errorsPaBpac, errorsPaBpai));
        }

        return ResponseEntity.badRequest().body(bpaOptional.isPresent() ? "NOT DATE EXISTS CEP" : "NOT DATE EXISTS BPA");
    }


    /**
     * Se PA e CBO de (bpaC e bpaI) estão em tb_procedimento_ocupação
     * @param paramInconsistency
     * @return
     */
    @PostMapping("/inconsistency/occupation")
    public ResponseEntity<Object> inconsistencyOccupation(@RequestBody ParamInconsistency paramInconsistency) {

        User user = userService.userInDb(1L);
        DatesSigtap datesSigtap = datesSigtapService.get(user);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramInconsistency.getDateBPA()), user);

        Optional<LinkOccupation> linkOccupationOptional;
        if(datesSigtap.isDateOccupationAuto()) {
            linkOccupationOptional = linkOccupationService.get();
        } else {
            linkOccupationOptional = linkOccupationService.get(datesSigtap.getDateOccupation());
        }

        if(bpaOptional.isPresent() && linkOccupationOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();
            LinkOccupation linkOccupation = linkOccupationOptional.get();

            List<Bpac> bpacListDB = bpacService.getBpacList(bpa);
            List<Bpai> bpaiListDB = bpaiService.getBpaiList(bpa);

            Set<String> occupationPa = linkOccupation.getOccupationList().stream()
                    .map(Occupation::getCodProcedimento)
                    .collect(Collectors.toSet());

            Set<String> occupationCBO = linkOccupation.getOccupationList().stream()
                    .map(Occupation::getCodOcupacao)
                    .collect(Collectors.toSet());

            //Verifica se PA de BPAC e BPAI existe no arquivo FPO.
            List<ErrorOccupationDTO> errorsOccupationBpacDTOS = bpacService.verifyErrorsOccupation(occupationPa, occupationCBO, bpacListDB);

            List<ErrorOccupationDTO> errorsOccupationBpaiDTOS = bpaiService.verifyErrorsOccupation(occupationPa, occupationCBO, bpaiListDB);


            return ResponseEntity.ok(new InconsistencyOccupationDTO(errorsOccupationBpacDTOS, errorsOccupationBpaiDTOS));

        }

        return ResponseEntity.badRequest().body("NOT DATE EXISTS FPO");
    }

    @PostMapping("/delete/{identifier}")
    public ResponseEntity<Object> deleteBPA(@PathVariable String identifier, Authentication authentication) {
        User user = userService.userInDb(1L);
        Bpa bpa = bpaService.get(identifier, user);

        bpaService.delete(bpa, user);

        return ResponseEntity.ok().build();
    }

    @PostMapping( value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ErrorsFile>> bpaCreate(@RequestPart("file") MultipartFile file, @RequestParam("paramNewBpa") String paramNewBpaJson, Authentication authentication) {
        try {
            StopWatch stopWatch = StopWatch.createStarted();

            User user = userService.userInDb(1L);

            ObjectMapper objectMapper = new ObjectMapper();
            ParamNewBpa paramNewBpa = objectMapper.readValue(paramNewBpaJson, ParamNewBpa.class);

            List<ErrorsFile> errorsFileList = new ArrayList<>();
            List<ErrorValidationDTO> errors = new ArrayList<>();


            String response = scannerFile.createBpa(file, user, paramNewBpa, errorsFileList, stopWatch);
            switch (response) {
                case "ERROR FILE" -> {
                    return ResponseEntity.badRequest().body(errorsFileList);
                }
                case "ERROR FORMAT DATE" -> {
                    errors.add(new ErrorValidationDTO("ERROR FORMAT DATE" , "Erro na formação da data do arquivo BPA. Por favor verifique a data no título do arquivo."));
                    errorsFileList.add(new ErrorsFile(String.valueOf(0) , errors));

                    return ResponseEntity.badRequest().body(errorsFileList);
                }
                case "NOT STORAGE" -> {
                    errors.add(new ErrorValidationDTO("NOT STORAGE" , "Espaço de armazenamento insuficiente."));
                    errorsFileList.add(new ErrorsFile(String.valueOf(0) , errors));

                    return ResponseEntity.badRequest().body(errorsFileList);
                }
                case "EXIST DATE" -> {
                    errors.add(new ErrorValidationDTO("EXIST DATE" , "A data do arquivo já existe."));
                    errorsFileList.add(new ErrorsFile(String.valueOf(0) , errors));

                    return ResponseEntity.badRequest().body(errorsFileList);
                }
            }

            return ResponseEntity.ok().build();

        } catch (StringIndexOutOfBoundsException e) {
            throw new StringIndexOutOfBoundsException("A estrutura do arquivo está incorreta o erro se encontra em " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("A estrutura do arquivo está incorreta: " + e.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/dates")
    public ResponseEntity<DatesDTO> dates(Authentication authentication) {
        User user = userService.userInDb(1L);

        DatesDTO datesDTO = bpaService.getDates(user);

        return ResponseEntity.ok(datesDTO);
    }
}
