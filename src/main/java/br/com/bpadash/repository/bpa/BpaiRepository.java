package br.com.bpadash.repository.bpa;

import br.com.bpadash.model.Bpa;
import br.com.bpadash.model.Bpai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BpaiRepository extends JpaRepository<Bpai, Long> {

    @Query(value = "SELECT SUM(" +
            "LENGTH(b.ident) + LENGTH(b.cnes) + LENGTH(b.cmp) + LENGTH(b.cnsmed) + " +
            "LENGTH(b.cbo) + LENGTH(b.dtaten) + LENGTH(b.flh) + LENGTH(b.seq) + " +
            "LENGTH(b.pa) + LENGTH(b.cnspac) + LENGTH(b.sexo) + LENGTH(b.ibge) + " +
            "LENGTH(b.cid) + LENGTH(b.idade) + LENGTH(b.qt) + LENGTH(b.caten) + " +
            "LENGTH(b.naut) + LENGTH(b.org) + LENGTH(b.nmpac) + LENGTH(b.dtnasc) + " +
            "LENGTH(b.raca) + LENGTH(b.etnia) + LENGTH(b.nac) + LENGTH(b.srv) + " +
            "LENGTH(b.clf) + LENGTH(b.equipeSeq) + LENGTH(b.equipeArea) + " +
            "LENGTH(b.cnpj) + LENGTH(b.cepPcnte) + LENGTH(b.logradPcnte) + " +
            "LENGTH(b.endPcnte) + LENGTH(b.complPcnte) + LENGTH(b.numPcnte) + " +
            "LENGTH(b.bairroPcnte) + LENGTH(b.ddtelPcnte) + LENGTH(b.emailPcnte) + " +
            "LENGTH(b.ine) + LENGTH(b.fim)" +
            ") " +
            "FROM Bpai b WHERE b.id IN :idList")
    Long calculateSizeById(@Param("idList") List<Long> idList);

    List<Bpai> findByBpa(Bpa bpa);

    Page<Bpai> findByBpa(Bpa bpa , Pageable pageable);

    void deleteByBpa(Bpa bpa);

    List<Bpai> findByCnsmedAndBpa(String cnsmed, Bpa bpa);

    List<Bpai> findByPaAndBpa(String oldPa, Bpa bpa);

    List<Bpai> findByIdIn(List<Long> cepsIds);

    List<Bpai> findByCnspacHasAndBpaIn(String key , List<Bpa> bpaList);

    List<Bpai> findByCboAndBpa(String s , Bpa bpa);

    Bpai findTopByBpaOrderByFlhDescSeqDesc(Bpa bpa);

    Optional<Bpai> findFristByCnspacHas(String key); // Optional<Bpai> findFristByCnspacHasAndBpaIn(String key , List<Bpa> bpaList);
}
