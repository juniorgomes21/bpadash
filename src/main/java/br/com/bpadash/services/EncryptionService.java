package br.com.bpadash.services;

import br.com.bpadash.model.bpa.Address;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.user.AddressUser;
import br.com.bpadash.model.user.User;
import br.com.bpadash.model.sigtap.ProfessionalComplete;
import io.github.cdimascio.dotenv.Dotenv;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class EncryptionService {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String ENCODE_KEY = dotenv.get("ENCODE_KEY");
    private static final String ENCODE_KEY_USER = dotenv.get("ENCODE_KEY_USER");
    private static final String ENCODE_ALGORITHM = dotenv.get("ENCODE_ALGORITHM");

    private static final StandardPBEStringEncryptor encryptor;

    static {
        // Inicialize a instância de encryptor no bloco estático
        encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(ENCODE_KEY);
        encryptor.setAlgorithm(ENCODE_ALGORITHM);
    }


    public static String encrypt(String data) {
        return encryptor.encrypt(data);
    }

    public static void encryptBpai(List<Bpai> bpaiList) {

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

    public static void decryptBpai(List<Bpai> bpaiListDB, boolean isCnsPac) {

        bpaiListDB.forEach(bpai -> {
            if(isCnsPac) bpai.setCnspac(bpai.getCnspac().isBlank() ? bpai.getCnspac() : encryptor.decrypt(bpai.getCnspac()));
            bpai.setCid(bpai.getCid().isBlank() ? bpai.getCid() : encryptor.decrypt(bpai.getCid()));
            bpai.setNmpac(bpai.getNmpac().isBlank() ? bpai.getNmpac() : encryptor.decrypt(bpai.getNmpac()));
            if(isCnsPac) bpai.setDtnasc(bpai.getDtnasc().isBlank() ? bpai.getDtnasc() : encryptor.decrypt(bpai.getDtnasc()));
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

    public static void decryptBpaiForTreatment(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach(bpai -> {
            bpai.setCnspac(bpai.getCnspac().isBlank() ? bpai.getCnspac() : encryptor.decrypt(bpai.getCnspac()));
            bpai.setCid(bpai.getCid().isBlank() ? bpai.getCid() : encryptor.decrypt(bpai.getCid()));
            bpai.setNmpac(bpai.getNmpac().isBlank() ? bpai.getNmpac() : encryptor.decrypt(bpai.getNmpac()));
            bpai.setDtnasc(bpai.getDtnasc().isBlank() ? bpai.getDtnasc() : encryptor.decrypt(bpai.getDtnasc()));
            bpai.setIdade(bpai.getIdade().isBlank() ? bpai.getIdade() : encryptor.decrypt(bpai.getIdade()));
            bpai.setSexo(bpai.getSexo().isBlank() ? bpai.getSexo() : encryptor.decrypt(bpai.getSexo()));
            bpai.setRaca(bpai.getRaca().isBlank() ? bpai.getRaca() : encryptor.decrypt(bpai.getRaca()));
        });
    }

    public static void encryptBpaiForTreatment(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach(bpai -> {
            bpai.setCnspac(bpai.getCnspac().isBlank() ? bpai.getCnspac() : encryptor.encrypt(bpai.getCnspac()));
            bpai.setCid(bpai.getCid().isBlank() ? bpai.getCid() : encryptor.encrypt(bpai.getCid()));
            bpai.setNmpac(bpai.getNmpac().isBlank() ? bpai.getNmpac() : encryptor.encrypt(bpai.getNmpac()));
            bpai.setDtnasc(bpai.getDtnasc().isBlank() ? bpai.getDtnasc() : encryptor.encrypt(bpai.getDtnasc()));
            bpai.setIdade(bpai.getIdade().isBlank() ? bpai.getIdade() : encryptor.encrypt(bpai.getIdade()));
            bpai.setSexo(bpai.getSexo().isBlank() ? bpai.getSexo() : encryptor.encrypt(bpai.getSexo()));
            bpai.setRaca(bpai.getRaca().isBlank() ? bpai.getRaca() : encryptor.encrypt(bpai.getRaca()));
        });
    }

    public static void decryptBpaiCep(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach( bpai -> {
            if(!bpai.getCepPcnte().isBlank()) bpai.setCepPcnte(encryptor.decrypt(bpai.getCepPcnte()));
        });
    }

    public static void decryptBpaiAddress(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach(bpai -> {
            if(!bpai.getCepPcnte().isBlank()) bpai.setCepPcnte(encryptor.decrypt(bpai.getCepPcnte()));
            if(!bpai.getLogradPcnte().isBlank()) bpai.setLogradPcnte(encryptor.decrypt(bpai.getLogradPcnte()));
            if(!bpai.getComplPcnte().isBlank()) bpai.setComplPcnte(encryptor.decrypt(bpai.getComplPcnte()));
            if(!bpai.getEndPcnte().isBlank()) bpai.setEndPcnte(encryptor.decrypt(bpai.getEndPcnte()));
            if(!bpai.getBairroPcnte().isBlank()) bpai.setBairroPcnte(encryptor.decrypt(bpai.getBairroPcnte()));
        });
    }

    public static void encryptBpaiAddress(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach(bpai -> {
            if(!bpai.getCepPcnte().isBlank()) bpai.setCepPcnte(encryptor.encrypt(bpai.getCepPcnte()));
            if(!bpai.getLogradPcnte().isBlank()) bpai.setLogradPcnte(encryptor.encrypt(bpai.getLogradPcnte()));
            if(!bpai.getComplPcnte().isBlank()) bpai.setComplPcnte(encryptor.encrypt(bpai.getComplPcnte()));
            if(!bpai.getEndPcnte().isBlank()) bpai.setEndPcnte(encryptor.encrypt(bpai.getEndPcnte()));
            if(!bpai.getBairroPcnte().isBlank()) bpai.setBairroPcnte(encryptor.encrypt(bpai.getBairroPcnte()));
        });
    }

    public static void encryptAddressUser(Address address) {
        if(!address.getCep().isBlank()) address.setCep(encryptor.encrypt(address.getCep()));
        if(!address.getCodLograud().isBlank()) address.setCodLograud(encryptor.encrypt(address.getCodLograud()));
        if(!address.getComplemento().isBlank()) address.setComplemento(encryptor.encrypt(address.getComplemento()));
        if(!address.getLogradouro().isBlank()) address.setLogradouro(encryptor.encrypt(address.getLogradouro()));
        if(!address.getBairro().isBlank()) address.setBairro(encryptor.encrypt(address.getBairro()));
        // NOT USED
        if(!address.getLocalidade().isBlank()) address.setLocalidade(encryptor.encrypt(address.getLocalidade()));
        if(!address.getUf().isBlank()) address.setUf(encryptor.encrypt(address.getUf()));
        if(!address.getIbge().isBlank()) address.setIbge(encryptor.encrypt(address.getIbge()));
        if(!address.getGia().isBlank()) address.setGia(encryptor.encrypt(address.getGia()));
        if(!address.getDdd().isBlank()) address.setDdd(encryptor.encrypt(address.getDdd()));
        if(!address.getSiafi().isBlank()) address.setSiafi(encryptor.encrypt(address.getSiafi()));

    }

    public static AddressUser decryptAddressUser(AddressUser address) {
        if(!address.getCep().isBlank()) address.setCep(encryptor.decrypt(address.getCep()));
        if(!address.getComplemento().isBlank()) address.setComplemento(encryptor.decrypt(address.getComplemento()));
        if(!address.getLogradouro().isBlank()) address.setLogradouro(encryptor.decrypt(address.getLogradouro()));
        if(!address.getLocalidade().isBlank()) address.setLocalidade(encryptor.decrypt(address.getLocalidade()));
        if(!address.getUf().isBlank()) address.setUf(encryptor.decrypt(address.getUf()));
        if(!address.getBairro().isBlank()) address.setBairro(encryptor.decrypt(address.getBairro()));

        return address;
    }

    public static void decryptBpaiDtNasc(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach(bpai -> {
            try {
                if(!bpai.getDtnasc().isBlank()) bpai.setDtnasc(encryptor.decrypt(bpai.getDtnasc()));
            } catch (EncryptionOperationNotPossibleException e) {
                System.out.println(bpai.getId());
            }
        });
    }

    public static void decryptSex(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach(bpai -> {
            bpai.setSexo(encryptor.decrypt(bpai.getSexo()));
        });
    }

    public static void encryptSex(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach(bpai -> {
            bpai.setSexo(encryptor.encrypt(bpai.getSexo()));
        });
    }

    public static void decryptBpaiIdade(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach(bpai -> {
            if(!bpai.getIdade().isBlank()) bpai.setIdade(encryptor.decrypt(bpai.getIdade()));
        });
    }

    public static void decryptBpaiIdadeAndDtnasc(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach(bpai -> {
            if(!bpai.getIdade().isBlank()) bpai.setIdade(encryptor.decrypt(bpai.getIdade()));
            if(!bpai.getDtnasc().isBlank()) bpai.setDtnasc(encryptor.decrypt(bpai.getDtnasc()));
        });
    }

    public static void encryptBpaiIdadeAndDtnasc(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach(bpai -> {
            bpai.setIdade(encryptor.encrypt(bpai.getIdade()));
            bpai.setDtnasc(encryptor.encrypt(bpai.getDtnasc()));
        });
    }

    public static void decryptRace(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach( bpai -> {
            bpai.setRaca(encryptor.decrypt(bpai.getRaca()));
        });
    }

    public static void decryptCnsPac(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach( bpai -> {
            bpai.setDtnasc(encryptor.decrypt(bpai.getDtnasc()));
            bpai.setCnspac(encryptor.decrypt(bpai.getCnspac()));
        });
    }

    public static void encryptCnsPac(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach( bpai -> {
            if(!bpai.getCnspac().isBlank()) bpai.setCnspac(encryptor.encrypt(bpai.getCnspac()));;
        });
    }

    public static void decryptProfessionalCns(List<ProfessionalComplete> professionalCompleteList) {
        professionalCompleteList.forEach(bpai -> {
            bpai.setCodCns(encryptor.decrypt(bpai.getCodCns()));
        });
    }

    public static void encrypt(List<ProfessionalComplete> list) {
        list.forEach( professional -> {
            String profId = professional.getProfId();
            String codCns = professional.getCodCns();
            String name = professional.getName();

            professional.setProfId(encryptor.encrypt(profId));
            professional.setKeyProfId(hashString(profId));
            professional.setCpf(encryptor.encrypt(professional.getCpf()));
            professional.setName(encryptor.encrypt(name));
            professional.setKeyName(hashString(name));
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
            professional.setCodCns(encryptor.encrypt(codCns));
            professional.setKeyCodCns(hashString(codCns));
            professional.setUser(encryptor.encrypt(professional.getUser()));
            professional.setCdRaca(encryptor.encrypt(professional.getCdRaca()));
            professional.setNameFather(encryptor.encrypt(professional.getNameFather()));
            professional.getDadosVinc().setCodCbo(encryptor.encrypt(professional.getDadosVinc().getCodCbo()));
            professional.setTelephone(professional.getTelephone().isBlank() ? professional.getTelephone() : encryptor.encrypt(professional.getTelephone()));
        });
    }

    public static void decrypt(List<ProfessionalComplete> list) {
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
            professional.getDadosVinc().setCodCbo(encryptor.decrypt(professional.getDadosVinc().getCodCbo()));
            professional.setTelephone(professional.getTelephone().isBlank() ? professional.getTelephone() : encryptor.decrypt(professional.getTelephone()));
        });
    }

    public static void encryptUser(User user) {
        user.setName(encryptor.encrypt(user.getName()));
        user.setCnpj(encryptor.encrypt(user.getCnpj()));
        user.setKeyCnpj(hashString(user.getCnpj()));
        user.setPackageNameUser(encryptor.encrypt(user.getPackageNameUser()));
        user.setCell(encryptor.encrypt(user.getCell()));
        user.setEmail(encryptor.encrypt(user.getEmail()));
        user.setKeyEmail(hashString(user.getEmail()));

    }

    public static String decrypt(String encryptedData) {
        return encryptedData.isBlank() ? encryptedData : encryptor.decrypt(encryptedData);
    }



    public static String hashString(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance(dotenv.get("ENCODE_TYPE"));
            byte[] emailHashBytes = md.digest(s.getBytes());
            StringBuilder stringBuilder = new StringBuilder();

            for (byte b : emailHashBytes) {
                stringBuilder.append(String.format("%02x", b));
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static Long encryptKeyUser(Long id) {
        return id + Long.parseLong(ENCODE_KEY_USER);
    }
}
