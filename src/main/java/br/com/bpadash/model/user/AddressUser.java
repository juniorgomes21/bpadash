package br.com.bpadash.model.user;

import br.com.bpadash.model.bpa.Address;

import javax.persistence.Entity;

@Entity
public class AddressUser extends Address {

    public AddressUser() {
    }

    public AddressUser(Address address) {
        this.setCep(address.getCep());
        this.setCodLograud(address.getCodLograud());
        this.setLogradouro(address.getLogradouro());
        this.setComplemento(address.getComplemento());
        this.setBairro(address.getBairro());
        this.setLocalidade("Belém");
        this.setUf("PA");
        this.setIbge(address.getIbge());
        this.setGia("");
        this.setDdd("091");
        this.setSiafi("");
        this.setBpai(false);
    }
}
