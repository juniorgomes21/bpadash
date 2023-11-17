package br.com.bpadash.services;

import br.com.bpadash.model.Bpai;
import br.com.bpadash.model.ProfessionalComplete;
import io.github.cdimascio.dotenv.Dotenv;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.util.List;

public class EncryptionService {

    public static String encrypt(String data) {
        Dotenv dotenv = Dotenv.load();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));
        return encryptor.encrypt(data);
    }

    public static void encryptBpai(List<Bpai> list) {
        Dotenv dotenv = Dotenv.load();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        list.forEach(bpai -> {
            bpai.setCnspac(encryptor.encrypt(bpai.getCnspac()));
            bpai.setCid(encryptor.encrypt(bpai.getCid()));
            bpai.setNmpac(encryptor.encrypt(bpai.getNmpac()));
            bpai.setDtnasc(encryptor.encrypt(bpai.getDtnasc()));
            bpai.setCepPcnte(encryptor.encrypt(bpai.getCepPcnte()));
            bpai.setLogradPcnte(encryptor.encrypt(bpai.getLogradPcnte()));
            bpai.setEndPcnte(encryptor.encrypt(bpai.getEndPcnte()));
            bpai.setComplPcnte(encryptor.encrypt(bpai.getComplPcnte()));
            bpai.setNumPcnte(encryptor.encrypt(bpai.getNumPcnte()));
            bpai.setSexo(encryptor.encrypt(bpai.getSexo()));
            bpai.setBairroPcnte(encryptor.encrypt(bpai.getBairroPcnte()));
            bpai.setDdtelPcnte(encryptor.encrypt(bpai.getDdtelPcnte()));
            bpai.setEmailPcnte(encryptor.encrypt(bpai.getEmailPcnte()));
        });
    }

    public static void encrypt(List<ProfessionalComplete> list) {
        Dotenv dotenv = Dotenv.load();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        list.forEach(professional -> {
            professional.setCpf(encryptor.encrypt(professional.getCpf()));
            professional.setName(encryptor.encrypt(professional.getName()));
            professional.setNameMother(encryptor.encrypt(professional.getNameMother()));
            professional.setBirthDate(encryptor.encrypt(professional.getBirthDate()));
            professional.setSexo(encryptor.encrypt(professional.getSexo()));
            professional.setLogradouro(encryptor.encrypt(professional.getLogradouro()));
            professional.setNumber(encryptor.encrypt(professional.getNumber()));
            professional.setComplement(encryptor.encrypt(professional.getComplement()));
            professional.setBairrodist(encryptor.encrypt(professional.getBairrodist()));
            professional.setCodCep(encryptor.encrypt(professional.getCodCep()));
            professional.setNumAgenc(encryptor.encrypt(professional.getNumAgenc()));
            professional.setContaCc(encryptor.encrypt(professional.getContaCc()));
            professional.setCodCns(encryptor.encrypt(professional.getCodCns()));
            professional.setUser(encryptor.encrypt(professional.getUser()));
            professional.setCdRaca(encryptor.encrypt(professional.getCdRaca()));
            professional.setNameFather(encryptor.encrypt(professional.getNameFather()));
        });
    }

    public static String decrypt(String encryptedData) {
        Dotenv dotenv = Dotenv.load();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));
        return encryptor.decrypt(encryptedData);
    }
}
