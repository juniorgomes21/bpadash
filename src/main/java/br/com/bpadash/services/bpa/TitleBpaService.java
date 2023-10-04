package br.com.bpadash.services.bpa;

import br.com.bpadash.model.Bpa;
import br.com.bpadash.model.TitleBpa;
import br.com.bpadash.repository.bpa.TitleBpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TitleBpaService {

    @Autowired
    private TitleBpaRepository titleBpaRepository;

    public TitleBpa createTitleBpa(String line, int lineNumber, Bpa bpa) throws IllegalArgumentException {

        if (!(line.length() >= 126)) {
            throw new IllegalArgumentException("O título do arquivo na linha " + lineNumber + " não contém 127 caracteres");
        }
        String iden = line.substring(0, 2);

        String hdr = line.substring(2, 7);
        if (!"#BPA#".equals(hdr)) {
            throw new IllegalArgumentException("O cabeçalho do título na linha " + lineNumber + " não é válido");
        }

        String mvm = line.substring(7, 13);
        if(!mvm.matches("\\d+")) {
            throw new IllegalArgumentException("Ano e mês de Processamento da produção na linha " + lineNumber + " só pode conter números");
        }

        String lin = line.substring(13, 19);
        if(!mvm.matches("\\d+")) {
            throw new IllegalArgumentException("O número de linhas do BPA gravadas na linha " + lineNumber + " só pode conter números");
        }

        String flh = line.substring(19, 25);
        if(!mvm.matches("\\d+")) {
            throw new IllegalArgumentException("A quantidades de folhas de BPA gravadas na linha " + lineNumber + " só pode conter números");
        }

        String smtVrf = line.substring(25, 29);
        if(!mvm.matches("\\d+")) {
            throw new IllegalArgumentException("O Campo de control na linha " + lineNumber + " só pode conter números");
        }

        String rsp = line.substring(29, 59);
        if(!mvm.matches("\\d+")) {
            throw new IllegalArgumentException("O Campo de control na linha " + lineNumber + " só pode conter números");
        }

        String sgl = line.substring(59, 65);
        String cgccpf = line.substring(65, 79);
        if(!mvm.matches("\\d+")) {
            throw new IllegalArgumentException("CGC/CPF do prestador ou do órgão público na linha " + lineNumber + " só pode conter números");
        }
        String dst = line.substring(79, 119);
        String dstIn = line.substring(119, 120);
        String versao = line.substring(120, 130);
//        try {
//            versao = line.substring(120, 130);
//        } catch (StringIndexOutOfBoundsException e) {
//            versao = "  ";
//        }

        String fim;
        try {
            fim = line.substring(130, 132);
        } catch (StringIndexOutOfBoundsException e) {
            fim = "  ";
        }

        TitleBpa titleBpa = new TitleBpa(
            bpa,
            iden,
            hdr,
            mvm,
            lin,
            flh,
            smtVrf,
            rsp,
            sgl,
            cgccpf,
            dst,
            dstIn,
            versao,
            fim
        );

        return titleBpa;
    }

    public TitleBpa save(TitleBpa titleBpa) {
        return titleBpaRepository.save(titleBpa);
    }

    public TitleBpa get(Bpa bpa) {

        return titleBpaRepository.findByBpa(bpa);
    }

    public int sizeByte(Long id) {
        return titleBpaRepository.calculateSizeById(id);
    }
}
