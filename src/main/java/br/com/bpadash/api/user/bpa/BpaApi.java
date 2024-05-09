package br.com.bpadash.api.user.bpa;

import br.com.bpadash.dto.DatesDTO;
import br.com.bpadash.dto.bpa.*;
import br.com.bpadash.dto.sigtap.*;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpac;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.bpa.TitleBpa;
import br.com.bpadash.model.enumModel.ActionEmployee;
import br.com.bpadash.model.enumModel.ActionType;
import br.com.bpadash.model.sigtap.*;
import br.com.bpadash.model.user.Employee;
import br.com.bpadash.model.user.User;
import br.com.bpadash.params.bpa.ParamNewBpa;
import br.com.bpadash.params.sigtap.ParamInconsistency;
import br.com.bpadash.services.bpa.*;
import br.com.bpadash.services.cache.CacheService;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import br.com.bpadash.services.fpo.LinkFpoService;
import br.com.bpadash.services.professional.LinkProfessionalsService;
import br.com.bpadash.services.professional.ProfessionalService;
import br.com.bpadash.services.scanner.ScannerFile;
import br.com.bpadash.services.sigtap.*;
import br.com.bpadash.services.user.EmployeeService;
import br.com.bpadash.services.user.StockHistoryService;
import br.com.bpadash.services.user.UserService;
import br.com.bpadash.utilities.Utilities;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
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
    private EmployeeService employeeService;
    @Autowired
    private StockHistoryService stockHistoryService;


    @GetMapping("/get/all")
    public ResponseEntity<List<BpaDTO>> getAll(Authentication authentication) {
        User user = userService.get(authentication);

        List<Bpa> bpaList = bpaService.get(user);

        List<BpaDTO> bpaDTOList = userService.getBpaDates(bpaList);

        return ResponseEntity.ok(bpaDTOList);
    }

    @GetMapping("/timeline")
    public ResponseEntity<List<TimeLineDTO>> timeLineUser(Authentication authentication) {
        User user = userService.get(authentication);

        return ResponseEntity.ok(userService.timeLine(user));
    }

    @PostMapping("/recalculate/{date}")
    public ResponseEntity<Object> timeLineUser(@PathVariable String date, Authentication authentication) {
        User user = userService.userLogged(authentication);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(date), user);

        if(bpaOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();

            bpaService.calculateAll(user, bpa, null, null, true);

            bpaService.save(bpa);

            return ResponseEntity.ok().build();

        }

        return ResponseEntity.badRequest().body("NOT FOUND BPA");
    }

    @GetMapping("/get/pa/cbo/{month}/{year}")
    public ResponseEntity<Page<BpaiDTO>> getBpaPaCbo(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable pageable,
            @PathVariable int month,
            @PathVariable int year,
            Authentication authentication
    ) {
        User user = userService.get(authentication);
        Bpa bpa = bpaService.getForDate(month, year, user);

        if(bpa == null) {
            return ResponseEntity.badRequest().body(null);
        }

        List<Bpac> bpacList = bpacService.get(bpa);
        List<Bpai> bpaiList = bpaiService.get(bpa);

        List<String> paCbo = new ArrayList<>();

//        bpacList.forEach( bpac -> {
//            List<String> pa
//        });

        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{month}/{year}")
    public ResponseEntity<BpaDTO> getBpa(@PathVariable int month, @PathVariable int year, Authentication authentication) {
        User user = userService.get(authentication);
        Bpa bpa = bpaService.getForDate(month, year, user);

        if(bpa == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(new BpaDTO(bpa));
    }


    @GetMapping("/invoicing/{dateBpa}")
    public ResponseEntity<Object> calculateInvoicing(@PathVariable String dateBpa, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(dateBpa), user);

        if(bpaOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();

//            if(bpa.getManagerBpa().isCalculateInvoicing()) {
//                Optional<LinkFpo> linkFpoOptional = linkFpoService.verify(user);
//
//                if (linkFpoOptional.isEmpty()) return ResponseEntity.badRequest().body("NOT FOUND FPO");
//
//                LinkFpo linkFpo = linkFpoOptional.get();
//
//                BigDecimal valeuTotalInvoicing = bpaService.calculateInvoicing(bpa, linkFpo.getFpoList(), null, null, user, true);
//
//                return ResponseEntity.ok(valeuTotalInvoicing);
//            }

            return ResponseEntity.ok(bpa.getManagerBpa().getInvoicing());
        }

        return ResponseEntity.badRequest().body("NOT FOUND");
    }

    @GetMapping("/invoicing/year/{year}")
    public ResponseEntity<Object> calculateInvoicingYear(@PathVariable int year, Authentication authentication) {
        User user = userService.get(authentication);

        List<Bpa> bpaList = bpaService.getForYear(user, year);

        boolean calculate = bpaList.stream().anyMatch(bpa -> bpa.getManagerBpa().isCalculateInvoicing());

        BigDecimal totalInvoicing = BigDecimal.ZERO;
        if(calculate) {

            Optional<LinkFpo> linkFpoOptional = linkFpoService.verify(user);

            if(linkFpoOptional.isPresent()) {
                LinkFpo linkFpo = linkFpoOptional.get();

                for (Bpa bpa: bpaList) {
                    if(bpa.getManagerBpa().isCalculateInvoicing()) {

                        BigDecimal valeuTotalInvoicing = bpaService.calculateInvoicing(bpa, linkFpo.getFpoList(), null, null, user, true);

                        totalInvoicing = totalInvoicing.add(valeuTotalInvoicing);

                        bpaService.saveManagerInvoicing(bpa, valeuTotalInvoicing);

                    } else {
                        totalInvoicing = totalInvoicing.add(bpa.getManagerBpa().getInvoicing());
                    }

                }

            } else {
                return ResponseEntity.badRequest().body("NOT FOUND FPO");
            }

        } else {
            for (Bpa bpa: bpaList) {
                totalInvoicing = totalInvoicing.add(bpa.getManagerBpa().getInvoicing());
            }
        }

        return ResponseEntity.ok(totalInvoicing);
    }

    @GetMapping("/generateFile/{identifier}/{employeeKey}")
    public ResponseEntity<Object> generateTextFile(@PathVariable String identifier, @PathVariable String employeeKey, Authentication authentication) {
        try {
            User user = userService.get(authentication);

            Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

            if(employeeOptional.isPresent() && employeeOptional.get().getPermissions().isDownloadBpa()) {
                Bpa bpa = bpaService.get(identifier, user);

                StringBuilder fileContent = bpaService.createFile(bpa);

                byte[] fileBytes = fileContent.toString().getBytes();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", "arquivo.txt");

                stockHistoryService.register(ActionEmployee.DOWNLOAD.getAction(), ActionType.DOWNLOAD.getAction(), bpa.getDate(), 0, user, employeeOptional.get());

                return ResponseEntity.ok().headers(headers).body(fileBytes);
            }

            return ResponseEntity.status(401).body("FORBIDDEN");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional
    @PostMapping("/delete/{employeeKey}")
    public ResponseEntity<Object> deleteMany(@PathVariable String employeeKey, @RequestBody List<String> identifiers, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

        if(employeeOptional.isPresent() && employeeOptional.get().getPermissions().isDeleteBpa()) {
            identifiers.forEach(identifier -> {
                Bpa bpa = bpaService.get(identifier, user);

                Long totalBytes = bpa.getFileSizeInBytes();

                bpaService.delete(bpa, user);

                userService.updateStorageAndSave(user, totalBytes, true);

                stockHistoryService.register(ActionEmployee.DELETE_BPA.getAction(), ActionType.DELETE.getAction(), bpa.getDate(), 0, user, employeeOptional.get());
            });


            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(401).body("FORBIDDEN");
    }

    @PostMapping( value = "/create/{employeeKey}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> bpaCreate(@PathVariable String employeeKey, @RequestPart("file") MultipartFile file, @RequestParam("paramNewBpa") String paramNewBpaJson, Authentication authentication) {
        try {
            StopWatch stopWatch = StopWatch.createStarted();

            User user = userService.get(authentication);
            Optional<Employee> employeeOptional = employeeService.get(user, employeeKey);

            if(employeeOptional.isPresent() && employeeOptional.get().getPermissions().isAddBpa()) {
                ObjectMapper objectMapper = new ObjectMapper();
                ParamNewBpa paramNewBpa = objectMapper.readValue(paramNewBpaJson, ParamNewBpa.class);

                List<ErrorsFile> errorsFileList = new ArrayList<>();

                String response = scannerFile.createBpa(file, user, paramNewBpa, errorsFileList, stopWatch, employeeOptional.get());

                switch (response) {
                    case "CREATE" -> {

                        return ResponseEntity.ok().body(response);
                    }
                    case "ERROR FILE" -> {
                        return ResponseEntity.badRequest().body(errorsFileList);
                    }
                    default -> {
                        return ResponseEntity.badRequest().body(response);
                    }
                }
            }

            return ResponseEntity.status(401).body("FORBIDDEN");

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
        User user = userService.get(authentication);

        DatesDTO datesDTO = bpaService.getDates(user);

        return ResponseEntity.ok(datesDTO);
    }

    // INCONSISTENCY

    /**
     * Se idade em (bpaI) é inferior a 1900 ou superior à data atual
     * @param paramInconsistency
     * @return
     */
    @PostMapping("/inconsistency/date/age")
    public ResponseEntity<Object> inconsistencyDates(@RequestBody ParamInconsistency paramInconsistency, Authentication authentication) {

        User user = userService.get(authentication);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramInconsistency.getDateBPA()), user);

        if(bpaOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();

            List<Bpai> bpaiListDB = bpaiService.get(bpa);

            EnCryptionAESService.decryptBpaiDtNasc(bpaiListDB);

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
    public ResponseEntity<Object> inconsistencyDatesProcedure(@RequestBody ParamInconsistency paramInconsistency, Authentication authentication) {
        User user = userService.get(authentication);
        DatesSigtap datesSigtap = user.getDatesSigtap();

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

            List<Bpai> bpaiListDB = bpaiService.get(bpa);

            EnCryptionAESService.decryptBpaiIdade(bpaiListDB);

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
    public ResponseEntity<Object> inconsistencyCep(@RequestBody ParamInconsistency paramInconsistency, Authentication authentication) {
        User user = userService.get(authentication);
        DatesSigtap datesSigtap = user.getDatesSigtap();

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

            List<Bpai> bpaiListDB = bpaiService.get(bpa);

            EnCryptionAESService.decryptBpaiCep(bpaiListDB);

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
    public ResponseEntity<Object> inconsistencyCepBlank(@RequestBody ParamInconsistency paramInconsistency, Authentication authentication) {
        User user = userService.get(authentication);
        DatesSigtap datesSigtap = user.getDatesSigtap();

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

            List<Bpai> bpaiListDB = bpaiService.get(bpa);

            EnCryptionAESService.decryptBpaiAddress(bpaiListDB);

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
    public ResponseEntity<Object> inconsistencyQtServices(@RequestBody ParamInconsistency paramInconsistency, Authentication authentication) {

        User user = userService.get(authentication);
        DatesSigtap datesSigtap = user.getDatesSigtap();

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

            List<Bpai> bpaiListDB = bpaiService.get(bpa);

            EnCryptionAESService.decryptFlhSeq(bpaiListDB);

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
    public ResponseEntity<Object> inconsistencyMonthBpa(@RequestBody ParamInconsistency paramInconsistency, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramInconsistency.getDateBPA()), user);

        if(bpaOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();

            TitleBpa titleBpa = titleBpaService.get(bpa);
            List<Bpai> bpaiListDB = bpaiService.get(bpa);

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
    public ResponseEntity<Object> inconsistencyRace(@RequestBody ParamInconsistency paramInconsistency, Authentication authentication) {
        User user = userService.get(authentication);

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramInconsistency.getDateBPA()), user);

        if(bpaOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();

            List<Bpai> bpaiListDB = bpaiService.get(bpa);

            EnCryptionAESService.decryptRace(bpaiListDB);

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
    public ResponseEntity<Object> inconsistencyProfessionals(@RequestBody ParamInconsistency paramInconsistency, Authentication authentication) {
        User user = userService.get(authentication);
        DatesSigtap datesSigtap = user.getDatesSigtap();

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramInconsistency.getDateBPA()), user);

        Optional<LinkProfessionals> linkProfessionalsOptional;
        if (datesSigtap.isDateProfessionalsAuto()) {
            linkProfessionalsOptional = linkProfessionalsService.get(user);
        } else {
            linkProfessionalsOptional = linkProfessionalsService.get(datesSigtap.getDateProfessionals(), user);
        }

        if(bpaOptional.isPresent() && linkProfessionalsOptional.isPresent()) {
            Bpa bpa = bpaOptional.get();
            LinkProfessionals linkProfessionals = linkProfessionalsOptional.get();

            List<Bpai> bpaiListDB = bpaiService.get(bpa);

            EnCryptionAESService.decryptFlhSeq(bpaiListDB);

            List<ProfessionalComplete> professionalCompleteList = linkProfessionals.getProfessionalCompleteList();

            EnCryptionAESService.decryptProfessionalCns(professionalCompleteList);

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
    public ResponseEntity<Object> inconsistencyFpo(@RequestBody ParamInconsistency paramInconsistency, Authentication authentication)  {
        User user = userService.get(authentication);
        DatesSigtap datesSigtap = user.getDatesSigtap();

        Optional<Bpa> bpaOptional = bpaService.get(Utilities.formatDate(paramInconsistency.getDateBPA()), user);

        Optional<LinkFpo> linkFpoOptional = linkFpoService.verify(user);

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

            List<Bpac> bpacListDB = bpacService.get(bpa);
            List<Bpai> bpaiListDB = bpaiService.get(bpa);

            //Campo PA de (BPAC e BPAI) e campo SEXO de BPAI estão em tb_procedimento
            Set<String> procedurePa = linkProcedure.getProcedureList().stream().map(Procedure::getCodProcedimento).collect(Collectors.toSet());
            //Se PA e CBO de (bpaC e bpaI) estão em tb_procedimento_ocupação
            Set<String> occupationPa = linkOccupation.getOccupationList().stream().map(Occupation::getCodProcedimento).collect(Collectors.toSet());
            linkProcedure = null;
            linkOccupation = null;

            EnCryptionAESService.decryptFlhSeq(bpaiListDB);

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
    public ResponseEntity<Object> inconsistencyProdution(@RequestBody ParamInconsistency paramInconsistency, Authentication authentication) {
        User user = userService.get(authentication);
        DatesSigtap datesSigtap = user.getDatesSigtap();

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

            List<Bpac> bpacListDB = bpacService.get(bpa);
            List<Bpai> bpaiListDB = bpaiService.get(bpa);

            EnCryptionAESService.decryptSex(bpaiListDB);

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
    public ResponseEntity<Object> inconsistencyOccupation(@RequestBody ParamInconsistency paramInconsistency, Authentication authentication) {
        User user = userService.get(authentication);
        DatesSigtap datesSigtap = user.getDatesSigtap();

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

            List<Bpac> bpacListDB = bpacService.get(bpa);
            List<Bpai> bpaiListDB = bpaiService.get(bpa);

            Set<String> occupationPa = linkOccupation.getOccupationList().stream()
                    .map(Occupation::getCodProcedimento)
                    .collect(Collectors.toSet());

            Set<String> occupationCBO = linkOccupation.getOccupationList().stream()
                    .map(Occupation::getCodOcupacao)
                    .collect(Collectors.toSet());

            linkOccupation = null; // liberar memória

            EnCryptionAESService.decryptFlhSeq(bpaiListDB);

            //Verifica se PA de BPAC e BPAI existe no arquivo FPO.
            List<ErrorOccupationDTO> errorsOccupationBpacDTOS = bpacService.verifyErrorsOccupation(occupationPa, occupationCBO, bpacListDB);

            List<ErrorOccupationDTO> errorsOccupationBpaiDTOS = bpaiService.verifyErrorsOccupation(occupationPa, occupationCBO, bpaiListDB);


            return ResponseEntity.ok(new InconsistencyOccupationDTO(errorsOccupationBpacDTOS, errorsOccupationBpaiDTOS));

        }

        return ResponseEntity.badRequest().body("NOT DATE EXISTS FPO");
    }

}
