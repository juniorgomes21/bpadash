package br.com.bpadash.services.bpa;

import br.com.bpadash.errorValidation.ErrorValidationDTO;
import br.com.bpadash.errorValidation.ErrorsFile;
import br.com.bpadash.model.Bpa;
import br.com.bpadash.model.TitleBpa;
import br.com.bpadash.repository.bpa.TitleBpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TitleBpaService {

    @Autowired
    private TitleBpaRepository titleBpaRepository;

    public TitleBpa createTitleBpa(String line, int lineNumber, Bpa bpa, List<ErrorsFile> errorsFileList) throws IllegalArgumentException {

        List<ErrorValidationDTO> errors = new ArrayList<>();

        if (line.length() < 131) {
            //TODO perguntar a respeito do tamanho do titulo
            errors.add(errorValidation("LINHA", "O título do arquivo não contém 131 caracteres"));
            errorsFileList.add(new ErrorsFile(String.valueOf(lineNumber), errors));

            return null;
        }
        String iden = line.substring(0, 2);

        String hdr = line.substring(2, 7);
        if (!"#BPA#".equals(hdr)) {
            errors.add(errorValidation("HDR", "O cabeçalho do título na linha não é válido"));
        }

        String mvm = line.substring(7, 13);
        if(!mvm.matches("\\d+")) {
            errors.add(errorValidation("MVM", "Ano e mês do processamento da produção só pode conter números"));
        }

        String lin = line.substring(13, 19);
        if(!lin.matches("\\d+")) {
            errors.add(errorValidation("LIN", "O número de linhas do BPA gravadas só pode conter números"));
        }

        String flh = line.substring(19, 25);
        if(!flh.matches("\\d+")) {
            errors.add(errorValidation("FLH", "A quantidades de folhas de BPA gravadas só pode conter números"));
        }

        String smtVrf = line.substring(25, 29);
        if(!smtVrf.matches("\\d+")) {
            errors.add(errorValidation("SMTVRF", "O Campo de controle na linha só pode conter números"));
        }

        String rsp = line.substring(29, 59);
        String sgl = line.substring(59, 65);

        String cgccpf = line.substring(65, 79);
        if(!cgccpf.matches("\\d+")) {
            errors.add(errorValidation("CGCCPF", "CGC/CPF do prestador ou do órgão público só pode conter números"));
        }

        String dst = line.substring(79, 119);

        String dstIn = line.substring(119, 120);

        String versao = line.substring(120, 130);

        String fim;
        try {
            fim = line.substring(130, 132);
        } catch (StringIndexOutOfBoundsException e) {
            fim = "  ";
        }

        if(!errors.isEmpty()) {
            errorsFileList.add(new ErrorsFile(String.valueOf(lineNumber), errors));
            return null;
        }

        return new TitleBpa(
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
    }

    public TitleBpa save(TitleBpa titleBpa) {
        return titleBpaRepository.save(titleBpa);
    }

    public TitleBpa get(Bpa bpa) {

        return titleBpaRepository.findByBpa(bpa);
    }

    public Long sizeByte(Long id) {
        return titleBpaRepository.calculateSizeById(id);
    }

    private ErrorValidationDTO errorValidation(String field, String message) {
        return new ErrorValidationDTO(field, message);
    }

    public void delete(Bpa bpa) {
        titleBpaRepository.deleteByBpa(bpa);
    }
}
