package br.com.bpadash.services.sigtap;

import br.com.bpadash.model.bpa.Address;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.repository.sigtap.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private CepService cepService;
    @Autowired
    private CodLogradService codLogradService;
    @Autowired
    private AddressRepository addressRepository;


    public Optional<Address> get(String cep) {
        return addressRepository.findByCep(cep);
    }

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public Address verifyCep(String cep) {
        Address address;
        Optional<Address> addressOptional = this.get(cep);

        address = addressOptional.orElseGet(() -> this.consult(cep));

        return address;
    }

    public Address consult(String cep) {
        Address address = cepService.consult(cep);

        System.out.println("fez consulta");

        if(address == null || address.getCep() == null) {
            return null;
        }

        address.setCep(address.getCep().replace("-", ""));


        return this.save(address);
    }

    public int executeTreatment(List<Bpai> bpaiList) {

        int count = 0;
        for(Bpai bpai: bpaiList) {
            if(!bpai.getCepPcnte().isBlank()) {
                String cepPcnte =  bpai.getCepPcnte();
                String logradPcnte= bpai.getLogradPcnte();
                String complPcnte = bpai.getComplPcnte();
                String endPcnte = bpai.getEndPcnte();
                String bairroPcnte = bpai.getBairroPcnte();

                if(!cepPcnte.isBlank() || !logradPcnte.isBlank() || !complPcnte.isBlank() || !endPcnte.isBlank() || !bairroPcnte.isBlank()) {

                    Address address = this.verifyCep(cepPcnte);

                    if(address != null) {
                        bpai.setLogradPcnte(logradPcnte.isBlank() ? "008" : logradPcnte);
                        bpai.setComplPcnte(String.format("%10s", address.getComplemento()));
                        bpai.setEndPcnte(String.format("%5s", address.getLogradouro()));
                        bpai.setBairroPcnte(String.format("%30s", address.getBairro()));
                    }

                    count++;
                }
            }
        }

        return count;
    }

    public String getCodLograd(String logradouro) {
        if(logradouro.equals("")) {
            return "008";
        }

        return codLogradService.get(logradouro.split(" ")[0]);
    }

    public int executeTreatmentCepBlank(List<Bpai> bpaiList , Address address) {
        int count = 0;

        for(Bpai bpai: bpaiList) {
            if(bpai.getCepPcnte().isBlank()) {
                bpai.setCepPcnte(address.getCep());
                bpai.setLogradPcnte(address.getCodLograud());
                bpai.setComplPcnte(String.format("%10s", address.getComplemento()));
                bpai.setEndPcnte(String.format("%5s", address.getLogradouro()));
                bpai.setBairroPcnte(String.format("%30s", address.getBairro()));

                count++;
            }
        }

        return count;
    }
}
