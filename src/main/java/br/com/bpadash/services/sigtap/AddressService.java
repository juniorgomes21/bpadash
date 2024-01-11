package br.com.bpadash.services.sigtap;

import br.com.bpadash.model.Address;
import br.com.bpadash.repository.sigtap.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private CepService cepService;
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

        if(addressOptional.isPresent()) {
            address = addressOptional.get();
        } else {
            address = cepService.consult(cep);
            if(address == null || address.getCep() == null) {
                return null;
            }

            address.setCep(address.getCep().replace("-", ""));

            address = this.save(address);
        }

        return address;
    }
}
