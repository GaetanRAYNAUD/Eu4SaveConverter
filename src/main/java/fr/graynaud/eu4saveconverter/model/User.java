package fr.graynaud.eu4saveconverter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

@Entity(name = "User")
@Table(name = "user")
public class User extends BaseEntity {

    @Column(name = "steam_id", length = 18, unique = true, nullable = false)
    private String steamId;

    @Column(name = "last_connection_date", nullable = false)
    private Date lastConnectionDate;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Role> roles;

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public Date getLastConnectionDate() {
        return lastConnectionDate;
    }

    public void setLastConnectionDate(Date lastConnectionDate) {
        this.lastConnectionDate = lastConnectionDate;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
