package br.com.bpadash.services.bpa;

import br.com.bpadash.dto.bpa.BpaiDTO;
import br.com.bpadash.model.Bpa;
import br.com.bpadash.model.Bpai;
import br.com.bpadash.model.BpaiValidation;
import br.com.bpadash.params.bpa.ParamUpdateBpai;
import br.com.bpadash.repository.bpa.BpaiRepository;
import br.com.bpadash.repository.bpa.BpaiValidationRepository;
import br.com.bpadash.services.EncryptionService;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BpaiService {

    @Autowired
    private BpaiRepository bpaiRepository;

    @Autowired
    private BpaiValidationRepository bpaiValidationRepository;


    public Bpai create(String line, int lineNumber, Bpa bpa) {
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
                bpa,
                String.valueOf(0),
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

        return bpai;
    }

    public Optional<Bpai> bpacId(Long id) {

        return bpaiRepository.findById(id);
    }

    public Bpai edit(Bpai bpai, ParamUpdateBpai paramUpdateBpai) {
        bpai.setCnes(paramUpdateBpai.getCnes());
        bpai.setCmp(paramUpdateBpai.getCmp());
        bpai.setCnsmed(paramUpdateBpai.getCnsmed());
        bpai.setCbo(paramUpdateBpai.getCbo());
        bpai.setDtaten(paramUpdateBpai.getDtaten());
        bpai.setFlh(paramUpdateBpai.getFlh());
        bpai.setSeq(paramUpdateBpai.getSeq());
        bpai.setPa(paramUpdateBpai.getPa());
        bpai.setCnspac(paramUpdateBpai.getCnspac());
        bpai.setSexo(paramUpdateBpai.getSexo());
        bpai.setIbge(paramUpdateBpai.getIbge());
        bpai.setCid(paramUpdateBpai.getCid());
        bpai.setIdade(paramUpdateBpai.getIdade());
        bpai.setQt(paramUpdateBpai.getQt());
        bpai.setCaten(paramUpdateBpai.getCaten());
        bpai.setNaut(paramUpdateBpai.getNaut());
        bpai.setOrg(paramUpdateBpai.getOrg());
        bpai.setNmpac(paramUpdateBpai.getNmpac());
        bpai.setDtnasc(paramUpdateBpai.getDtnasc());
        bpai.setRaca(paramUpdateBpai.getRaca());
        bpai.setEtnia(paramUpdateBpai.getEtnia());
        bpai.setNac(paramUpdateBpai.getNac());
        bpai.setSrv(paramUpdateBpai.getSrv());
        bpai.setClf(paramUpdateBpai.getClf());
        bpai.setEquipeSeq(paramUpdateBpai.getEquipeSeq());
        bpai.setEquipeArea(paramUpdateBpai.getEquipeArea());
        bpai.setCnpj(paramUpdateBpai.getCnpj());
        bpai.setCepPcnte(paramUpdateBpai.getCepPcnte());
        bpai.setLogradPcnte(paramUpdateBpai.getLogradPcnte());
        bpai.setEndPcnte(paramUpdateBpai.getEndPcnte());
        bpai.setComplPcnte(paramUpdateBpai.getComplPcnte());
        bpai.setNumPcnte(paramUpdateBpai.getNumPcnte());
        bpai.setBairroPcnte(paramUpdateBpai.getBairroPcnte());
        bpai.setDdtelPcnte(paramUpdateBpai.getDdtelPcnte());
        bpai.setEmailPcnte(paramUpdateBpai.getEmailPcnte());
        bpai.setIne(paramUpdateBpai.getIne());

        return bpai;
    }

    public void delete(Bpai bpai) {
        bpaiRepository.delete(bpai);
    }

    public void delete(Bpa bpa) {
        bpaiRepository.deleteByBpa(bpa);
    }

    public void deleteById(List<Long> bpai) {
        bpaiRepository.deleteAllById(bpai);
    }

    public void save(Bpai bpai) {
        bpaiRepository.save(bpai);
    }

    @Transactional
    public List<Bpai> save(List<Bpai> bpaiList, StopWatch startTime) {
        System.out.println("salvando BPAI: " + startTime.getTime() +"/ " + startTime.getTime()/1000);
        List<Bpai> x =  bpaiRepository.saveAll(bpaiList);
        System.out.println("salvo BPAI "+ startTime.getTime() +"/ " + startTime.getTime()/1000);
        return x;
    }

    public Long sizeByte(List<Long> listIds) {
        return bpaiRepository.calculateSizeById(listIds);
    }

    public Page<BpaiDTO> get(Bpa bpa , Pageable pageable) {
        Page<Bpai> page = bpaiRepository.findByBpa(bpa, pageable);

        List<BpaiDTO> bpacDTOList = page.getContent().stream()
                .map(bpai -> new BpaiDTO(bpai, bpa.getIdentifier()))
                .collect(Collectors.toList());

        return new PageImpl<>(bpacDTOList, pageable, page.getTotalElements());
    }

    public List<Bpai> getBpaiList(Bpa bpa) {
        return bpaiRepository.findByBpa(bpa);
    }

    public BpaiValidation createBpaiValidation() {
        BpaiValidation bpaiValidation = new BpaiValidation();

        return bpaiValidationRepository.save(bpaiValidation);
    }

}

