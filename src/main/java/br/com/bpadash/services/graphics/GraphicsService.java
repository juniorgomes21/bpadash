package br.com.bpadash.services.graphics;

import br.com.bpadash.dto.graphics.SexGraphicsDTO;
import br.com.bpadash.model.bpa.Bpa;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GraphicsService {

    @Autowired
    private BpaiService bpaiService;

    //TODO habilitar o envic
    @Cacheable(value = "sexGraphics", key = "#cacheKey")
    public SexGraphicsDTO dataSex(Bpa bpa, String cacheKey) {
        List<Bpai> bpaiList = bpaiService.get(bpa);

        EnCryptionAESService.decryptSex(bpaiList);

        Map<String, List<Bpai>> groupedBySex = bpaiList.stream().collect(Collectors.groupingBy(Bpai::getSexo));

        List<Bpai> countM = groupedBySex.getOrDefault("M", List.of());
        List<Bpai> countF = groupedBySex.getOrDefault("F", List.of());

        return new SexGraphicsDTO(countM.size(), countF.size());
    }

    @Cacheable(value = "sexGraphicsAll", key = "#cacheKey")
    public SexGraphicsDTO dataSexAll(List<Bpa> bpaList, String cacheKey) {
        int countM = 0;
        int countF = 0;

        for(Bpa bpa: bpaList) {
            List<Bpai> bpaiList = bpaiService.get(bpa);

            EnCryptionAESService.decryptSex(bpaiList);

            Map<String, List<Bpai>> groupedBySex = bpaiList.stream().collect(Collectors.groupingBy(Bpai::getSexo));

            countM = countM + groupedBySex.getOrDefault("M", List.of()).size();
            countF = countF + groupedBySex.getOrDefault("F", List.of()).size();
        }

        return new SexGraphicsDTO(countM, countF);
    }

    public SexGraphicsDTO dataAge(Bpa bpa , String cacheId) {
        List<Bpai> bpaiList = bpaiService.get(bpa);

        EnCryptionAESService.decryptBpaiIdade(bpaiList);

        Map<String, List<Bpai>> groupedBySex = bpaiList.stream().collect(Collectors.groupingBy(Bpai::getSexo));

        List<Bpai> countM = groupedBySex.getOrDefault("M", List.of());
        List<Bpai> countF = groupedBySex.getOrDefault("F", List.of());

        return new SexGraphicsDTO(countM.size(), countF.size());
    }
}
