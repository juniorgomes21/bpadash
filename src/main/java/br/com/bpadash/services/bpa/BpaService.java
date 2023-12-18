package br.com.bpadash.services.bpa;

import br.com.bpadash.dto.DatesDTO;
import br.com.bpadash.dto.bpa.BpaDTO;
import br.com.bpadash.dto.sigtap.ErrorRaceDTO;
import br.com.bpadash.dto.sigtap.ErrorAgeDatesDTO;
import br.com.bpadash.dto.sigtap.ErrorDtAtendDTODTO;
import br.com.bpadash.model.*;
import br.com.bpadash.model.enumModel.ZoneTime;
import br.com.bpadash.projections.DateProjection;
import br.com.bpadash.repository.bpa.BpaRepository;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.user.StorageService;
import br.com.bpadash.services.user.UserService;
import br.com.bpadash.utilities.RandomNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    private StorageService storageService;

    public List<BpaDTO> getAll(User user) {
        List<Bpa> bpaList = bpaRepository.findByUser(user);

        List<BpaDTO> bpaDTOList = new ArrayList<>();
        bpaList.forEach( bpa -> {
            BpaDTO bpaDto = new BpaDTO(bpa);
            bpaDTOList.add(bpaDto);
        });

        return bpaDTOList;
    }

    public Bpa get(String identifier, User user) {

        return bpaRepository.findByIdentifierAndUser(identifier, user);
    }

    public Optional<Bpa> get(LocalDate date, User user) {

        return bpaRepository.findByDateAndUser(date, user);
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

    public List<Bpa> saveAll(List<Bpa> bpas) {
        return bpaRepository.saveAll(bpas);
    }

    public Bpa getForDate(int month, int year, User user) {

        return bpaRepository.findByDateAndUser(LocalDate.of(year, month, 1), user).orElse(null);
    }

    public void updatebyte(Bpa bpa, Long totalBytes, boolean add) {
        if(add) {
            bpa.setFileSizeInBytes(bpa.getFileSizeInBytesInt() + totalBytes);
        } else {
            bpa.setFileSizeInBytes(bpa.getFileSizeInBytesInt() - totalBytes);
        }

        this.save(bpa);
    }

    public StringBuilder createFile(Bpa bpa) {
        StringBuilder fileContent = new StringBuilder();

        TitleBpa titleBpa = titleBpaService.get(bpa);
        List<Bpac> bpacList = bpacService.getBpacList(bpa);
        List<Bpai> bpaiList = bpaiService.getBpaiList(bpa);

        fileContent.append(titleBpa.toString() );
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

    public DatesDTO getDates(User user) {
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        List<DateProjection> bpaiList = bpaRepository.findAllByUser(user, DateProjection.class, sort);

        DatesDTO datesDTO = new DatesDTO();
        bpaiList.forEach( linkFpoListf -> {
            LocalDate date = linkFpoListf.getDate();

            List<Integer> dateList = new ArrayList<>();
            dateList.add(date.getMonthValue());
            dateList.add(date.getYear());

            datesDTO.getDates().add(dateList);
        });

        return datesDTO;
    }

    @Transactional
    public void delete(Bpa bpa, User user) {
        user.getBpas().remove(bpa);

        bpacService.delete(bpa);
        bpaiService.delete(bpa);
        titleBpaService.delete(bpa);

        userService.updateStorageAndSave(user, bpa.getFileSizeInBytesInt(), "subtract");
        userService.save(user);

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
                    String name = EncryptionService.decrypt(bpai.getNmpac());
                    String age = EncryptionService.decrypt(bpai.getIdade());

                    errors.add(new ErrorAgeDatesDTO(
                            bpai.getId(),
                            "DATE INVALID",
                            flh,
                            seq,
                            name,
                            age, //String.valueOf(Integer.parseInt(age) / 12),
                            date.toString(),
                            beffore1900));
                }

            } catch (Exception e) {
                String name = EncryptionService.decrypt(bpai.getNmpac());
                String age = EncryptionService.decrypt(bpai.getIdade());

                String date = bpai.getDtnasc().substring(0, 4) + "-" + bpai.getDtnasc().substring(4, 6) + "-" + bpai.getDtnasc().substring(6, 8);
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
                errors.add(new ErrorRaceDTO(bpai.getId(), bpai.getFlh(), bpai.getSeq(), "RACE INVALID", EncryptionService.decrypt(bpai.getNmpac()), bpai.getRaca()));
            }
        }

        return errors;
    }
}
