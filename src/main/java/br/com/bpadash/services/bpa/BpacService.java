package br.com.bpadash.services.bpa;

import br.com.bpadash.dto.bpa.BpacDTO;
import br.com.bpadash.model.Bpa;
import br.com.bpadash.model.Bpac;
import br.com.bpadash.params.bpa.ParamUpdateBpac;
import br.com.bpadash.repository.bpa.BpacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BpacService {

    @Autowired
    private BpacRepository bpacRepository;

    public Bpac createBpac(String line, int lineNumber, Bpa bpa) {
            String formatLine = " Erro linha (" + lineNumber + ")";

            if (!(line.length() >= 48)) {
                throw new IllegalArgumentException("A linha Não contém 48 caracteres." + formatLine);
            }
            String iden = line.substring(0, 2);
            String cne = line.substring(2, 9);
            if(!cne.matches("\\d+")) {
                throw new IllegalArgumentException("O código CNES deverá ser preenchido apenas com números." + formatLine);
            }

            String cmp = line.substring(9, 15);
            if(!cmp.matches("\\d+")) {
                throw new IllegalArgumentException("O campo deverá ser preenchido apenas com números." + formatLine);
            }

            //TODO verificar validação
            String cbo = line.substring(15, 21);
            if(false) {
                throw new IllegalArgumentException("Código conforme a Classificação Brasileira de Ocupações (CBO)." + formatLine);
            }

            String flh = line.substring(21, 24);
            if(!flh.matches("\\d+")) {
                throw new IllegalArgumentException("Número da folha do BPA. Domínio [001..999]." + formatLine);
            }

            String seq = line.substring(24, 26);
            if(!seq.matches("\\d+")) {
                throw new IllegalArgumentException("Número da folha do BPA. Domínio [001..999]." + formatLine);
            }

            String pa = line.substring(26, 36);
            if(!pa.matches("\\d+")) {
                throw new IllegalArgumentException("O campo deverá ser preenchido apenas com números." + formatLine);
            }

            String idade = line.substring(36, 39);
            if(!idade.matches("\\d+")) {
                throw new IllegalArgumentException("O campo deverá ser preenchido apenas com números." + formatLine);
            }

            String qt = line.substring(39, 45);
            if(!cmp.matches("\\d+")) {
                throw new IllegalArgumentException("O campo deverá ser preenchido apenas com números." + formatLine);
            }

            //TODO verificar validação
            String org = line.substring(45, 48);
            if(!org.equals("BPA")) {
                System.out.println(org);
                throw new IllegalArgumentException("A linha não é do tipo BPA." + formatLine);
            }

            String fim;

            try {
                fim = line.substring(48, 50);
            } catch (StringIndexOutOfBoundsException e) {
                fim = "  ";
            }

            Bpac bpac = new Bpac(
                    bpa,
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

            return bpac;
    }

    public Bpac bpacId(Long id) {
        Optional<Bpac> optionalBpac = bpacRepository.findById(id);

        return optionalBpac.get();
    }

    public int sizeByte(List<Long> listIds) {
        return bpacRepository.calculateSizeById(listIds);
    }

    public Bpac edit(Bpac bpac , ParamUpdateBpac paramUpdateBpac) {
        bpac.setCnes(paramUpdateBpac.getCnes());
        bpac.setCmp(paramUpdateBpac.getCmp());
        bpac.setCbo(paramUpdateBpac.getCbo());
        bpac.setFlh(paramUpdateBpac.getFlh());
        bpac.setSeq(paramUpdateBpac.getSeq());
        bpac.setPa(paramUpdateBpac.getPa());
        bpac.setIdade(paramUpdateBpac.getIdade());
        bpac.setQt(paramUpdateBpac.getQt());
        bpac.setOrg(paramUpdateBpac.getOrg());

        return bpac;
    }

    public void save(Bpac bpac) {
        bpacRepository.save(bpac);
    }

    public List<Bpac> save(List<Bpac> bpacList) {
        return bpacRepository.saveAll(bpacList);
    }

    public void delete(Bpac bpac) {
        bpacRepository.delete(bpac);
    }

    public void delete(List<Long> listIds) {
        bpacRepository.deleteAllById(listIds);
    }

    public Page<BpacDTO> get(Bpa bpa , Pageable pageable) {
        Page<Bpac> page = bpacRepository.findByBpa(bpa, pageable);

        List<BpacDTO> bpacDTOList = page.getContent().stream()
                .map(BpacDTO::new)
                .collect(Collectors.toList());



        return new PageImpl<>(bpacDTOList, pageable, page.getTotalElements());
    }
}
