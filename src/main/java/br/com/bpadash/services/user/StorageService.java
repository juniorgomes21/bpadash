package br.com.bpadash.services.user;

import br.com.bpadash.dto.bpa.BpacDTO;
import br.com.bpadash.dto.bpa.BpaiDTO;
import br.com.bpadash.dto.bpa.TitleBpaDTO;
import br.com.bpadash.model.*;
import br.com.bpadash.services.bpa.BpaiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageService {

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

    public Long hasStorage(TitleBpa titleBpa , List<Bpac> bpacList , List<Bpai> bpaiList) {

        return this.createFile(titleBpa, bpacList, bpaiList);
    }

    public long hasStorage(List<ProfessionalComplete> list) {

        StringBuilder fileContent = new StringBuilder();

        // Adicione os dados de Bpai
        for (ProfessionalComplete professionalComplete : list) {
            fileContent.append(professionalComplete.toString());
            fileContent.append("\n");
            fileContent.append(professionalComplete.getDadosVinc().toString());
        }


        return fileContent.toString().getBytes().length;
    }


    private Long getBytes(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(object);
            return (long) json.length();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public void updateStorage(Bpa bpa, User user) {
        user.setStorageUsed(user.getStorageUsed() - bpa.getFileSizeInBytesInt());
        user.setStorageFree(user.getStorageFree() + bpa.getFileSizeInBytesInt());
    }

    private long createFile(TitleBpa titleBpa , List<Bpac> bpacList , List<Bpai> bpaiList) {
        StringBuilder fileContent = new StringBuilder();

        if(titleBpa!= null) {
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

        return fileContent.toString().getBytes().length;
    }

}
