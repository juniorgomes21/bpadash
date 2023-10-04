package br.com.bpadash.services.bpa;

import br.com.bpadash.model.*;
import br.com.bpadash.params.bpa.ParamNewBpa;
import br.com.bpadash.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScannerFile {

    @Autowired
    private BpaService bpaService;

    @Autowired
    private TitleBpaService titleBpaService;

    @Autowired
    private BpacService bpacService;

    @Autowired
    private BpaiService bpaiService;

    @Autowired
    private UserService userService;

    @Transactional
    public Bpa createBpa(MultipartFile file, User user, ParamNewBpa paramNewBpa) throws IllegalArgumentException {
        TitleBpa titleBpa = new TitleBpa();
        List<Bpac> bpacList = new ArrayList<>();
        List<Bpai> bpaiList = new ArrayList<>();

        Bpa bpa = bpaService.save(new Bpa(user, bpaService.generateIdentifier(user), paramNewBpa));

        try {
            // Obtém o fluxo de entrada do arquivo
            InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                if(line.startsWith("01")) {
                    titleBpa = titleBpaService.createTitleBpa(line, lineNumber, bpa);

                } else if (line.startsWith("02")) {
                    Bpac bpac = bpacService.createBpac(line, lineNumber, bpa);
                    bpacList.add(bpac);

                } else if (line.startsWith("03")) {
                    Bpai bpai = bpaiService.createBpai(line, lineNumber, bpa);
                    bpaiList.add(bpai);
                }

                lineNumber++;
            }

            int byteTitle = 0;
            TitleBpa titleBpa1 = titleBpaService.save(titleBpa);
            byteTitle = titleBpaService.sizeByte(titleBpa1.getId());

            int byteBpac = 0;
            if(!bpacList.isEmpty()) {
                List<Bpac> listBpac = bpacService.save(bpacList);
                List<Long> ids = new ArrayList<>();
                listBpac.forEach( bpac -> {
                    ids.add(bpac.getId());
                });

                byteBpac = bpacService.sizeByte(ids);
            }

            int byteBpai = 0;
            if(!bpaiList.isEmpty()) {
                List<Bpai> listBpai = bpaiService.save(bpaiList);
                List<Long> ids = new ArrayList<>();
                listBpai.forEach( bpac -> {
                    ids.add(bpac.getId());
                });

                byteBpac = bpaiService.sizeByte(ids);
            }

            int totalBytes = byteBpai + byteBpac + byteTitle;

            bpaService.updatebyte(bpa, totalBytes, true);

            userService.addBpa(user, bpa);

            return bpa;


        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IOException e) {
            throw new NullPointerException();

        }
    }
}
