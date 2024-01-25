package br.com.bpadash.repository.sigtap;

import br.com.bpadash.model.bpa.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByCep(String cep);
}
