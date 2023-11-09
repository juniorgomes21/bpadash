package br.com.bpadash.services.bpa;

import br.com.bpadash.dto.error.ErrorsFile;
import br.com.bpadash.model.*;
import br.com.bpadash.params.bpa.ParamNewBpa;
import br.com.bpadash.services.fpo.FpoService;
import br.com.bpadash.services.user.StorageService;
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

    @Autowired
    private StorageService storageService;

    @Autowired
    private FpoService fpoService;

    @Transactional
    public String createBpa(MultipartFile file, User user, ParamNewBpa paramNewBpa, List<ErrorsFile> errorsFileList) throws IllegalArgumentException {
        TitleBpa titleBpa = new TitleBpa();
        List<Bpac> bpacList = new ArrayList<>();
        List<Bpai> bpaiList = new ArrayList<>();

        Bpa bpa = new Bpa(user, bpaService.generateIdentifier(user), paramNewBpa);

        if(bpaService.get(bpa.getDate(), user).isPresent()) {
            return "EXIST DATE";
        }

        try {
            // Obtém o fluxo de entrada do arquivo
            Long xxx = file.getSize();
            InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                if(line.startsWith("01")) {
                    titleBpa = titleBpaService.createTitleBpa(line, lineNumber, bpa, errorsFileList);

                } else if (line.startsWith("02")) {
                    Bpac bpac = bpacService.createBpac(user, line, lineNumber, bpa, errorsFileList);
                    if(bpac != null) {
                        bpacList.add(bpac);
                    }

                } else if (line.startsWith("03")) {
                    Bpai bpai = bpaiService.create(line, lineNumber, bpa);
                    if(bpai != null) {
                        bpaiList.add(bpai);
                    }
                }

                lineNumber++;
            }

            if(!errorsFileList.isEmpty()) {
                return "ERROR FILE";
            }

            Long totalBytes = storageService.hasStorage(bpa, titleBpa, bpacList, bpaiList);


            if(user.getStorageFree() < totalBytes) {
                return "NOT STORAGE";
            }

            bpa = bpaService.save(bpa);

            titleBpaService.save(titleBpa);

            if(!bpacList.isEmpty()) {
                bpacService.save(bpacList);
            }

            if(!bpaiList.isEmpty()) {
                bpaiService.save(bpaiList);
            }

            bpaService.updatebyte(bpa, totalBytes, true);

            userService.addBpa(user, bpa, totalBytes);

            System.out.println(totalBytes);
            System.out.println("totalBytes DB = " + StorageService.formatBytes(totalBytes));

            return "CREATE";


        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IOException e) {
            throw new NullPointerException();
        }
    }

    public Bpa createBpac(User user, MultipartFile file , Bpa bpa, List<ErrorsFile> errorsFileList) throws IllegalArgumentException {
        List<Bpac> bpacList = new ArrayList<>();


        try {
            // Obtém o fluxo de entrada do arquivo
            InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("02")) {
                    Bpac bpac = bpacService.createBpac(user, line, lineNumber, bpa, errorsFileList);
                    bpacList.add(bpac);
                }

                lineNumber++;
            }

            if(!errorsFileList.isEmpty()) {
                return null;
            }

            Long byteBpac = 0L;
            if(!bpacList.isEmpty()) {
                List<Bpac> listBpac = bpacService.save(bpacList);
                List<Long> ids = new ArrayList<>();
                listBpac.forEach( bpac -> {
                    ids.add(bpac.getId());
                });

                byteBpac = bpacService.sizeByte(ids);
            }


            bpaService.updatebyte(bpa, byteBpac, true);

            return bpa;

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IOException e) {
            throw new NullPointerException();
        }
    }

    public Bpa createBpai(MultipartFile file , Bpa bpa) throws IllegalArgumentException {
        List<Bpai> bpaiList = new ArrayList<>();

        try {
            // Obtém o fluxo de entrada do arquivo
            InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                 if (line.startsWith("03")) {
                    Bpai bpai = bpaiService.create(line, lineNumber, bpa);
                    bpaiList.add(bpai);
                 }

                lineNumber++;
            }

            Long byteBpai = 0L;
            if(!bpaiList.isEmpty()) {
                List<Bpai> listBpai = bpaiService.save(bpaiList);
                List<Long> ids = new ArrayList<>();
                listBpai.forEach( bpac -> {
                    ids.add(bpac.getId());
                });

                byteBpai = bpaiService.sizeByte(ids);
            }

            bpaService.updatebyte(bpa, byteBpai, true);

            return bpa;

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IOException e) {
            throw new NullPointerException();

        }
    }

    public List<Fpo> createFpo(MultipartFile file) throws IllegalArgumentException {
        try {
            // Obtém o fluxo de entrada do arquivo
            InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            int lineNumber = 1;
            List<Fpo> fpoList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                try {
                    String pa = line.substring(2, 11);
                    if (pa.matches("\\d+")) {
                        fpoList.add(fpoService.create(pa, line, lineNumber));
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    // Pula a linha sem informações
                }
                lineNumber++;
            }

            return fpoList;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IOException e) {
            throw new NullPointerException();

        }
    }
}
