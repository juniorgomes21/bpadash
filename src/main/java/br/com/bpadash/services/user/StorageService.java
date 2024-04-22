package br.com.bpadash.services.user;

import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpac;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.bpa.TitleBpa;
import br.com.bpadash.model.sigtap.ProfessionalComplete;
import br.com.bpadash.model.user.User;
import br.com.bpadash.services.bpa.BpaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageService {

    @Autowired
    private BpaService bpaService;
    @Autowired
    private UserService userService;

    public static String formatBytes(Long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            double kilobytes = (double) bytes / 1024;
            return String.format("%.2f KB", kilobytes);
        } else if (bytes < 1024 * 1024 * 1024) {
            double megabytes = (double) bytes / (1024 * 1024);
            return String.format("%.2f MB", megabytes);
        } else {
            long gigabytes = bytes / (1024 * 1024 * 1024);
            return gigabytes + " GB";
        }
    }

    public static String porcent(Long totalBytes, Long bytesUsed) {
        double percent = ((double) bytesUsed / totalBytes) * 100;

        return String.format("%.2f", percent);
    }

    public void updateBytesBpaAndUser(User user , boolean addUser , Bpa bpa , boolean addBpa , Long size) {

        bpaService.updateBytes(bpa, size, addBpa);

        userService.updateStorageAndSave(user, size, addUser);

    }

    public Long quantityBytes(TitleBpa titleBpa , List<Bpac> bpacList , List<Bpai> bpaiList) {
        StringBuilder fileContent = new StringBuilder();

        if(titleBpa != null) {
            fileContent.append(titleBpa.toString());
            fileContent.append("\n");
        }

        if(bpacList != null) {
            for (Bpac bpac : bpacList) {
                fileContent.append(bpac.toString());
                fileContent.append("\n");
            }
        }

        if(bpaiList != null) {
            for (Bpai bpai : bpaiList) {
                fileContent.append(bpai.toString());
                fileContent.append("\n");
            }
        }

        return (long) fileContent.toString().getBytes().length;
    }

    public long quantityBytes(List<ProfessionalComplete> list) {

        StringBuilder fileContent = new StringBuilder();

        // Adicione os dados de Bpai
        for (ProfessionalComplete professionalComplete : list) {
            fileContent.append(professionalComplete.toString());
            fileContent.append("\n");
            fileContent.append(professionalComplete.getDadosVinc().toString());
        }


        return fileContent.toString().getBytes().length;
    }

}
