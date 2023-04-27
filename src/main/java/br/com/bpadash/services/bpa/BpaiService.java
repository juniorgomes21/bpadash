package br.com.bpadash.services.bpa;

import br.com.bpadash.model.Bpai;
import br.com.bpadash.repository.bpa.BpaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class BpaiService {

    @Autowired
    private BpaiRepository bpaiRepository;

    public void save(List<Bpai> bpaiList) {
        bpaiRepository.saveAll(bpaiList);
    }


    /**
     * Faz a avalidação da estrutura do arquivo BPAI.
     * @param bpaiList
     * @return
     */
    public String isValidBpai(List<Bpai> bpaiList) {

        List<Integer> validsIdent = Arrays.asList(1, 2, 3);
        List<String> validsSex = Arrays.asList("F", "M");

        bpaiList.forEach( bpai -> {
            if(validsIdent.contains(bpai.getIdent())) {

            } else if(!bpai.getCnes().matches("\\d+") || bpai.getCnes().length() != 7) {
                // tem que conter apenas numeros e conter apenas 7 digitos
            } else if(bpai.getCmp().length() == 6) {
                // tem que ter 6 digitos
            } else if(bpai.getCnes().length() == 14) {
                // tem que ter 14 digitos
            }

//            else if(bpai.getCmp()) {
//
//            } else if(bpai.getCnsmed()) {
//
//            } else if(bpai.getCb()) {
//
//            } else if(bpai.getDtaten()) {
//
//            } else if(bpai.getFl()) {
//
//            } else if(bpai.getSe()) {
//
//            } else if(bpai.getPa()) {
//
//            } else if(bpai.getCnspac()) {
//
//            } else if(validsSex.contains(bpai.getSexo().toUpperCase())) {
//
//            } else if(bpai.getIbge()) {
//
//            } else if(bpai.getCid()) {
//
//            } else if(bpai.getIdad()) {
//
//            } else if(bpai.getQ()) {
//
//            } else if(bpai.getCaten()) {
//
//            } else if(bpai.getNaut()) {
//
//            } else if(bpai.getOrg()) {
//
//            } else if(bpai.getNmpac()) {
//
//            } else if(bpai.getDtnasc()) {
//
//            } else if(bpai.getRaca()) {
//
//            } else if(bpai.getEtnia()) {
//
//            } else if(bpai.getNac()) {
//
//            } else if(bpai.getSrv()) {
//
//            } else if(bpai.getClf()) {
//
//            } else if(bpai.getEquipeSeq()) {
//
//            } else if(bpai.getEquipeArea()) {
//
//            } else if(bpai.getCnpj()) {
//
//            } else if(bpai.getCepPcnte()) {
//
//            } else if(bpai.getLogradPcnte()) {
//
//            } else if(bpai.getEndPcnte()) {
//
//            } else if(bpai.getComplPcnte()) {
//
//            } else if(bpai.getNumPcnte()) {
//
//            } else if(bpai.getBairroPcnte()) {
//
//            } else if(bpai.getDdtelPcnte()) {
//
//            } else if(bpai.getEmailPcnte()) {
//
//            } else if(bpai.getIne()) {
//
//            }
        });





        return "";
    }
    public static void main(String[] args) {
        List<Integer> validsIdent = Arrays.asList(1, 2, 3);

        if(validsIdent.contains(Integer.valueOf("01"))) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}

