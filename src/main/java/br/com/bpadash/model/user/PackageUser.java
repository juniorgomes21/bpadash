package br.com.bpadash.model.user;

import javax.persistence.*;

@Entity
public class PackageUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String packageName;
    private Long sizeStorage;
    private int numberRules;
    private int maxSession;

    public PackageUser() {
    }

    public PackageUser(String packageName , Long sizeStorage, int numberRules, int maxSession) {
        this.packageName = packageName;
        this.sizeStorage = sizeStorage;
        this.numberRules = numberRules;
        this.maxSession = maxSession;
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

    public int getMaxSession() {
        return maxSession;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }
}
