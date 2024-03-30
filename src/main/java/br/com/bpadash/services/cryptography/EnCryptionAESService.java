package br.com.bpadash.services.cryptography;

import br.com.bpadash.model.bpa.Address;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.sigtap.ProfessionalComplete;
import br.com.bpadash.model.user.AddressUser;
import io.github.cdimascio.dotenv.Dotenv;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EnCryptionAESService {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String ALGORITHM = dotenv.get("ENCODE_KEY_ALGORITHM");
    private static final String ENCODE_KEY_USER = dotenv.get("ENCODE_KEY_USER");
    private static final String SECRET_KEY = dotenv.get("SECRET_KEY");
    private static final SecretKey secretKey = loadAESKey();
    private static final Cipher cipher;


    static {
        try {
            cipher = Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public static SecretKey loadAESKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    public static String encrypt(String data) {

        try {
            if (data != null && !data.isBlank()) {
                byte[] encryptedBytes;
                try {
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                    encryptedBytes = cipher.doFinal(data.getBytes());
                } catch (Exception e) {
                    Cipher cipherNew = Cipher.getInstance(ALGORITHM);
                    cipherNew.init(Cipher.ENCRYPT_MODE, secretKey);
                    encryptedBytes = cipherNew.doFinal(data.getBytes());
                }

                return Base64.getEncoder().encodeToString(encryptedBytes);
            }
        } catch (Exception e) {
            System.out.println("encrypt()");
            e.printStackTrace();
        }

        return data;
    }

    public static String decrypt(String data) {
        try {
            if (data != null && !data.isBlank()) {
                byte[] decryptedBytes;

                try {
                    cipher.init(Cipher.DECRYPT_MODE, secretKey);
                    decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(data));
                } catch (Exception e) {
                    Cipher cipherNew = Cipher.getInstance(ALGORITHM);
                    cipherNew.init(Cipher.DECRYPT_MODE, secretKey);
                    decryptedBytes = cipherNew.doFinal(Base64.getDecoder().decode(data));
                }

                String x = new String(decryptedBytes);
                return x;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public static void encryptBpaiInitial(List<Bpai> bpaiList) {
        bpaiList.forEach(bpai -> {
            bpai.setCnspac(encrypt(bpai.getCnspac()));
            bpai.setCnspacHas(EnCryptionAESService.hashString(bpai.getCnspac()));
            bpai.setCnsmed(encrypt(bpai.getCnsmed()));
            bpai.setIbge(encrypt(bpai.getIbge()));
            bpai.setFlh(encrypt(bpai.getFlh()));
            bpai.setSeq(encrypt(bpai.getSeq()));
            bpai.setCid(encrypt(bpai.getCid()));
            bpai.setNmpac(encrypt(bpai.getNmpac()));
            bpai.setIdade(encrypt(bpai.getIdade()));
            bpai.setDtaten(encrypt(bpai.getDtaten()));
            bpai.setDtnasc(encrypt(bpai.getDtnasc()));
            bpai.setCepPcnte(encrypt(bpai.getCepPcnte()));
            bpai.setLogradPcnte(encrypt(bpai.getLogradPcnte()));
            bpai.setEndPcnte(encrypt(bpai.getEndPcnte()));
            bpai.setComplPcnte(encrypt(bpai.getComplPcnte()));
            bpai.setNumPcnte(encrypt(bpai.getNumPcnte()));
            bpai.setSexo(encrypt(bpai.getSexo()));
            bpai.setRaca(encrypt(bpai.getRaca()));
            bpai.setBairroPcnte(encrypt(bpai.getBairroPcnte()));
            bpai.setDdtelPcnte(encrypt(bpai.getDdtelPcnte()));
            bpai.setEmailPcnte(encrypt(bpai.getEmailPcnte()));
        });
    }

    public static void encryptBpai(List<Bpai> bpaiListDB, boolean isCnsPac) {
        bpaiListDB.forEach( bpai -> {
            if(isCnsPac) bpai.setCnspac(encrypt(bpai.getCnspac()));
            bpai.setCid(encrypt(bpai.getCid()));
            bpai.setNmpac(encrypt(bpai.getNmpac()));
            bpai.setIbge(encrypt(bpai.getIbge()));
            bpai.setFlh(encrypt(bpai.getFlh()));
            bpai.setSeq(encrypt(bpai.getSeq()));
            if(isCnsPac) bpai.setDtnasc(encrypt(bpai.getDtnasc()));
            bpai.setCnsmed(encrypt(bpai.getCnsmed()));
            bpai.setDtaten(encrypt(bpai.getDtaten()));
            bpai.setIdade(encrypt(bpai.getIdade()));
            bpai.setCepPcnte(encrypt(bpai.getCepPcnte()));
            bpai.setLogradPcnte(encrypt(bpai.getLogradPcnte()));
            bpai.setEndPcnte(encrypt(bpai.getEndPcnte()));
            bpai.setComplPcnte(encrypt(bpai.getComplPcnte()));
            bpai.setNumPcnte(encrypt(bpai.getNumPcnte()));
            bpai.setSexo(encrypt(bpai.getSexo()));
            bpai.setRaca(encrypt(bpai.getRaca()));
            bpai.setBairroPcnte(encrypt(bpai.getBairroPcnte()));
            bpai.setDdtelPcnte(encrypt(bpai.getDdtelPcnte()));
            bpai.setEmailPcnte(encrypt(bpai.getEmailPcnte()));
        });
    }

    public static void decryptBpai(List<Bpai> bpaiListDB, boolean isCnsPac) {
        bpaiListDB.forEach(bpai -> {
            if(isCnsPac) bpai.setCnspac(decrypt(bpai.getCnspac()));
            bpai.setCid(decrypt(bpai.getCid()));
            bpai.setNmpac(decrypt(bpai.getNmpac()));
            bpai.setIbge(decrypt(bpai.getIbge()));
            bpai.setFlh(decrypt(bpai.getFlh()));
            bpai.setSeq(decrypt(bpai.getSeq()));
            if(isCnsPac) bpai.setDtnasc(decrypt(bpai.getDtnasc()));
            bpai.setCnsmed(decrypt(bpai.getCnsmed()));
            bpai.setDtaten(decrypt(bpai.getDtaten()));
            bpai.setIdade(decrypt(bpai.getIdade()));
            bpai.setCepPcnte(decrypt(bpai.getCepPcnte()));
            bpai.setLogradPcnte(decrypt(bpai.getLogradPcnte()));
            bpai.setEndPcnte(decrypt(bpai.getEndPcnte()));
            bpai.setComplPcnte(decrypt(bpai.getComplPcnte()));
            bpai.setNumPcnte(decrypt(bpai.getNumPcnte()));
            bpai.setSexo(decrypt(bpai.getSexo()));
            bpai.setRaca(decrypt(bpai.getRaca()));
            bpai.setBairroPcnte(decrypt(bpai.getBairroPcnte()));
            bpai.setDdtelPcnte(decrypt(bpai.getDdtelPcnte()));
            bpai.setEmailPcnte(decrypt(bpai.getEmailPcnte()));
        });
    }

    public static void decryptBpaiCep(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach( bpai -> {
            bpai.setCepPcnte(decrypt(bpai.getCepPcnte()));
            bpai.setFlh(decrypt(bpai.getFlh()));
            bpai.setSeq(decrypt(bpai.getSeq()));
        });
    }

    public static void decryptBpaiAddress(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach(bpai -> {
            bpai.setFlh(decrypt(bpai.getFlh()));
            bpai.setSeq(decrypt(bpai.getSeq()));
            bpai.setCepPcnte(decrypt(bpai.getCepPcnte()));
            bpai.setLogradPcnte(decrypt(bpai.getLogradPcnte()));
            bpai.setComplPcnte(decrypt(bpai.getComplPcnte()));
            bpai.setEndPcnte(decrypt(bpai.getEndPcnte()));
            bpai.setBairroPcnte(decrypt(bpai.getBairroPcnte()));
        });
    }

    public static void encryptBpaiAddress(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach(bpai -> {
            bpai.setCepPcnte(encrypt(bpai.getCepPcnte()));
            bpai.setLogradPcnte(encrypt(bpai.getLogradPcnte()));
            bpai.setComplPcnte(encrypt(bpai.getComplPcnte()));
            bpai.setEndPcnte(encrypt(bpai.getEndPcnte()));
            bpai.setBairroPcnte(encrypt(bpai.getBairroPcnte()));
        });
    }

    public static void encryptAddressUser(Address address) {
        address.setCep(encrypt(address.getCep()));
        address.setCodLograud(encrypt(address.getCodLograud()));
        address.setComplemento(encrypt(address.getComplemento()));
        address.setLogradouro(encrypt(address.getLogradouro()));
        address.setBairro(encrypt(address.getBairro()));
        // NOT USED
        address.setLocalidade(encrypt(address.getLocalidade()));
        address.setUf(encrypt(address.getUf()));
        address.setIbge(encrypt(address.getIbge()));
        address.setGia(encrypt(address.getGia()));
        address.setDdd(encrypt(address.getDdd()));
        address.setSiafi(encrypt(address.getSiafi()));
    }

    public static AddressUser decryptAddressUser(AddressUser address) {
        address.setCep(decrypt(address.getCep()));
        address.setLogradouro(decrypt(address.getLogradouro()));
        address.setLocalidade(decrypt(address.getLocalidade()));
        address.setUf(decrypt(address.getUf()));
        address.setBairro(decrypt(address.getBairro()));

        return address;
    }

    public static void decryptBpaiDtNasc(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach( bpai -> {
            try {
                bpai.setDtnasc(decrypt(bpai.getDtnasc()));
                bpai.setFlh(decrypt(bpai.getFlh()));
                bpai.setSeq(decrypt(bpai.getSeq()));
            } catch (EncryptionOperationNotPossibleException e) {
                System.out.println(bpai.getId());
            }
        });
    }

    public static void decryptSex(List<Bpai> bpaiListDB) {
        bpaiListDB.parallelStream().forEach(bpai -> {
            bpai.setSexo(decrypt(bpai.getSexo()));
            bpai.setFlh(decrypt(bpai.getFlh()));
            bpai.setSeq(decrypt(bpai.getSeq()));
        });
    }

    public static void encryptSex(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach(bpai -> {
            bpai.setFlh(encrypt(bpai.getFlh()));
            bpai.setSeq(encrypt(bpai.getSeq()));
            bpai.setSexo(encrypt(bpai.getSexo()));
        });
    }

    public static void decryptBpaiIdade(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach(bpai -> {
            bpai.setIdade(decrypt(bpai.getIdade()));
            bpai.setFlh(decrypt(bpai.getFlh()));
            bpai.setSeq(decrypt(bpai.getSeq()));
        });
    }

    public static void decryptFlhSeq(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach(bpai -> {
            bpai.setFlh(decrypt(bpai.getFlh()));
            bpai.setSeq(decrypt(bpai.getSeq()));
        });
    }

    public static void encryptBpaiDtAtendi(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach(bpai -> {
            bpai.setDtaten(encrypt(bpai.getDtaten()));
        });
    }

    public static void decryptBpaiDtAtendi(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach(bpai -> {
            bpai.setDtaten(decrypt(bpai.getDtaten()));
            bpai.setFlh(decrypt(bpai.getFlh()));
            bpai.setSeq(decrypt(bpai.getSeq()));
        });
    }

    public static void decryptRace(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach( bpai -> {
            bpai.setRaca(decrypt(bpai.getRaca()));
            bpai.setFlh(decrypt(bpai.getFlh()));
            bpai.setSeq(decrypt(bpai.getSeq()));
        });
    }

    public static void decryptAgeAndSexAndRace(List<Bpai> bpaiList) {
        bpaiList.forEach( bpai -> {
            if(!bpai.getRaca().isBlank()) bpai.setRaca(decrypt(bpai.getRaca()));
            if(!bpai.getSexo().isBlank()) bpai.setSexo(decrypt(bpai.getSexo()));
            if(!bpai.getIdade().isBlank()) bpai.setIdade(decrypt(bpai.getIdade()));
        });
    }

    public static void encryptCnsPac(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach( bpai -> {
            bpai.setCnspac(encrypt(bpai.getCnspac()));;
        });
    }

    public static void decryptProfessionalCns(List<ProfessionalComplete> professionalCompleteList) {
        professionalCompleteList.forEach( prof -> {
            prof.setCodCns(decrypt(prof.getCodCns()));
        });
    }

    public static void decryptBpaiIdadeAndDtnasc(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach(bpai -> {
            bpai.setIdade(decrypt(bpai.getIdade()));
            bpai.setDtnasc(decrypt(bpai.getDtnasc()));
        });
    }

    public static void encryptBpaiIdadeAndDtnasc(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach(bpai -> {
            bpai.setIdade(encrypt(bpai.getIdade()));
            bpai.setDtnasc(encrypt(bpai.getDtnasc()));
        });
    }

    public static void decryptCnsPac(List<Bpai> bpaiListDB) {
        bpaiListDB.forEach( bpai -> {
            bpai.setDtnasc(decrypt(bpai.getDtnasc()));
            bpai.setCnspac(decrypt(bpai.getCnspac()));
        });
    }

    public static void decryptProfessionalCnsAndName(List<ProfessionalComplete> professionalCompleteList) {
        professionalCompleteList.forEach( prof -> {
            prof.setCodCns(decrypt(prof.getCodCns()));
            prof.setName(decrypt(prof.getName()));
        });
    }

    public static void decryptCnsmed(List<Bpai> bpaiList) {
        bpaiList.forEach(bpai -> {
            bpai.setCnsmed(decrypt(bpai.getCnsmed()));
        });
    }

    public static Long encryptKeyUser(Long id) {
        return id + Long.parseLong(ENCODE_KEY_USER);
    }

    public static void encrypt(List<ProfessionalComplete> list) {
        list.forEach( professional -> {
            String profId = professional.getProfId();
            String codCns = professional.getCodCns();
            String name = professional.getName();

            professional.setProfId(encrypt(profId));
            professional.setKeyProfId(hashString(profId));
            professional.setCpf(encrypt(professional.getCpf()));
            professional.setName(encrypt(name));
            professional.setKeyName(hashString(name));
            professional.setNameMother(encrypt(professional.getNameMother()));
            professional.setBirthDate(encrypt(professional.getBirthDate()));
            professional.setSexo(encrypt(professional.getSexo()));
            professional.setLogradouro(encrypt(professional.getLogradouro()));
            professional.setNumber(encrypt(professional.getNumber()));
            professional.setComplement(encrypt(professional.getComplement()));
            professional.setBairrodist(encrypt(professional.getBairrodist()));
            professional.setCodCep(encrypt(professional.getCodCep()));
            professional.setNumAgenc(encrypt(professional.getNumAgenc()));
            professional.setContaCc(encrypt(professional.getContaCc()));
            professional.setCodCns(encrypt(codCns));
            professional.setKeyCodCns(hashString(codCns));
            professional.setUser(encrypt(professional.getUser()));
            professional.setCdRaca(encrypt(professional.getCdRaca()));
            professional.setNameFather(encrypt(professional.getNameFather()));
            professional.getDadosVinc().setCodCbo(encrypt(professional.getDadosVinc().getCodCbo()));
            professional.setTelephone(encrypt(professional.getTelephone()));
        });
    }

    public static void decrypt(List<ProfessionalComplete> list) {
        list.forEach(professional -> {
            professional.setProfId(decrypt(professional.getProfId()));
            professional.setName(decrypt(professional.getName()));
            professional.setCpf(decrypt(professional.getCpf()));
            professional.setNumber(decrypt(professional.getNumber()));
            professional.setLogradouro(decrypt(professional.getLogradouro()));
            professional.setNameMother(decrypt(professional.getNameMother()));
            professional.setBirthDate(decrypt(professional.getBirthDate()));
            professional.setSexo(decrypt(professional.getSexo()));
            professional.setComplement(decrypt(professional.getComplement()));
            professional.setBairrodist(decrypt(professional.getBairrodist()));
            professional.setCodCep(decrypt(professional.getCodCep()));
            professional.setNumAgenc(decrypt(professional.getNumAgenc()));
            professional.setContaCc(decrypt(professional.getContaCc()));
            professional.setCodCns(decrypt(professional.getCodCns()));
            professional.setUser(decrypt(professional.getUser()));
            professional.setCdRaca(decrypt(professional.getCdRaca()));
            professional.setNameFather(decrypt(professional.getNameFather()));
            professional.getDadosVinc().setCodCbo(decrypt(professional.getDadosVinc().getCodCbo()));
            professional.setTelephone(decrypt(professional.getTelephone()));
        });
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

    public static String encryptId(Long id) {
        while (true) {
           String data = encrypt(String.valueOf(id));
           if(!data.contains("/")) {
               return data;
           }
        }
    }

}
