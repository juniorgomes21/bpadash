package br.com.bpadash.services.bpa;

import br.com.bpadash.dto.DatesDTO;
import br.com.bpadash.dto.sigtap.ErrorRaceDTO;
import br.com.bpadash.dto.sigtap.ErrorAgeDatesDTO;
import br.com.bpadash.dto.sigtap.ErrorDtAtendDTODTO;
import br.com.bpadash.model.bpa.*;
import br.com.bpadash.model.enumModel.ZoneTime;
import br.com.bpadash.model.sigtap.Fpo;
import br.com.bpadash.model.sigtap.LinkFpo;
import br.com.bpadash.model.user.User;
import br.com.bpadash.projections.DateProjection;
import br.com.bpadash.repository.bpa.BpaRepository;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import br.com.bpadash.services.fpo.LinkFpoService;
import br.com.bpadash.services.user.StorageService;
import br.com.bpadash.services.user.UserService;
import br.com.bpadash.utilities.RandomNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BpaService {

    @Autowired
    private BpaRepository bpaRepository;
    @Autowired
    private TitleBpaService titleBpaService;
    @Autowired
    private BpacService bpacService;
    @Autowired
    private BpaiService bpaiService;
    @Autowired
    private UserService userService;
    @Autowired
    private LinkFpoService linkFpoService;


    public List<Bpa> get(User user) {
        List<Bpa> bpaList = bpaRepository.findByUser(user);

        return bpaList;
    }

    public Bpa get(String identifier, User user) {

        return bpaRepository.findByIdentifierAndUser(identifier, user);
    }

    public Optional<Bpa> get(LocalDate date, User user) {

        return bpaRepository.findByDateAndUser(date, user);
    }

    public List<Bpa> getForYear(User user, int year) {
        // TODO mudar para o ano corrente. perguntar se o arquivo BPA só chega fim do mês.
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        return bpaRepository.findByDateBetweenAndUser(startDate, endDate, user, Sort.by(Sort.Direction.ASC, "date"));
    }

    public String generateIdentifier(User user) {
        String identifier;
        while (true) {
            identifier = RandomNumber.createIndentifier();

            boolean exist = false;
            for(Bpa bpa: user.getBpas()) {
                if (bpa.getIdentifier().equals(identifier)) {
                    exist = true;
                    break;
                }
            }

            if(!exist) {
                break;
            }
        }

        return identifier;
    }

    public Bpa save(Bpa bpa) {
        return  bpaRepository.save(bpa);
    }

    public void updateStateManager(Bpa bpa, boolean isCount) {
        if(isCount) bpa.getManagerBpa().setCalculateCountLine(true);
        bpa.getManagerBpa().setCalculateAge(true);
        bpa.getManagerBpa().setCalculateRace(true);
        bpa.getManagerBpa().setCalculateSex(true);

        this.save(bpa);
    }

    public void saveManagerCountLine(Bpa bpa, int countBpac, int countBpai, int countTotal) {
        bpa.getManagerBpa().setCountTotalLine(countTotal);
        bpa.getManagerBpa().setCountLineBpac(countBpac);
        bpa.getManagerBpa().setCountLineBpai(countBpai);

        bpa.getManagerBpa().setCalculateCountLine(false);

        bpaRepository.save(bpa);
    }

    public void saveManagerInvoicing(Bpa bpa, BigDecimal valeuTotalInvoicing) {
        bpa.getManagerBpa().setInvoicing(valeuTotalInvoicing);
        bpa.getManagerBpa().setCalculateInvoicing(false);

        this.save(bpa);
    }

    public void saveManagerAge(Bpa bpa, Map<String, Integer> map) {
        bpa.getManagerBpa().setYong(map.get("yong"));
        bpa.getManagerBpa().setMiddleAge(map.get("middleAge"));
        bpa.getManagerBpa().setOld(map.get("old"));

        bpa.getManagerBpa().setCalculateAge(false);

        this.save(bpa);
    }

    public void saveManagerRace(Bpa bpa, Map<String, Integer> map) {
        bpa.getManagerBpa().setBlank(map.get("blank") == null ? 0 : map.get("blank"));
        bpa.getManagerBpa().setBlack(map.get("black") == null ? 0 : map.get("black"));
        bpa.getManagerBpa().setBrown(map.get("brown") == null ? 0 : map.get("brown"));
        bpa.getManagerBpa().setYellow(map.get("yellow") == null ? 0 : map.get("yellow"));
        bpa.getManagerBpa().setIndigenous(map.get("Indigenous") == null ? 0 : map.get("Indigenous"));
        bpa.getManagerBpa().setNoInformation(map.get("noInformation") == null ? 0 : map.get("noInformation"));

        bpa.getManagerBpa().setCalculateRace(false);

        this.save(bpa);
    }


    public void saveManagerSex(Bpa bpa, Map<String, Integer> map) {
        bpa.getManagerBpa().setSexM(map.get("M"));
        bpa.getManagerBpa().setSexF(map.get("F"));

        bpa.getManagerBpa().setCalculateSex(false);

        this.save(bpa);
    }

    public List<Bpa> saveAll(List<Bpa> bpas) {
        return bpaRepository.saveAll(bpas);
    }

    public Bpa getForDate(int month, int year, User user) {

        return bpaRepository.findByDateAndUser(LocalDate.of(year, month, 1), user).orElse(null);
    }

    public void updateBytes(Bpa bpa, Long totalBytes, boolean add) {
        if(add) {
            bpa.setFileSizeInBytes(bpa.getFileSizeInBytes() + totalBytes);
        } else {
            bpa.setFileSizeInBytes(bpa.getFileSizeInBytes() - totalBytes);
        }

        this.save(bpa);
    }

    public StringBuilder createFile(Bpa bpa) {
        StringBuilder fileContent = new StringBuilder();

        TitleBpa titleBpa = titleBpaService.get(bpa);
        List<Bpac> bpacList = bpacService.get(bpa);
        List<Bpai> bpaiList = bpaiService.get(bpa);

        EnCryptionAESService.decryptBpai(bpaiList, true);

        fileContent.append(titleBpa.toString());
        fileContent.append("\n");

        for (Bpac bpac : bpacList) {
            fileContent.append(bpac.toString());
            fileContent.append("\n");
        }

        for (Bpai bpai : bpaiList) {
            fileContent.append(bpai.toString());
            fileContent.append("\n");
        }

        return fileContent;
    }

    public boolean isValidFile(MultipartFile file) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));

            String key = "BPA";

            try {
                String keyGet = br.readLine().substring(3, 6);
                return keyGet.equals(key);
            } catch (Exception e) {
                return false;
            }

        } catch (IOException e) {
            return false;
        }
    }

    public DatesDTO getDates(User user) {

        List<DateProjection> bpaList = bpaRepository.findAllByUser(user, DateProjection.class, Sort.by(Sort.Direction.DESC, "date"));

        DatesDTO datesDTO = new DatesDTO();
        bpaList.forEach( dateProjection -> {
            LocalDate date = dateProjection.getDate();

            List<Integer> dateList = new ArrayList<>();
            dateList.add(date.getMonthValue());
            dateList.add(date.getYear());

            datesDTO.getDates().add(dateList);
        });

        return datesDTO;
    }

    public void delete(Bpa bpa, User user) {
        user.getBpas().remove(bpa);

        bpacService.delete(bpa);
        bpaiService.delete(bpa);
        titleBpaService.delete(bpa);

        bpaRepository.delete(bpa);
    }

    public List<ErrorAgeDatesDTO> verifyErrorsDate(List<Bpai> bpaiListDB) {
        List<ErrorAgeDatesDTO> errors = new ArrayList<>();

        LocalDate now = LocalDate.now(ZoneId.of(ZoneTime.BR.getBr()));
        for (Bpai bpai: bpaiListDB) {

            String flh = bpai.getFlh();
            String seq = bpai.getSeq();

            try {
                int year = Integer.parseInt(bpai.getDtnasc().substring(0, 4));
                int month = Integer.parseInt(bpai.getDtnasc().substring(4, 6));
                int day = Integer.parseInt(bpai.getDtnasc().substring(6, 8));

                LocalDate date = LocalDate.of(year, month, day);

                int comparison = now.compareTo(date);
                boolean beffore1900 = date.getYear() < 1900;

                if(beffore1900 || comparison < 0) {
                    String name = EnCryptionAESService.decrypt(bpai.getNmpac());
                    String age = EnCryptionAESService.decrypt(bpai.getIdade());

                    errors.add(new ErrorAgeDatesDTO(
                            bpai.getId(),
                            "DATE INVALID",
                            flh,
                            seq,
                            name,
                            age,
                            date.toString(),
                            beffore1900));
                }

            } catch (Exception e) {
                String name = EnCryptionAESService.decrypt(bpai.getNmpac());
                String age = EnCryptionAESService.decrypt(bpai.getIdade());
                String date = bpai.getDtnasc().isBlank() ? bpai.getDtnasc() : bpai.getDtnasc().substring(0, 4) + "-" + bpai.getDtnasc().substring(4, 6) + "-" + bpai.getDtnasc().substring(6, 8);

                errors.add(new ErrorAgeDatesDTO(
                        bpai.getId(),
                        "ERROR FORMATION DATE",
                        flh,
                        seq,
                        name,
                        age, //String.valueOf(Integer.parseInt(age) / 12),
                        date,
                        false));
            }
        }

        return errors;
    }

    public List<ErrorDtAtendDTODTO> verifyErrorsDtAtend(List<Bpai> bpaiListDB , TitleBpa titleBpa) {
        List<ErrorDtAtendDTODTO> errors = new ArrayList<>();

        LocalDate dateTitle = LocalDate.of(Integer.parseInt(titleBpa.getMvm().substring(0,4)), Integer.parseInt(titleBpa.getMvm().substring(4,6)), 1);

        EnCryptionAESService.decryptBpaiDtAtendi(bpaiListDB);

        for (Bpai bpai: bpaiListDB) {
            LocalDate date = LocalDate.of(Integer.parseInt(bpai.getDtaten().substring(0,4)), Integer.parseInt(bpai.getDtaten().substring(4,6)), 1);

            if(!dateTitle.equals(date)) {
                errors.add(new ErrorDtAtendDTODTO(bpai.getId(), bpai.getFlh(), bpai.getSeq(), "DATE SERVICE INVALID", dateTitle.toString(), date.toString()));
            }
        }

        return errors;
    }

    public List<ErrorRaceDTO> verifyErrorsRace(List<Bpai> bpaiListDB) {
        List<ErrorRaceDTO> errors = new ArrayList<>();

        List<String> races = new ArrayList<>(Arrays.asList("01", "02", "03", "04", "05"));
        for (Bpai bpai: bpaiListDB) {

            if(!races.contains(bpai.getRaca())) {
                errors.add(new ErrorRaceDTO(bpai.getId(), bpai.getFlh(), bpai.getSeq(), "RACE INVALID", EnCryptionAESService.decrypt(bpai.getNmpac()), bpai.getRaca()));
            }
        }

        return errors;
    }

    public BigDecimal calculateInvoicing(Bpa bpa, List<Fpo> fpoList, List<Bpac> bpacList, List<Bpai> bpaiList, User user, boolean isSave) {

        if(fpoList == null) {
            Optional<LinkFpo> linkFpoOptional = linkFpoService.verify(user);

            if(linkFpoOptional.isEmpty()) return BigDecimal.ZERO;

            fpoList = linkFpoOptional.get().getFpoList();
        }

        if(bpacList == null) {
            bpacList = bpacService.get(bpa);
        }

        if(bpaiList == null) {
            bpaiList = bpaiService.get(bpa);
        }

        List<Fpo> fpoProcessed = new ArrayList<>();
        BigDecimal invoicingTotal = BigDecimal.ZERO;

        for (Bpac bpac: bpacList) {
            int qt = Integer.parseInt(bpac.getQt());
            String pa = bpac.getPa().substring(0, 9);

            Optional<Fpo> fpoOptional;

            fpoOptional = fpoProcessed.stream().filter( fpo -> fpo.getPa().equals(pa)).findFirst();
            if(fpoOptional.isEmpty()) {
                fpoOptional = fpoList.stream().filter( fpo -> fpo.getPa().equals(pa)).findFirst();
            }

            if(fpoOptional.isPresent()) {
                Fpo fpo = fpoOptional.get();
                fpoProcessed.add(fpo);

                BigDecimal valorUnit = fpo.getValueUnit();

                BigDecimal valueMultiply = valorUnit.multiply(new BigDecimal(qt));

                invoicingTotal = invoicingTotal.add(valueMultiply);
            }
        }

        for (Bpai bpai: bpaiList) {
            int qt = Integer.parseInt(bpai.getQt());
            String pa = bpai.getPa().substring(0, 9);

            Optional<Fpo> fpoOptional;

            fpoOptional = fpoProcessed.stream().filter( fpo -> fpo.getPa().equals(pa)).findFirst();
            if(fpoOptional.isEmpty()) {
                fpoOptional = fpoList.stream().filter( fpo -> fpo.getPa().equals(pa)).findFirst();
            }

            if(fpoOptional.isPresent()) {
                Fpo fpo = fpoOptional.get();
                fpoProcessed.add(fpo);

                BigDecimal valorUnit = fpo.getValueUnit();

                BigDecimal valueMultiply = valorUnit.multiply(new BigDecimal(qt));

                invoicingTotal = invoicingTotal.add(valueMultiply);
            }
        }

        if(isSave) {
            this.saveManagerInvoicing(bpa, invoicingTotal);
        } else {
            bpa.getManagerBpa().setInvoicing(invoicingTotal);
            bpa.getManagerBpa().setCalculateInvoicing(false);
        }

        return invoicingTotal;
    }

    public int calculateProcedure(Bpa bpa) {
        List<Bpac> bpacList = bpacService.get(bpa);
        List<Bpai> bpaiList = bpaiService.get(bpa);

        int totalLine = bpacList.size() + bpaiList.size();

        this.saveManagerCountLine(bpa, bpacList.size(), bpaiList.size(), totalLine + 1);

        return totalLine;
    }

    public Long calculateByte(User user) {
        List<Bpa> bpaList = this.get(user);

        long totalByte = 0L;
        for (Bpa bpa: bpaList) {
            totalByte = totalByte + bpa.getFileSizeInBytes();
        }

        return totalByte;
    }

    public void calculateLine(Bpa bpa , int countBpac , int countBpai) {
        bpa.getManagerBpa().setCountLineBpac(countBpac);
        bpa.getManagerBpa().setCountLineBpai(countBpai);
        bpa.getManagerBpa().setCountTotalLine(countBpac + countBpai + 1);
    }

    public Map<String, Integer> calculateAge(Bpa bpa, List<Bpac> bpacList, List<Bpai> bpaiList) {

        Map<String, Integer> contagemIdades = new HashMap<>();

        if(bpacList != null) {
            bpacList.forEach( bpac -> {
                String age = bpac.getIdade();
                contagemIdades.put(age, contagemIdades.getOrDefault(age, 0) + 1);
            });
        }

        bpaiList.forEach( bpai -> {
            String age = bpai.getIdade();
            contagemIdades.put(age, contagemIdades.getOrDefault(age, 0) + 1);
        });

        // Agrupar as idades em faixas e calcular a porcentagem para cada grupo
        Map<String, Integer> grupos = new HashMap<>();
        grupos.put("yong", 0);
        grupos.put("middleAge", 0);
        grupos.put("old", 0);

        contagemIdades.forEach((idade, ocorrencias) -> {
            int idadeInt = Integer.parseInt(idade);

            if (idadeInt <= 20) {
                grupos.put("yong", grupos.getOrDefault("yong", 0) + ocorrencias);
            } else if (idadeInt <= 50) {
                grupos.put("middleAge", grupos.getOrDefault("middleAge", 0) + ocorrencias);
            } else {
                grupos.put("old", grupos.getOrDefault("old", 0) + ocorrencias);
            }
        });


        // Total de ocorrências
        int totalOcorrencias = bpaiList.size();

        // Calcular a porcentagem para cada grupo
        Map<String, Integer> porcentagensGrupos = grupos.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (int) Math.round(((double) entry.getValue() / totalOcorrencias) * 100)
                ));

        bpa.getManagerBpa().setYong(porcentagensGrupos.get("yong"));
        bpa.getManagerBpa().setMiddleAge(porcentagensGrupos.get("middleAge"));
        bpa.getManagerBpa().setOld(porcentagensGrupos.get("old"));

        return porcentagensGrupos;
    }

    public Map<String, Integer> calculateSex(Bpa bpa , List<Bpai> bpaiList) {

        Map<String, Integer> contagemIdades = new HashMap<>();
        contagemIdades.put("M", 0);
        contagemIdades.put("F", 0);

        bpaiList.forEach(bpai -> {
            String age = bpai.getSexo();
            contagemIdades.put(age, contagemIdades.getOrDefault(age, 0) + 1);
        });


        int totalOcorrencias = bpaiList.size();

        // Calcular a porcentagem para cada grupo
        Map<String, Integer> porcentagensGrupos = contagemIdades.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (int) Math.round(((double) entry.getValue() / totalOcorrencias) * 100)
                ));

        bpa.getManagerBpa().setSexM(porcentagensGrupos.get("M"));
        bpa.getManagerBpa().setSexF(porcentagensGrupos.get("F"));

        return porcentagensGrupos;
    }

    public Map<String, Integer> calculateRace(Bpa bpa , List<Bpai> bpaiList) {

        Map<String, Integer> contagemRace = new HashMap<>();

        bpaiList.forEach(bpai -> {
            String race = bpai.getRaca();
            contagemRace.put(race, contagemRace.getOrDefault(race, 0) + 1);
        });

        // Agrupar as idades em faixas e calcular a porcentagem para cada grupo
        Map<String, Integer> grupos = new HashMap<>();
        grupos.put("blank", 0);
        grupos.put("black", 0);
        grupos.put("brown", 0);
        grupos.put("yellow", 0);
        grupos.put("Indigenous", 0);
        grupos.put("noInformation", 0);

        contagemRace.forEach((race, ocorrencias) -> {
            switch (race) {
                case "01" -> grupos.put("blank" , grupos.getOrDefault("blank" , 0) + ocorrencias);
                case "02" -> grupos.put("black" , grupos.getOrDefault("black" , 0) + ocorrencias);
                case "03" -> grupos.put("brown" , grupos.getOrDefault("brown" , 0) + ocorrencias);
                case "04" -> grupos.put("yellow" , grupos.getOrDefault("yellow" , 0) + ocorrencias);
                case "05" -> grupos.put("Indigenous" , grupos.getOrDefault("Indigenous" , 0) + ocorrencias);
                case "99" -> grupos.put("noInformation" , grupos.getOrDefault("noInformation" , 0) + ocorrencias);
            }
        });

        // Total de ocorrências
        int totalOcorrencias = bpa.getManagerBpa().getCountLineBpai();

        // Calcular a porcentagem para cada grupo
        Map<String, Integer> porcentagensGrupos = grupos.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (int) Math.round(((double) entry.getValue() / totalOcorrencias) * 100)
                ));

        bpa.getManagerBpa().setBlank(porcentagensGrupos.get("blank") == null ? 0 : porcentagensGrupos.get("blank"));
        bpa.getManagerBpa().setBlack(porcentagensGrupos.get("black") == null ? 0 : porcentagensGrupos.get("black"));
        bpa.getManagerBpa().setBrown(porcentagensGrupos.get("brown") == null ? 0 : porcentagensGrupos.get("brown"));
        bpa.getManagerBpa().setYellow(porcentagensGrupos.get("yellow") == null ? 0 : porcentagensGrupos.get("yellow"));
        bpa.getManagerBpa().setIndigenous(porcentagensGrupos.get("Indigenous") == null ? 0 : porcentagensGrupos.get("Indigenous"));
        bpa.getManagerBpa().setNoInformation(porcentagensGrupos.get("noInformation") == null ? 0 : porcentagensGrupos.get("noInformation"));

        return porcentagensGrupos;
    }

    public Map<String, Integer> calculateDtaten(Bpa bpa , List<Bpai> bpaiList) {
        if(bpaiList == null) {
            bpaiList = bpaiService.get(bpa);

            EnCryptionAESService.decryptRace(bpaiList);
        }

        Map<String, Integer> contagemRace = new HashMap<>();

        bpaiList.forEach(bpai -> {
            String race = bpai.getRaca();
            contagemRace.put(race, contagemRace.getOrDefault(race, 0) + 1);
        });

        // Agrupar as idades em faixas e calcular a porcentagem para cada grupo
        Map<String, Integer> grupos = new HashMap<>();
        grupos.put("blank", 0);
        grupos.put("black", 0);
        grupos.put("brown", 0);
        grupos.put("yellow", 0);
        grupos.put("Indigenous", 0);
        grupos.put("noInformation", 0);

        contagemRace.forEach((race, ocorrencias) -> {
            switch (race) {
                case "01" -> grupos.put("blank" , grupos.getOrDefault("blank" , 0) + ocorrencias);
                case "02" -> grupos.put("black" , grupos.getOrDefault("black" , 0) + ocorrencias);
                case "03" -> grupos.put("brown" , grupos.getOrDefault("brown" , 0) + ocorrencias);
                case "04" -> grupos.put("yellow" , grupos.getOrDefault("yellow" , 0) + ocorrencias);
                case "05" -> grupos.put("Indigenous" , grupos.getOrDefault("Indigenous" , 0) + ocorrencias);
                case "99" -> grupos.put("noInformation" , grupos.getOrDefault("noInformation" , 0) + ocorrencias);
            }
        });

        // Total de ocorrências
        int totalOcorrencias = bpa.getManagerBpa().getCountLineBpai();

        // Calcular a porcentagem para cada grupo
        Map<String, Integer> porcentagensGrupos = grupos.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (int) Math.round(((double) entry.getValue() / totalOcorrencias) * 100)
                ));

        if(bpa.getId() != null) {
            bpaiService.EntityManagerDetach(bpaiList);

            this.saveManagerRace(bpa, porcentagensGrupos);
        } else {
            bpa.getManagerBpa().setBlank(porcentagensGrupos.get("blank") == null ? 0 : porcentagensGrupos.get("blank"));
            bpa.getManagerBpa().setBlack(porcentagensGrupos.get("black") == null ? 0 : porcentagensGrupos.get("black"));
            bpa.getManagerBpa().setBrown(porcentagensGrupos.get("brown") == null ? 0 : porcentagensGrupos.get("brown"));
            bpa.getManagerBpa().setYellow(porcentagensGrupos.get("yellow") == null ? 0 : porcentagensGrupos.get("yellow"));
            bpa.getManagerBpa().setIndigenous(porcentagensGrupos.get("Indigenous") == null ? 0 : porcentagensGrupos.get("Indigenous"));
            bpa.getManagerBpa().setNoInformation(porcentagensGrupos.get("noInformation") == null ? 0 : porcentagensGrupos.get("noInformation"));
        }

        return porcentagensGrupos;
    }

    public void calculateAll(User user, Bpa bpa , List<Bpai> bpaiList, List<Bpac> bpacList, boolean calculateAll) {

        if(bpa.getId() != null) {
            bpaiList = bpaiService.get(bpa);
            bpacList = bpacService.get(bpa);
            EnCryptionAESService.decryptAgeAndSexAndRace(bpaiList);

            bpaiService.EntityManagerDetach(bpaiList);
        }

        if(calculateAll) {
            this.calculateLine(bpa, bpacList.size(), bpaiList.size());
            this.calculateAge(bpa, bpacList, bpaiList);
            this.calculateSex(bpa, bpaiList);
            this.calculateRace(bpa, bpaiList);
            if(linkFpoService.exist(user)) {
                this.calculateInvoicing(bpa, null, bpacList, bpaiList, user, false);
            } else {
                bpa.getManagerBpa().setCalculateInvoicing(true);
            }
        }

    }

    public void calculateLineInTitle(Bpa bpa , int sizeBpai , int sizeBpac , boolean add) {
        TitleBpa titleBpa = titleBpaService.get(bpa);

        String total;
        int value = Integer.parseInt(titleBpa.getLin());

        if(add) {
            total = String.format("%06d", value + sizeBpai + sizeBpac);
        } else {
            total = String.format("%06d", value - sizeBpai - sizeBpac);
        }

        titleBpa.setLin(total);
    }
}
