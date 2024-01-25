package br.com.bpadash.model.user;

import javax.persistence.*;

@Entity
public class PackageUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String packageName;
    @Column(unique = true)
    private Long sizeStorage;
    @Column(unique = true)
    private int numberRules;


    public PackageUser() {
    }

    public PackageUser(String packageName , Long sizeStorage , int numberRules) {
        this.packageName = packageName;
        this.sizeStorage = sizeStorage;
        this.numberRules = numberRules;
    }

    public Long getId() {
        return id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Long getSizeStorage() {
        return sizeStorage;
    }

    public void setSizeStorage(Long sizeStorage) {
        this.sizeStorage = sizeStorage;
    }

    public int getNumberRules() {
        return numberRules;
    }

    public void setNumberRules(int numberRules) {
        this.numberRules = numberRules;
    }
}
