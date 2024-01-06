package br.com.bpadash.services.sigtap;

import br.com.bpadash.model.Address;
import br.com.bpadash.repository.sigtap.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Optional<Address> get(String cep) {
        return addressRepository.findByCep(cep);
    }
}
