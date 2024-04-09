package br.com.bpadash.dto;

public class TokenDTO {

    private String token;
    private String typo = "Bearer";
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

    public String getTypo() {
        return typo;
    }

    public void setTypo(String typo) {
        this.typo = typo;
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
