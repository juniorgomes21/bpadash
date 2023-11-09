package br.com.bpadash.services.bpa;

import br.com.bpadash.dto.bpa.BpaDTO;
import br.com.bpadash.model.*;
import br.com.bpadash.repository.bpa.BpaRepository;
import br.com.bpadash.services.user.StorageService;
import br.com.bpadash.services.user.UserService;
import br.com.bpadash.utilities.RandomNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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

        List<BpaDTO> bpaDTOList = new ArrayList<>();
        user.getBpas().forEach( bpa -> {
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

        // Adicione o título
        fileContent.append(titleBpa.toString() );
        fileContent.append("\n");

        // Adicione os dados de Bpac
        for (Bpac bpac : bpacList) {
            fileContent.append(bpac.toString()); // Você deve implementar o método toString() em Bpac
            fileContent.append("\n");
        }

        // Adicione os dados de Bpai
        for (Bpai bpai : bpaiList) {
            fileContent.append(bpai.toString()); // Você deve implementar o método toString() em Bpai
            fileContent.append("\n");
        }


        return fileContent;
    }

    @Transactional
    public void delete(Bpa bpa, User user) {
        user.getBpas().remove(bpa);

        bpacService.delete(bpa);
        bpaiService.delete(bpa);
        titleBpaService.delete(bpa);

        storageService.updateStorage(bpa, user);
        userService.save(user);

        bpaRepository.delete(bpa);
    }
}
