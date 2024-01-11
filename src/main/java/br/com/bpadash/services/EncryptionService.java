package br.com.bpadash.services;

import br.com.bpadash.model.Bpai;
import br.com.bpadash.model.ProfessionalComplete;
import io.github.cdimascio.dotenv.Dotenv;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

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
            String cnspac = bpai.getCnspac();

            bpai.setCnspac(encryptor.encrypt(cnspac));
            bpai.setCnspacHas(cnspac.isBlank() ? "" : hashString(cnspac));
            bpai.setCid(bpai.getCid().isBlank() ? bpai.getCid() : encryptor.encrypt(bpai.getCid()));
            bpai.setNmpac(bpai.getNmpac().isBlank() ? bpai.getNmpac() : encryptor.encrypt(bpai.getNmpac()));
            bpai.setIdade(bpai.getIdade().isBlank() ? bpai.getIdade() : encryptor.encrypt(bpai.getIdade()));
            bpai.setDtnasc(bpai.getDtnasc().isBlank() ? bpai.getDtnasc() : encryptor.encrypt(bpai.getDtnasc()));
            bpai.setCepPcnte(bpai.getCepPcnte().isBlank() ? bpai.getCepPcnte() : encryptor.encrypt(bpai.getCepPcnte()));
            bpai.setLogradPcnte(bpai.getLogradPcnte().isBlank() ? bpai.getLogradPcnte() : encryptor.encrypt(bpai.getLogradPcnte()));
            bpai.setEndPcnte(bpai.getEndPcnte().isBlank() ? bpai.getEndPcnte() : encryptor.encrypt(bpai.getEndPcnte()));
            bpai.setComplPcnte(bpai.getComplPcnte().isBlank() ? bpai.getComplPcnte() : encryptor.encrypt(bpai.getComplPcnte()));
            bpai.setNumPcnte(bpai.getNumPcnte().isBlank() ? bpai.getNumPcnte() : encryptor.encrypt(bpai.getNumPcnte()));
            bpai.setSexo(bpai.getSexo().isBlank() ? bpai.getSexo() : encryptor.encrypt(bpai.getSexo()));
            bpai.setRaca(bpai.getRaca().isBlank() ? bpai.getRaca() : encryptor.encrypt(bpai.getRaca()));
            bpai.setBairroPcnte(bpai.getBairroPcnte().isBlank() ? bpai.getBairroPcnte() : encryptor.encrypt(bpai.getBairroPcnte()));
            bpai.setDdtelPcnte(bpai.getDdtelPcnte().isBlank() ? bpai.getDdtelPcnte() : encryptor.encrypt(bpai.getDdtelPcnte()));
            bpai.setEmailPcnte(bpai.getEmailPcnte().isBlank() ? bpai.getEmailPcnte() : encryptor.encrypt(bpai.getEmailPcnte()));
        });
    }

    public static void decryptBpai(List<Bpai> bpaiListDB) {
        Dotenv dotenv = Dotenv.load();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        bpaiListDB.forEach(bpai -> {
            bpai.setCnspac(bpai.getCnspac().isBlank() ? bpai.getCnspac() : encryptor.decrypt(bpai.getCnspac()));
            bpai.setCid(bpai.getCid().isBlank() ? bpai.getCid() : encryptor.decrypt(bpai.getCid()));
            bpai.setNmpac(bpai.getNmpac().isBlank() ? bpai.getNmpac() : encryptor.decrypt(bpai.getNmpac()));
            bpai.setDtnasc(bpai.getDtnasc().isBlank() ? bpai.getDtnasc() : encryptor.decrypt(bpai.getDtnasc()));
            bpai.setIdade(bpai.getIdade().isBlank() ? bpai.getIdade() : encryptor.decrypt(bpai.getIdade()));
            bpai.setCepPcnte(bpai.getCepPcnte().isBlank() ? bpai.getCepPcnte() : encryptor.decrypt(bpai.getCepPcnte()));
            bpai.setLogradPcnte(bpai.getLogradPcnte().isBlank() ? bpai.getLogradPcnte() : encryptor.decrypt(bpai.getLogradPcnte()));
            bpai.setEndPcnte(bpai.getEndPcnte().isBlank() ? bpai.getEndPcnte() : encryptor.decrypt(bpai.getEndPcnte()));
            bpai.setComplPcnte(bpai.getComplPcnte().isBlank() ? bpai.getComplPcnte() : encryptor.decrypt(bpai.getComplPcnte()));
            bpai.setNumPcnte(bpai.getNumPcnte().isBlank() ? bpai.getNumPcnte() : encryptor.decrypt(bpai.getNumPcnte()));
            bpai.setSexo(bpai.getSexo().isBlank() ? bpai.getSexo() : encryptor.decrypt(bpai.getSexo()));
            bpai.setRaca(bpai.getRaca().isBlank() ? bpai.getRaca() : encryptor.decrypt(bpai.getRaca()));
            bpai.setBairroPcnte(bpai.getBairroPcnte().isBlank() ? bpai.getBairroPcnte() : encryptor.decrypt(bpai.getBairroPcnte()));
            bpai.setDdtelPcnte(bpai.getDdtelPcnte().isBlank() ? bpai.getDdtelPcnte() : encryptor.decrypt(bpai.getDdtelPcnte()));
            bpai.setEmailPcnte(bpai.getEmailPcnte().isBlank() ? bpai.getEmailPcnte() : encryptor.decrypt(bpai.getEmailPcnte()));
        });
    }


    public static void decryptBpaiCep(List<Bpai> bpaiListDB) {
        Dotenv dotenv = Dotenv.load();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        bpaiListDB.forEach( bpai -> {
            if(!bpai.getCepPcnte().isBlank()) bpai.setCepPcnte(encryptor.decrypt(bpai.getCepPcnte()));
        });
    }

    public static void decryptBpaiAddress(List<Bpai> bpaiListDB) {
        Dotenv dotenv = Dotenv.load();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        bpaiListDB.forEach(bpai -> {
            if(!bpai.getCepPcnte().isBlank()) bpai.setCepPcnte(encryptor.decrypt(bpai.getCepPcnte()));
            if(!bpai.getLogradPcnte().isBlank()) bpai.setLogradPcnte(encryptor.decrypt(bpai.getLogradPcnte()));
            if(!bpai.getComplPcnte().isBlank()) bpai.setComplPcnte(encryptor.decrypt(bpai.getComplPcnte()));
            if(!bpai.getEndPcnte().isBlank()) bpai.setEndPcnte(encryptor.decrypt(bpai.getEndPcnte()));
            if(!bpai.getBairroPcnte().isBlank()) bpai.setBairroPcnte(encryptor.decrypt(bpai.getBairroPcnte()));
        });
    }

    public static void decryptBpaiDtNasc(List<Bpai> bpaiListDB) {
        Dotenv dotenv = Dotenv.load();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        bpaiListDB.forEach(bpai -> {
            try {
                if(!bpai.getDtnasc().isBlank()) bpai.setDtnasc(encryptor.decrypt(bpai.getDtnasc()));
            } catch (EncryptionOperationNotPossibleException e) {
                System.out.println(bpai.getId());
            }
        });
    }

    public static void decryptSex(List<Bpai> bpaiListDB) {
        Dotenv dotenv = Dotenv.load();

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        bpaiListDB.forEach(bpai -> {
            bpai.setSexo(encryptor.decrypt(bpai.getSexo()));
        });
    }

    public static void encryptSex(List<Bpai> bpaiListDB) {
        Dotenv dotenv = Dotenv.load();

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        bpaiListDB.forEach(bpai -> {
            bpai.setSexo(encryptor.encrypt(bpai.getSexo()));
        });
    }

    public static void decryptBpaiIdade(List<Bpai> bpaiListDB) {
        Dotenv dotenv = Dotenv.load();

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        bpaiListDB.forEach(bpai -> {
            if(!bpai.getIdade().isBlank()) bpai.setIdade(encryptor.decrypt(bpai.getIdade()));
        });
    }

    public static void decryptBpaiIdadeAndDtnasc(List<Bpai> bpaiListDB) {
        Dotenv dotenv = Dotenv.load();

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        bpaiListDB.forEach(bpai -> {
            if(!bpai.getIdade().isBlank()) bpai.setIdade(encryptor.decrypt(bpai.getIdade()));
            if(!bpai.getDtnasc().isBlank()) bpai.setDtnasc(encryptor.decrypt(bpai.getDtnasc()));
        });
    }

    public static void encryptBpaiIdadeAndDtnasc(List<Bpai> bpaiListDB) {
        Dotenv dotenv = Dotenv.load();

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        bpaiListDB.forEach(bpai -> {
            bpai.setIdade(encryptor.encrypt(bpai.getIdade()));
            bpai.setDtnasc(encryptor.encrypt(bpai.getDtnasc()));
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

    public static void decryptCnsPac(List<Bpai> bpaiListDB) {
        Dotenv dotenv = Dotenv.load();

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        bpaiListDB.forEach( bpai -> {
            bpai.setDtnasc(encryptor.decrypt(bpai.getDtnasc()));
            bpai.setCnspac(encryptor.decrypt(bpai.getCnspac()));
        });
    }

    public static void encryptCnsPac(List<Bpai> bpaiListDB) {
        Dotenv dotenv = Dotenv.load();

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        bpaiListDB.forEach( bpai -> {
            if(!bpai.getCnspac().isBlank()) bpai.setCnspac(encryptor.encrypt(bpai.getCnspac()));;
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


        list.forEach( professional -> {
            String profId = professional.getProfId();

            professional.setProfId(encryptor.encrypt(profId));
            professional.setKeyProfId(hashString(profId));
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
            professional.setNumber(encryptor.decrypt(professional.getNumber()));
            professional.setLogradouro(encryptor.decrypt(professional.getLogradouro()));
            professional.setNameMother(encryptor.decrypt(professional.getNameMother()));
            professional.setBirthDate(encryptor.decrypt(professional.getBirthDate()));
            professional.setSexo(encryptor.decrypt(professional.getSexo()));
            professional.setComplement(encryptor.decrypt(professional.getComplement()));
            professional.setBairrodist(encryptor.decrypt(professional.getBairrodist()));
            professional.setCodCep(encryptor.decrypt(professional.getCodCep()));
            professional.setNumAgenc(encryptor.decrypt(professional.getNumAgenc()));
            professional.setContaCc(encryptor.decrypt(professional.getContaCc()));
            professional.setCodCns(encryptor.decrypt(professional.getCodCns()));
            professional.setUser(encryptor.decrypt(professional.getUser()));
            professional.setCdRaca(encryptor.decrypt(professional.getCdRaca()));
            professional.setNameFather(encryptor.decrypt(professional.getNameFather()));
            professional.setTelephone(professional.getTelephone().isBlank() ? professional.getTelephone() : encryptor.decrypt(professional.getTelephone()));
        });
    }

    public static String decrypt(String encryptedData) {
        Dotenv dotenv = Dotenv.load();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));

        return encryptedData.isBlank() ? encryptedData : encryptor.decrypt(encryptedData);
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
