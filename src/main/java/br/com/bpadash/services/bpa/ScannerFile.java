package br.com.bpadash.services.bpa;

import br.com.bpadash.model.Bpac;
import br.com.bpadash.model.Bpai;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScannerFile {

    public List<Bpai> bpaiCreate(MultipartFile file) {

            try {
                // Obtém o fluxo de entrada do arquivo
                InputStream inputStream = file.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                // Lê o conteúdo do arquivo
                List<Bpai> bpaiList = new ArrayList<>();
                String line;
                int cont = 1;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("03")) {
                            String ident = line.substring(0, 2);  // 1
                            String cnes = line.substring(2, 9);  // 2
                            String cmp = line.substring(9, 15);  // 3
                            String cnsmed = line.substring(15, 30);  // 4
                            String cbo = line.substring(30, 36);  // 5
                            String dtaten = line.substring(36, 44);  // 6
                            String flh = line.substring(44, 47);  // 7
                            String seq = line.substring(47, 49);  // 8
                            String pa = line.substring(49, 59);  // 9
                            String cnspac = line.substring(59, 74);  // 10
                            String sexo = line.substring(74, 75);  // 11
                            String ibge = line.substring(75, 81);  // 12
                            String cid = line.substring(81, 85);  // 13
                            String idade = line.substring(85, 88);  // 14
                            String qt = line.substring(88, 94);  // 15
                            String caten = line.substring(94, 96);  // 16
                            String naut = line.substring(96, 109);  // 17
                            String org = line.substring(109, 112);  // 18
                            String nmpac = line.substring(112, 142);  // 19
                            String dtnasc = line.substring(142, 150);  // 20
                            String raca = line.substring(150, 152);  // 21
                            String etnia = line.substring(152, 156);  // 22
                            String nac = line.substring(156, 159);  // 23
                            String srv = line.substring(159, 162);  // 24
                            String clf = line.substring(162, 165);  // 25
                            String equipe_seq = line.substring(165, 173);  // 26
                            String equipe_area = line.substring(173, 177);  // 27
                            String cnpj = line.substring(177, 191);  // 28
                            String cep_pcnte = line.substring(191, 199);  // 29
                            String lograd_pcnte = line.substring(199, 202);  // 30
                            String end_pcnte = line.substring(202, 232);  // 31
                            String compl_pcnte = line.substring(232, 242);  // 32
                            String num_pcnte = line.substring(242, 247);  // 33
                            String bairro_pcnte = line.substring(247, 277);  // 34
                            String ddtel_pcnte = line.substring(277, 288); // 35
                            String email_pcnte = line.substring(288, 328); // 36
                            String ine = line.substring(328, 338); // 37
                            String fim;

                            try {
                                fim = line.substring(338, 340); // 38
                            } catch (StringIndexOutOfBoundsException e) {
                                fim = "  ";
                            }

                            Bpai bpai = new Bpai(
                                String.valueOf(cont),
                                ident,
                                cnes,
                                cmp,
                                cnsmed,
                                cbo,
                                dtaten,
                                flh,
                                seq,
                                pa,
                                cnspac,
                                sexo,
                                ibge,
                                cid,
                                idade,
                                qt,
                                caten,
                                naut,
                                org,
                                nmpac,
                                dtnasc,
                                raca,
                                etnia,
                                nac,
                                srv,
                                clf,
                                equipe_seq,
                                equipe_area,
                                cnpj,
                                cep_pcnte,
                                lograd_pcnte,
                                end_pcnte,
                                compl_pcnte,
                                num_pcnte,
                                bairro_pcnte,
                                ddtel_pcnte,
                                email_pcnte,
                                ine,
                                fim
                            );

                            bpaiList.add(bpai);

                            cont++;
                        }
                }

                br.close();

                return bpaiList;
            } catch (IOException e) {
                return null;
            }
    }


    public List<Bpac> bpacCreate(MultipartFile file) {
        try {
            // Obtém o fluxo de entrada do arquivo
            InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            // Lê o conteúdo do arquivo
            List<Bpac> bpacList = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("02")) {
                    String iden = line.substring(0, 2);
                    String cne = line.substring(0, 9);
                    String cmp = line.substring(9, 15);
                    String cbo = line.substring(15, 21);
                    String flh = line.substring(21, 24);
                    String seq = line.substring(24, 26);
                    String pa = line.substring(26, 36);
                    String idade = line.substring(36, 39);
                    String qt = line.substring(39, 45);
                    String org = line.substring(45, 48);
                    String fim;

                    try {
                        fim = line.substring(48, 50);
                    } catch (StringIndexOutOfBoundsException e) {
                        fim = "  ";
                    }

                    Bpac bpac = new Bpac(
                            iden,
                            cne,
                            cmp,
                            cbo,
                            flh,
                            seq,
                            pa,
                            idade,
                            qt,
                            org,
                            fim
                    );

                    bpacList.add(bpac);
                }
            }

            br.close();

            return bpacList;
        } catch (IOException e) {
            return null;
        }
    }
}
