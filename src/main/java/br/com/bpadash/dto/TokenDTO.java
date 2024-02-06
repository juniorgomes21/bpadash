package br.com.bpadash.dto;

import br.com.bpadash.dto.bpa.BpaDTO;

import java.util.ArrayList;
import java.util.List;

public class TokenDTO {

    private String token;
    private String tipo = "Bearer";
    private UserDTO userDTO;
    private DatesDTO datesDTO;


    public TokenDTO(String token) {
        this.token = token;
    }

    public TokenDTO(String token, UserDTO userDTO, DatesDTO datesDTO) {
        this.token = token;
        this.userDTO = userDTO;
        this.datesDTO = datesDTO;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public DatesDTO getDatesDTO() {
        return datesDTO;
    }

    public void setDatesDTO(DatesDTO datesDTO) {
        this.datesDTO = datesDTO;
    }
}
