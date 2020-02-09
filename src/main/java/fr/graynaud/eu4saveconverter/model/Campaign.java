package fr.graynaud.eu4saveconverter.model;

import javax.persistence.*;

@Entity(name = "Campaign")
@Table(name = "campaign")
public class Campaign extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "path", nullable = false, unique = true)
    private String path;

    @Column(name = "nb_saves", nullable = false)
    private Integer nbSaves;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") //Nullable in case the user is deleted, we want to keep the campaigns
    private User user;

    public Campaign() {
    }

    public Campaign(String name, String path, User user) {
        this.name = name;
        this.path = path;
        this.nbSaves = 0;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getNbSaves() {
        return nbSaves;
    }

    public void setNbSaves(Integer nbSaves) {
        this.nbSaves = nbSaves;
    }

    public void addSave() {
        this.nbSaves++;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String toFileName() {
        return this.path + "_" + this.nbSaves + ".json";
    }
}
