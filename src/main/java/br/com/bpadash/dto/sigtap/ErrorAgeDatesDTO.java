package br.com.bpadash.dto.sigtap;

public class ErrorAgeDatesDTO extends ErrorSigTapDTO {
    private String date;
    private String name;
    private String age;
    private boolean beffore1900;

    public ErrorAgeDatesDTO() {
    }

    public ErrorAgeDatesDTO(Long id, String msg, String flh, String seq, String name, String age, String date, boolean beffore1900) {
        super(id , msg, flh, seq);
        this.name = name;
        this.age = age;
        this.date = date;
        this.beffore1900 = beffore1900;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public boolean isBeffore1900() {
        return beffore1900;
    }

    public void setBeffore1900(boolean beffore1900) {
        this.beffore1900 = beffore1900;
    }
}
