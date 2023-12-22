package br.com.bpadash.dto.sigtap;

public class ErrorAgeProcedureDTO extends ErrorSigTapDTO {
    private String name;
    private int age;
    private int ageMin;
    private int ageMax;
    private boolean ageMinB;
    private String dateNasc;

    public ErrorAgeProcedureDTO() {
    }

    public ErrorAgeProcedureDTO(Long id , String msg , String flh , String seq , String name , int age , int ageMin , int ageMax , boolean ageMinB , String dateNasc) {
        super(id , msg , flh , seq);
        this.name = name;
        this.age = age;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.ageMinB = ageMinB;
        this.dateNasc = dateNasc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(int ageMin) {
        this.ageMin = ageMin;
    }

    public int getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(int ageMax) {
        this.ageMax = ageMax;
    }

    public boolean isAgeMinB() {
        return ageMinB;
    }

    public void setAgeMinB(boolean ageMinB) {
        this.ageMinB = ageMinB;
    }

    public String getDateNasc() {
        return dateNasc;
    }

    public void setDateNasc(String dateNasc) {
        this.dateNasc = dateNasc;
    }
}
