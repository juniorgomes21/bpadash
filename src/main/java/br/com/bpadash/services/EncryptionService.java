package br.com.bpadash.services;

import br.com.bpadash.model.Bpai;
import br.com.bpadash.model.ProfessionalComplete;
import io.github.cdimascio.dotenv.Dotenv;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class EncryptionService {

    public static String encrypt(String data) {
        Dotenv dotenv = Dotenv.load();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));
        return encryptor.encrypt(data);
    }

    public static void encryptBpai(List<Bpai> bpaiList) {
        Dotenv dotenv = Dotenv.load();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        bpaiList.forEach(bpai -> {
            bpai.setCnspac(encryptor.encrypt(bpai.getCnspac()));
            bpai.setCid(encryptor.encrypt(bpai.getCid()));
            bpai.setNmpac(encryptor.encrypt(bpai.getNmpac()));
            bpai.setIdade(encryptor.encrypt(bpai.getIdade()));
            bpai.setDtnasc(encryptor.encrypt(bpai.getDtnasc()));
            bpai.setCepPcnte(encryptor.encrypt(bpai.getCepPcnte()));
            bpai.setLogradPcnte(encryptor.encrypt(bpai.getLogradPcnte()));
            bpai.setEndPcnte(encryptor.encrypt(bpai.getEndPcnte()));
            bpai.setComplPcnte(encryptor.encrypt(bpai.getComplPcnte()));
            bpai.setNumPcnte(encryptor.encrypt(bpai.getNumPcnte()));
            bpai.setSexo(encryptor.encrypt(bpai.getSexo()));
            bpai.setRaca(encryptor.encrypt(bpai.getRaca()));
            bpai.setBairroPcnte(encryptor.encrypt(bpai.getBairroPcnte()));
            bpai.setDdtelPcnte(encryptor.encrypt(bpai.getDdtelPcnte()));
            bpai.setEmailPcnte(encryptor.encrypt(bpai.getEmailPcnte()));
        });
    }

    public static void decryptBpai(List<Bpai> bpaiListDB) {
        Dotenv dotenv = Dotenv.load();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        bpaiListDB.forEach(bpai -> {
            bpai.setCnspac(encryptor.decrypt(bpai.getCnspac()));
            bpai.setCid(encryptor.decrypt(bpai.getCid()));
            bpai.setNmpac(encryptor.decrypt(bpai.getNmpac()));
            bpai.setDtnasc(encryptor.decrypt(bpai.getDtnasc()));
            bpai.setCepPcnte(encryptor.decrypt(bpai.getCepPcnte()));
            bpai.setLogradPcnte(encryptor.decrypt(bpai.getLogradPcnte()));
            bpai.setEndPcnte(encryptor.decrypt(bpai.getEndPcnte()));
            bpai.setComplPcnte(encryptor.decrypt(bpai.getComplPcnte()));
            bpai.setNumPcnte(encryptor.decrypt(bpai.getNumPcnte()));
            bpai.setSexo(encryptor.decrypt(bpai.getSexo()));
            bpai.setRaca(encryptor.decrypt(bpai.getRaca()));
            bpai.setBairroPcnte(encryptor.decrypt(bpai.getBairroPcnte()));
            bpai.setDdtelPcnte(encryptor.decrypt(bpai.getDdtelPcnte()));
            bpai.setEmailPcnte(encryptor.decrypt(bpai.getEmailPcnte()));
        });
    }

    public static void decryptBpaiCep(List<Bpai> bpaiListDB) {
        Dotenv dotenv = Dotenv.load();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        bpaiListDB.forEach(bpai -> {
            bpai.setCepPcnte(encryptor.decrypt(bpai.getCepPcnte()));
        });
    }

    public static void decryptBpaiDtNasc(List<Bpai> bpaiListDB) {
        Dotenv dotenv = Dotenv.load();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        bpaiListDB.forEach(bpai -> {
            bpai.setDtnasc(encryptor.decrypt(bpai.getDtnasc()));
        });
    }

    public static void decryptBpaiSex(List<Bpai> bpaiListDB) {
        Dotenv dotenv = Dotenv.load();

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        bpaiListDB.forEach(bpai -> {
            bpai.setSexo(encryptor.decrypt(bpai.getSexo()));
        });
    }

    public static void decryptBpaiIdade(List<Bpai> bpaiListDB) {
        Dotenv dotenv = Dotenv.load();

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        bpaiListDB.forEach(bpai -> {
            bpai.setIdade(encryptor.decrypt(bpai.getIdade()));
        });
    }

    public static void encryptRace(List<Bpai> bpaiListDB) {
        Dotenv dotenv = Dotenv.load();

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        bpaiListDB.forEach( bpai -> {
            bpai.setRaca(encryptor.encrypt(bpai.getRaca()));
        });
    }

    public static void decryptRace(List<Bpai> bpaiListDB) {
        Dotenv dotenv = Dotenv.load();

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        bpaiListDB.forEach( bpai -> {
            bpai.setRaca(encryptor.decrypt(bpai.getRaca()));
        });
    }

    public static void decryptProfessionalCns(List<ProfessionalComplete> professionalCompleteList) {
        Dotenv dotenv = Dotenv.load();

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        professionalCompleteList.forEach(bpai -> {
            bpai.setCodCns(encryptor.decrypt(bpai.getCodCns()));
        });
    }

    public static void encrypt(List<ProfessionalComplete> list) {
        Dotenv dotenv = Dotenv.load();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));


        list.forEach(professional -> {
            professional.setProfId(encryptor.encrypt(professional.getProfId()));
            professional.setKeyProfId(hashString(professional.getProfId()));
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
            professional.setTelephone(professional.getTelephone().isBlank() ? professional.getTelephone() : encryptor.encrypt(professional.getTelephone()));
//            professional.getDadosVinc().setCodCbo(encryptor.encrypt(professional.getDadosVinc().getCodCbo()));
        });
    }

    public static void decrypt(List<ProfessionalComplete> list) {
        Dotenv dotenv = Dotenv.load();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        list.forEach(professional -> {
            professional.setProfId(encryptor.decrypt(professional.getProfId()));
            professional.setName(encryptor.decrypt(professional.getName()));
            professional.setCpf(encryptor.decrypt(professional.getCpf()));
            professional.setLogradouro(encryptor.decrypt(professional.getLogradouro()));
            professional.setNumber(encryptor.decrypt(professional.getNumber()));
            professional.setComplement(encryptor.decrypt(professional.getComplement()));
            professional.setBairrodist(encryptor.decrypt(professional.getBairrodist()));
            professional.setCodCep(encryptor.decrypt(professional.getCodCep()));
            professional.setCodCns(encryptor.decrypt(professional.getCodCns()));
            professional.getDadosVinc().setCodCbo(encryptor.decrypt(professional.getDadosVinc().getCodCbo()));
            professional.setTelephone(encryptor.decrypt(professional.getTelephone()));
        });
    }

    public static String decrypt(String encryptedData) {
        Dotenv dotenv = Dotenv.load();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        return encryptor.decrypt(encryptedData);
    }

    public static String hashString(String s) {
        Dotenv dotenv = Dotenv.load();
        try {
            MessageDigest md = MessageDigest.getInstance(dotenv.get("ENCODE_TYPE"));
            byte[] emailBytes = s.getBytes();
            byte[] emailHashBytes = md.digest(emailBytes);
            StringBuilder stringBuilder = new StringBuilder();

            for (byte b : emailHashBytes) {
                stringBuilder.append(String.format("%02x", b));
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
